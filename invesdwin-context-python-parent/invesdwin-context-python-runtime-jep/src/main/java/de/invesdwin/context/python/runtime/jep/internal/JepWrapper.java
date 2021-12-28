package de.invesdwin.context.python.runtime.jep.internal;

import java.io.Closeable;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.core.io.ClassPathResource;

import de.invesdwin.context.python.runtime.jep.JepProperties;
import de.invesdwin.context.python.runtime.jep.JepScriptTaskEnginePython;
import de.invesdwin.instrument.DynamicInstrumentationReflections;
import de.invesdwin.util.lang.finalizer.AFinalizer;
import io.netty.util.concurrent.FastThreadLocal;
import jep.Jep;
import jep.JepConfig;
import jep.JepException;
import jep.SubInterpreter;

@NotThreadSafe
public final class JepWrapper implements Closeable {

    private static final FastThreadLocal<JepWrapper> JEP = new FastThreadLocal<JepWrapper>() {
        @Override
        protected JepWrapper initialValue() {
            return new JepWrapper();
        }

        @Override
        protected void onRemoval(final JepWrapper value) throws Exception {
            value.close();
        }
    };

    static {
        DynamicInstrumentationReflections.addPathToJavaLibraryPath(JepProperties.JEP_LIBRARY_PATH);
    }

    private final JepWrapperFinalizer finalizer;

    private JepWrapper() {
        try {
            this.finalizer = new JepWrapperFinalizer();
            this.finalizer.register(this);
            final JepScriptTaskEnginePython engine = new JepScriptTaskEnginePython(finalizer.jep);
            engine.eval(new ClassPathResource(JepWrapper.class.getSimpleName() + ".py", JepWrapper.class));
            engine.close();
        } catch (final JepException e) {
            throw new RuntimeException(
                    "Maybe you are mixing the python version with a different one for which jep was compiled for?", e);
        }
    }

    public Jep getJep() {
        return finalizer.jep;
    }

    @Override
    public void close() {
        finalizer.close();
    }

    private static final class JepWrapperFinalizer extends AFinalizer {

        private SubInterpreter jep;

        private JepWrapperFinalizer() throws JepException {
            this.jep = new SubInterpreter(new JepConfig().setSharedModules(JepProperties.getSharedModules()));
        }

        @Override
        protected void clean() {
            try {
                jep.close();
                jep = null;
            } catch (final JepException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected boolean isCleaned() {
            return jep == null;
        }

        @Override
        public boolean isThreadLocal() {
            return false;
        }

    }

    public static JepWrapper get() {
        return JEP.get();
    }
}