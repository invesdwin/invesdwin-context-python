package de.invesdwin.context.python.runtime.python4j.internal;

import javax.annotation.concurrent.NotThreadSafe;

import org.bytedeco.cpython.PyObject;
import org.nd4j.python4j.PythonException;
import org.nd4j.python4j.PythonExecutioner;
import org.nd4j.python4j.PythonObject;
import org.nd4j.python4j.PythonType;
import org.springframework.core.io.ClassPathResource;

import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.context.python.runtime.python4j.Python4jScriptTaskEnginePython;
import de.invesdwin.util.assertions.Assertions;
import de.invesdwin.util.concurrent.lock.ILock;
import io.netty.util.concurrent.FastThreadLocal;

/**
 * WARNING: Don't share instances of this class between threads, or else deadlocks or jvm crashes might occur due to GIL
 * lock mismanagement.
 */
@NotThreadSafe
public final class UncheckedPythonEngineWrapper implements IPythonEngineWrapper {

    private static final String ANS = "__ans__";
    private static final String ANS_EQUALS = ANS + " = ";

    private static final FastThreadLocal<UncheckedPythonEngineWrapper> INSTANCE = new FastThreadLocal<UncheckedPythonEngineWrapper>() {
        @Override
        protected UncheckedPythonEngineWrapper initialValue() throws Exception {
            return new UncheckedPythonEngineWrapper();
        }
    };

    private static PyObject globals;
    private static PyObject globalsAns;
    private final GilLock gilLock = new GilLock();

    private UncheckedPythonEngineWrapper() {
    }

    public PythonObject newNone() {
        evalUnchecked(ANS_EQUALS + "None");
        return getAns();
    }

    public void init() {
        synchronized (UncheckedPythonEngineWrapper.class) {
            Assertions.checkNotNull(PythonExecutioner.class);
            if (UncheckedPythonEngineWrapper.globals != null) {
                return;
            }
            gilLock.lock();
            try {
                final Python4jScriptTaskEnginePython engine = new Python4jScriptTaskEnginePython(this);
                engine.eval(new ClassPathResource(UncheckedPythonEngineWrapper.class.getSimpleName() + ".py",
                        UncheckedPythonEngineWrapper.class));
                engine.close();

                final PyObject main = org.bytedeco.cpython.global.python.PyImport_ImportModule("__main__");
                UncheckedPythonEngineWrapper.globals = org.bytedeco.cpython.global.python.PyModule_GetDict(main);
                UncheckedPythonEngineWrapper.globalsAns = org.bytedeco.cpython.global.python.PyUnicode_FromString(ANS);
                //we keep the refs eternally
                //org.bytedeco.cpython.global.python.Py_DecRef(main);
                //org.bytedeco.cpython.global.python.Py_DecRef(globals);
                //org.bytedeco.cpython.global.python.Py_DecRef(globalsAns);
            } finally {
                gilLock.unlock();
            }
        }
    }

    @Override
    public ILock getLock() {
        return gilLock;
    }

    @Override
    public void exec(final String expression) {
        IScriptTaskRunnerPython.LOG.debug("exec %s", expression);
        eval(expression);
    }

    private void eval(final String expression) {
        gilLock.lock();
        try {
            evalUnchecked(expression);
        } finally {
            gilLock.unlock();
        }
    }

    private void evalUnchecked(final String expression) {
        final int result = org.bytedeco.cpython.global.python.PyRun_SimpleString(expression);
        if (result != 0) {
            throw new PythonException("Execution failed, unable to retrieve python exception.");
        }
    }

    private PythonObject getAns() {
        return new PythonObject(org.bytedeco.cpython.global.python.PyObject_GetItem(globals, globalsAns), false);
    }

    @Override
    public Object get(final String variable) {
        IScriptTaskRunnerPython.LOG.debug("get %s", variable);
        gilLock.lock();
        try {
            evalUnchecked(ANS_EQUALS + variable);
            final PythonObject ans = getAns();
            final PythonType<Object> type = ModifiedPythonTypes.getPythonTypeForPythonObject(ans);
            return type.toJava(ans);
        } finally {
            gilLock.unlock();
        }
    }

    @Override
    public void set(final String variable, final Object value) {
        IScriptTaskRunnerPython.LOG.debug("set %s = %s", variable, value);
        gilLock.lock();
        try {
            if (value == null) {
                evalUnchecked(variable + " = None");
            } else {
                final PythonObject converted = ModifiedPythonTypes.convert(value);
                org.bytedeco.cpython.global.python.PyDict_SetItemString(globals, variable,
                        converted.getNativePythonObject());
            }
        } catch (final Throwable t) {
            throw new RuntimeException("Variable=" + variable + " Value=" + value, t);
        } finally {
            gilLock.unlock();
        }
    }

    public static UncheckedPythonEngineWrapper getInstance() {
        return UncheckedPythonEngineWrapper.INSTANCE.get();
    }

}