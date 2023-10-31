package de.invesdwin.context.python.runtime.jython;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.core.io.ClassPathResource;

import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.integration.script.IScriptTaskInputs;
import de.invesdwin.context.integration.script.IScriptTaskResults;
import de.invesdwin.context.integration.script.callback.IScriptTaskCallback;
import de.invesdwin.context.integration.script.callback.ReflectiveScriptTaskCallback;
import de.invesdwin.context.integration.script.callback.ReturnExpression;
import de.invesdwin.context.python.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.util.assertions.Assertions;
import de.invesdwin.util.lang.UUIDs;

@NotThreadSafe
public class SimpleCallbackTest {

    private static final Map<String, String> UUID_SECRET = new ConcurrentHashMap<>();

    private final IScriptTaskRunnerPython runner;
    private int voidMethodCalled;

    public SimpleCallbackTest(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public static String getSecretStatic(final String uuid) {
        return UUID_SECRET.get(uuid);
    }

    public String getSecret(final String uuid) {
        return UUID_SECRET.get(uuid);
    }

    @ReturnExpression
    public String getSecretExpression(final String uuid) {
        return "\"secret\"+\"123\"";
    }

    public void voidMethod() {
        voidMethodCalled++;
    }

    public void testSimpleCallback() {
        final String uuid = UUIDs.newPseudoRandomUUID();
        final String secret = "secret123";
        UUID_SECRET.put(uuid, secret);
        try {
            new AScriptTaskPython<Void>() {

                @Override
                public IScriptTaskCallback getCallback() {
                    return new ReflectiveScriptTaskCallback(SimpleCallbackTest.this);
                }

                @Override
                public void populateInputs(final IScriptTaskInputs inputs) {
                    inputs.putString("putUuid", uuid);
                }

                @Override
                public void executeScript(final IScriptTaskEngine engine) {
                    engine.eval(new ClassPathResource(SimpleCallbackTest.class.getSimpleName() + ".py", SimpleCallbackTest.class));
                }

                @Override
                public Void extractResults(final IScriptTaskResults results) {
                    final String getSecretStaticImport = results.getString("getSecretStaticImport");
                    Assertions.assertThat(getSecretStaticImport).isEqualTo(secret);

                    final String getSecretStaticCallback = results.getString("getSecretStaticCallback");
                    Assertions.assertThat(getSecretStaticCallback).isEqualTo(secret);

                    final String getSecretCallback = results.getString("getSecretCallback");
                    Assertions.assertThat(getSecretCallback).isEqualTo(secret);

                    final String getSecretExpressionCallback = results.getString("getSecretExpressionCallback");
                    Assertions.assertThat(getSecretExpressionCallback).isEqualTo(secret);

                    Assertions.assertThat(voidMethodCalled).isEqualTo(1);
                    return null;
                }
            }.run(runner);
        } finally {
            UUID_SECRET.remove(uuid);
        }
    }

}
