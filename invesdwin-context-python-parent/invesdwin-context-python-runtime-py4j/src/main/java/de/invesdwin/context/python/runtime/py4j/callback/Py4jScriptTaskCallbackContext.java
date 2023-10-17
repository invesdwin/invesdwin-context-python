package de.invesdwin.context.python.runtime.py4j.callback;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.concurrent.ThreadSafe;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;

import de.invesdwin.context.integration.marshaller.MarshallerJsonJackson;
import de.invesdwin.context.integration.script.callback.IScriptTaskCallback;
import de.invesdwin.context.python.runtime.contract.callback.ScriptTaskParametersPythonFromJson;
import de.invesdwin.context.python.runtime.contract.callback.ScriptTaskParametersPythonFromJsonPool;
import de.invesdwin.context.python.runtime.contract.callback.ScriptTaskReturnsPythonToExpression;
import de.invesdwin.context.python.runtime.contract.callback.ScriptTaskReturnsPythonToExpressionPool;
import de.invesdwin.util.error.Throwables;
import de.invesdwin.util.lang.UUIDs;

@ThreadSafe
public class Py4jScriptTaskCallbackContext implements Closeable {

    private static final Map<String, Py4jScriptTaskCallbackContext> UUID_CONTEXT = new ConcurrentHashMap<>();

    private final String uuid;
    private final IScriptTaskCallback callback;
    private final ObjectMapper mapper;

    public Py4jScriptTaskCallbackContext(final IScriptTaskCallback callback) {
        this.uuid = UUIDs.newPseudoRandomUUID();
        this.callback = callback;
        UUID_CONTEXT.put(uuid, this);
        this.mapper = MarshallerJsonJackson.getInstance().getJsonMapper(false);
    }

    public static Py4jScriptTaskCallbackContext getContext(final String uuid) {
        return UUID_CONTEXT.get(uuid);
    }

    public String getUuid() {
        return uuid;
    }

    public String invoke(final String methodName, final String args) {
        final ScriptTaskParametersPythonFromJson parameters = ScriptTaskParametersPythonFromJsonPool.INSTANCE
                .borrowObject();
        final ScriptTaskReturnsPythonToExpression returns = ScriptTaskReturnsPythonToExpressionPool.INSTANCE
                .borrowObject();
        try {
            final JsonNode jsonArgs = toJsonNode(args);
            parameters.setParameters(jsonArgs);
            callback.invoke(methodName, parameters, returns);
            return returns.getReturnExpression();
        } finally {
            ScriptTaskReturnsPythonToExpressionPool.INSTANCE.returnObject(returns);
            ScriptTaskParametersPythonFromJsonPool.INSTANCE.returnObject(parameters);
        }
    }

    private JsonNode toJsonNode(final String json) {
        try {
            final JsonNode node = mapper.readTree(json);
            if (node instanceof NullNode) {
                return null;
            } else {
                return node;
            }
        } catch (final Throwable t) {
            throw Throwables.propagate(t);
        }
    }

    @Override
    public void close() {
        UUID_CONTEXT.remove(uuid);
    }

}
