package de.invesdwin.context.python.runtime.py4j;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.concurrent.ThreadSafe;

import de.invesdwin.context.integration.script.callback.IScriptTaskCallback;
import de.invesdwin.context.integration.script.callback.ObjectScriptTaskParameters;
import de.invesdwin.context.integration.script.callback.ObjectScriptTaskParametersPool;
import de.invesdwin.context.integration.script.callback.ObjectScriptTaskReturns;
import de.invesdwin.context.integration.script.callback.ObjectScriptTaskReturnsPool;
import de.invesdwin.util.lang.UUIDs;

@ThreadSafe
public class Py4jScriptTaskCallbackContext implements Closeable {

    private static final Map<String, Py4jScriptTaskCallbackContext> UUID_CONTEXT = new ConcurrentHashMap<>();

    private final String uuid;
    private final IScriptTaskCallback callback;

    public Py4jScriptTaskCallbackContext(final IScriptTaskCallback callback) {
        this.uuid = UUIDs.newPseudoRandomUUID();
        this.callback = callback;
        UUID_CONTEXT.put(uuid, this);
    }

    public static Py4jScriptTaskCallbackContext getContext(final String uuid) {
        return UUID_CONTEXT.get(uuid);
    }

    public String getUuid() {
        return uuid;
    }

    public Object invoke(final String methodName, final Object[] args) {
        final ObjectScriptTaskParameters parameters = ObjectScriptTaskParametersPool.INSTANCE.borrowObject();
        final ObjectScriptTaskReturns returns = ObjectScriptTaskReturnsPool.INSTANCE.borrowObject();
        try {
            parameters.setParameters(args);
            callback.invoke(methodName, parameters, returns);
            return returns.getReturnValue();
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
