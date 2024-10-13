package de.invesdwin.context.python.runtime.japyb;

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
import de.invesdwin.util.math.decimal.Decimal;

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

    public double callManyParams(final boolean p1, final byte p2, final short p3, final char p4, final int p5,
            final long p6, final float p7, final double p8, final String p9, final Decimal p10) {
        return (p1 ? 1 : 0) + p2 + p3 + Double.parseDouble(String.valueOf(p4)) + p5 + p6 + p7 + p8 + p9.length()
                + p10.doubleValue();
    }

    @ReturnExpression
    public String callManyParamsExpression(final boolean p1, final byte p2, final short p3, final char p4, final int p5,
            final long p6, final float p7, final double p8, final String p9, final Decimal p10) {
        final StringBuilder expression = new StringBuilder();
        expression.append(p1 ? 1 : 0);
        expression.append(" + ");
        expression.append(p2);
        expression.append(" + ");
        expression.append(p3);
        expression.append(" + ");
        expression.append(Double.parseDouble(String.valueOf(p4)));
        expression.append(" + ");
        expression.append(p5);
        expression.append(" + ");
        expression.append(p6);
        expression.append(" + ");
        expression.append(p7);
        expression.append(" + ");
        expression.append(p8);
        expression.append(" + ");
        expression.append(p9.length());
        expression.append(" + ");
        expression.append(p10.doubleValue());
        return expression.toString();
    }

    @ReturnExpression
    public String callManyParamsExpressionMultiline(final boolean p1, final byte p2, final short p3, final char p4,
            final int p5, final long p6, final float p7, final double p8, final String p9, final Decimal p10) {
        final StringBuilder expression = new StringBuilder("value = ");
        expression.append(p1 ? 1 : 0);
        expression.append("\nvalue += ");
        expression.append(p2);
        expression.append("\nvalue += ");
        expression.append(p3);
        expression.append("\nvalue += ");
        expression.append(Double.parseDouble(String.valueOf(p4)));
        expression.append("\nvalue += ");
        expression.append(p5);
        expression.append("\nvalue += ");
        expression.append(p6);
        expression.append("\nvalue += ");
        expression.append(p7);
        expression.append("\nvalue += ");
        expression.append(p8);
        expression.append("\nvalue += ");
        expression.append(p9.length());
        expression.append("\nvalue += ");
        expression.append(p10.doubleValue());
        expression.append("\nvalue");
        return expression.toString();
    }

    private String putManyParamsExpressionMultiline(final String variable, final boolean p1, final byte p2,
            final short p3, final char p4, final int p5, final long p6, final float p7, final double p8,
            final String p9, final Decimal p10) {
        final StringBuilder expression = new StringBuilder(variable + " = ");
        expression.append(p1 ? 1 : 0);
        expression.append("\n" + variable + " += ");
        expression.append(p2);
        expression.append("\n" + variable + " += ");
        expression.append(p3);
        expression.append("\n" + variable + " += ");
        expression.append(Double.parseDouble(String.valueOf(p4)));
        expression.append("\n" + variable + " += ");
        expression.append(p5);
        expression.append("\n" + variable + " += ");
        expression.append(p6);
        expression.append("\n" + variable + " += ");
        expression.append(p7);
        expression.append("\n" + variable + " += ");
        expression.append(p8);
        expression.append("\n" + variable + " += ");
        expression.append(p9.length());
        expression.append("\n" + variable + " += ");
        expression.append(p10.doubleValue());
        return expression.toString();
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
                    inputs.putExpression("putManyParamsExpression", callManyParamsExpression(true, (byte) 2, (short) 3,
                            '4', 5, 6L, 7f, 8.0, "123456789", new Decimal("10")));
                    inputs.putExpression("putManyParamsExpressionMultilineWrong",
                            putManyParamsExpressionMultiline("putManyParamsExpressionMultilineWrong", true, (byte) 2,
                                    (short) 3, '4', 5, 6L, 7f, 8.0, "123456789", new Decimal("10")));
                    inputs.getEngine()
                            .eval(putManyParamsExpressionMultiline("putManyParamsExpressionMultiline", true, (byte) 2,
                                    (short) 3, '4', 5, 6L, 7f, 8.0, "123456789", new Decimal("10")));
                }

                @Override
                public void executeScript(final IScriptTaskEngine engine) {
                    engine.eval(new ClassPathResource(SimpleCallbackTest.class.getSimpleName() + ".py",
                            SimpleCallbackTest.class));
                }

                @Override
                public Void extractResults(final IScriptTaskResults results) {
                    final String getSecretStaticCallback = results.getString("getSecretStaticCallback");
                    Assertions.assertThat(getSecretStaticCallback).isEqualTo(secret);

                    final String getSecretCallback = results.getString("getSecretCallback");
                    Assertions.assertThat(getSecretCallback).isEqualTo(secret);

                    final String getSecretExpressionCallback = results.getString("getSecretExpressionCallback");
                    Assertions.assertThat(getSecretExpressionCallback).isEqualTo(secret);

                    Assertions.assertThat(voidMethodCalled).isEqualTo(1);

                    final double getManyParamsExpression = results.getDouble("getManyParamsExpression");
                    Assertions.assertThat(getManyParamsExpression).isEqualTo(55.0);

                    final double getManyParamsExpressionMultilineWrong = results
                            .getDouble("getManyParamsExpressionMultilineWrong");
                    Assertions.assertThat(getManyParamsExpressionMultilineWrong).isEqualTo(55.0);

                    final double getManyParamsExpressionMultiline = results
                            .getDouble("getManyParamsExpressionMultiline");
                    Assertions.assertThat(getManyParamsExpressionMultiline).isEqualTo(55.0);
                    return null;
                }
            }.run(runner);
        } finally {
            UUID_SECRET.remove(uuid);
        }
    }

}
