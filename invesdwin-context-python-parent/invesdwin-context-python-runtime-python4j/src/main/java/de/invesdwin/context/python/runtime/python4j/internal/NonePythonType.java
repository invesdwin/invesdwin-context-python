package de.invesdwin.context.python.runtime.python4j.internal;

import javax.annotation.concurrent.NotThreadSafe;

import org.nd4j.python4j.PythonObject;
import org.nd4j.python4j.PythonType;

@NotThreadSafe
public class NonePythonType extends PythonType<Void> {

    public NonePythonType() {
        super("NoneType", Void.class);
    }

    @Override
    public boolean accepts(final Object javaObject) {
        return javaObject == null;
    }

    @Override
    public Void toJava(final PythonObject pythonObject) {
        return null;
    }

    @Override
    public PythonObject toPython(final Void javaObject) {
        return UncheckedPythonEngineWrapper.getInstance().newNone();
    }

}
