package de.invesdwin.context.python.runtime.graalpy;

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
public class GraalpyScriptTaskCallbackContext implements Closeable {

    private static final Map<String, GraalpyScriptTaskCallbackContext> UUID_CONTEXT = new ConcurrentHashMap<>();

    private final String uuid;
    private final IScriptTaskCallback callback;

    public GraalpyScriptTaskCallbackContext(final IScriptTaskCallback callback) {
        this.uuid = UUIDs.newPseudoRandomUUID();
        this.callback = callback;
        UUID_CONTEXT.put(uuid, this);
    }

    public static GraalpyScriptTaskCallbackContext getContext(final String uuid) {
        return UUID_CONTEXT.get(uuid);
    }

    public void init(final GraalpyScriptTaskEnginePython engine) {
        engine.getInputs().putString("graalpyScriptTaskCallbackContextUuid", getUuid());
        engine.eval(new ClassPathResource(GraalpyScriptTaskCallbackContext.class.getSimpleName() + ".py",
                GraalpyScriptTaskCallbackContext.class));
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
