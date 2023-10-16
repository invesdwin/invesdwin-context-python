package de.invesdwin.context.python.runtime.jep;

import java.io.Closeable;
import java.util.Arrays;
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
public class JepScriptTaskCallbackContext implements Closeable {

    private static final Map<String, JepScriptTaskCallbackContext> UUID_CONTEXT = new ConcurrentHashMap<>();

    private final String uuid;
    private final IScriptTaskCallback callback;

    public JepScriptTaskCallbackContext(final IScriptTaskCallback callback) {
        this.uuid = UUIDs.newPseudoRandomUUID();
        this.callback = callback;
        UUID_CONTEXT.put(uuid, this);
    }

    public static JepScriptTaskCallbackContext getContext(final String uuid) {
        return UUID_CONTEXT.get(uuid);
    }

    public String getUuid() {
        return uuid;
    }

    public Object invoke(final Object[] args) {
        final ObjectScriptTaskParameters parameters = ObjectScriptTaskParametersPool.INSTANCE.borrowObject();
        final ObjectScriptTaskReturns returns = ObjectScriptTaskReturnsPool.INSTANCE.borrowObject();
        try {
            parameters.setParameters(Arrays.copyOfRange(args, 1, args.length));
            final String methodName = (String) args[0];
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
