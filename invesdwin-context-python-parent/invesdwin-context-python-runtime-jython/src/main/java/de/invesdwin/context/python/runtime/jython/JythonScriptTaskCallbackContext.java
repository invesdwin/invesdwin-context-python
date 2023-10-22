package de.invesdwin.context.python.runtime.jython;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.concurrent.ThreadSafe;

import org.springframework.core.io.ClassPathResource;

import de.invesdwin.context.integration.script.callback.IScriptTaskCallback;
import de.invesdwin.context.integration.script.callback.ObjectScriptTaskParameters;
import de.invesdwin.context.integration.script.callback.ObjectScriptTaskParametersPool;
import de.invesdwin.context.integration.script.callback.ObjectScriptTaskReturnValue;
import de.invesdwin.context.integration.script.callback.ObjectScriptTaskReturns;
import de.invesdwin.context.integration.script.callback.ObjectScriptTaskReturnsPool;
import de.invesdwin.util.lang.UUIDs;

@ThreadSafe
public class JythonScriptTaskCallbackContext implements Closeable {

    private static final Map<String, JythonScriptTaskCallbackContext> UUID_CONTEXT = new ConcurrentHashMap<>();

    private final String uuid;
    private final IScriptTaskCallback callback;

    public JythonScriptTaskCallbackContext(final IScriptTaskCallback callback) {
        this.uuid = UUIDs.newPseudoRandomUUID();
        this.callback = callback;
        UUID_CONTEXT.put(uuid, this);
    }

    public static JythonScriptTaskCallbackContext getContext(final String uuid) {
        return UUID_CONTEXT.get(uuid);
    }

    public void init(final JythonScriptTaskEnginePython engine) {
        engine.getInputs().putString("jythonScriptTaskCallbackContextUuid", getUuid());
        engine.eval(new ClassPathResource(JythonScriptTaskCallbackContext.class.getSimpleName() + ".py",
                JythonScriptTaskCallbackContext.class));
    }

    public String getUuid() {
        return uuid;
    }

    public ObjectScriptTaskReturnValue invoke(final String methodName, final Object... args) {
        final ObjectScriptTaskParameters parameters = ObjectScriptTaskParametersPool.INSTANCE.borrowObject();
        final ObjectScriptTaskReturns returns = ObjectScriptTaskReturnsPool.INSTANCE.borrowObject();
        try {
            parameters.setParameters(args);
            callback.invoke(methodName, parameters, returns);
            return returns.newReturn();
        } finally {
            ObjectScriptTaskReturnsPool.INSTANCE.returnObject(returns);
            ObjectScriptTaskParametersPool.INSTANCE.returnObject(parameters);
        }
    }

    @Override
    public void close() {
        UUID_CONTEXT.remove(uuid);
    }

}
