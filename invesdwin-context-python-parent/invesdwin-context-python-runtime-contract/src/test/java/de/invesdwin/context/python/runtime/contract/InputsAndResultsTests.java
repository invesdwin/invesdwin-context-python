package de.invesdwin.context.python.runtime.contract;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.python.runtime.contract.hello.HelloWorldScript;
import de.invesdwin.util.concurrent.Executors;
import de.invesdwin.util.concurrent.WrappedExecutorService;
import de.invesdwin.util.concurrent.future.Futures;

@NotThreadSafe
public class InputsAndResultsTests {

    private final IScriptTaskRunnerPython runner;

    public InputsAndResultsTests(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void test() {
        new HelloWorldScript(runner).testHelloWorld();
        new InputsAndResultsTestByte(runner).testByte();
        new InputsAndResultsTestFloat(runner).testFloat();
        new InputsAndResultsTestDouble(runner).testDouble();
        new InputsAndResultsTestDecimal(runner).testDecimal();
        new InputsAndResultsTestPercent(runner).testPercent();
        new InputsAndResultsTestShort(runner).testShort();
        new InputsAndResultsTestInteger(runner).testInteger();
        new InputsAndResultsTestLong(runner).testLong();
        new InputsAndResultsTestCharacter(runner).testCharacter();
        new InputsAndResultsTestString(runner).testString();
        new InputsAndResultsTestBoolean(runner).testBoolean();
        new InputsAndResultsTestNull(runner).testNull();
        new InputsAndResultsTestNullPutGet(runner).testNullPutGet();
        new InputsAndResultsTestEmpty(runner).testEmpty();
        new InputsAndResultsTestEmptyMatrixValue(runner).testEmptyMatrixValue();
        new InputsAndResultsTestDoubleNan(runner).testDoubleNan();
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
                InputsAndResultsTests.class.getSimpleName() + "_testParallel",
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
