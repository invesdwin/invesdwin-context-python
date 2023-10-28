package de.invesdwin.context.python.runtime.japyb;

import javax.annotation.concurrent.NotThreadSafe;

import org.junit.jupiter.api.Test;

import de.invesdwin.context.python.runtime.contract.InputsAndResultsTests;
import de.invesdwin.context.python.runtime.contract.callback.ParametersAndReturnsTests;
import de.invesdwin.context.test.ATest;
import jakarta.inject.Inject;

@NotThreadSafe
public class JapybScriptTaskRunnerPythonTest extends ATest {

    @Inject
    private JapybScriptTaskRunnerPython runner;

    @Test
    public void test() {
        new InputsAndResultsTests(runner).test();
    }

    @Test
    public void testParallel() {
        new InputsAndResultsTests(runner).testParallel();
    }

    @Test
    public void testCallback() {
        new ParametersAndReturnsTests(runner).test();
    }

    @Test
    public void testCallbackParallel() {
        new ParametersAndReturnsTests(runner).testParallel();
    }

    @Test
    public void testCallJava() {
        new CallJavaTest(runner).testCallJava();
    }

}
