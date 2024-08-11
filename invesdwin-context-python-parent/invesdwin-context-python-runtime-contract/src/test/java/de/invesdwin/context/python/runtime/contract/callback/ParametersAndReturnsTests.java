package de.invesdwin.context.python.runtime.contract.callback;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.util.concurrent.Executors;
import de.invesdwin.util.concurrent.WrappedExecutorService;
import de.invesdwin.util.concurrent.future.Futures;

@NotThreadSafe
public class ParametersAndReturnsTests {

    private final IScriptTaskRunnerPython runner;

    public ParametersAndReturnsTests(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void test() {
        new ParametersAndReturnsTestByte(runner).testByte();
        new ParametersAndReturnsTestFloat(runner).testFloat();
        new ParametersAndReturnsTestDouble(runner).testDouble();
        new ParametersAndReturnsTestDecimal(runner).testDecimal();
        new ParametersAndReturnsTestPercent(runner).testPercent();
        new ParametersAndReturnsTestShort(runner).testShort();
        new ParametersAndReturnsTestInteger(runner).testInteger();
        new ParametersAndReturnsTestLong(runner).testLong();
        new ParametersAndReturnsTestCharacter(runner).testCharacter();
        new ParametersAndReturnsTestString(runner).testString();
        new ParametersAndReturnsTestBoolean(runner).testBoolean();
        new ParametersAndReturnsTestNullPutGet(runner).testNullPutGet();
        new ParametersAndReturnsTestEmpty(runner).testEmpty();
        new ParametersAndReturnsTestEmptyMatrixValue(runner).testEmptyMatrixValue();
        new ParametersAndReturnsTestDoubleNan(runner).testDoubleNan();
    }

    public void testParallel() {
        final List<Runnable> tasks = new ArrayList<Runnable>();
        for (int i = 0; i < 10; i++) {
            tasks.add(new Runnable() {
                @Override
                public void run() {
                    test();
                }
            });
        }
        final WrappedExecutorService executor = Executors.newFixedThreadPool(
                ParametersAndReturnsTests.class.getSimpleName() + "_testParallel",
                Runtime.getRuntime().availableProcessors());
        try {
            Futures.submitAndWait(executor, tasks);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
        }
    }

}
