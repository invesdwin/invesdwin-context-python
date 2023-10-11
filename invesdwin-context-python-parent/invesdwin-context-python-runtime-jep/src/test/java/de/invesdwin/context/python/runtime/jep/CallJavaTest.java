package de.invesdwin.context.python.runtime.jep;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.core.io.ClassPathResource;

import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.integration.script.IScriptTaskInputs;
import de.invesdwin.context.integration.script.IScriptTaskResults;
import de.invesdwin.context.python.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.util.assertions.Assertions;
import de.invesdwin.util.lang.UUIDs;

@NotThreadSafe
public class CallJavaTest {

    private static final Map<String, String> UUID_SECRET = new ConcurrentHashMap<>();

    private final IScriptTaskRunnerPython runner;

    public CallJavaTest(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public static String getSecret(final String uuid) {
        return UUID_SECRET.get(uuid);
    }

    public void testCallJava() {
        final String uuid = UUIDs.newPseudoRandomUUID();
        final String secret = "secret123";
        UUID_SECRET.put(uuid, secret);
        try {
            new AScriptTaskPython<Void>() {

                @Override
                public void populateInputs(final IScriptTaskInputs inputs) {
                    inputs.putString("putUuid", uuid);
                }

                @Override
                public void executeScript(final IScriptTaskEngine engine) {
                    engine.eval(new ClassPathResource(CallJavaTest.class.getSimpleName() + ".py", CallJavaTest.class));
                }

                @Override
                public Void extractResults(final IScriptTaskResults results) {
                    final String getSecret = results.getString("getSecret");
                    Assertions.assertThat(getSecret).isEqualTo(secret);
                    return null;
                }
            }.run(runner);
        } finally {
            UUID_SECRET.remove(uuid);
        }
    }

}
