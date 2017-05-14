package de.invesdwin.context.python.runtime.py4j;

import javax.annotation.concurrent.NotThreadSafe;
import javax.inject.Inject;

import org.junit.Test;

import de.invesdwin.context.r.runtime.contract.InputsAndResultsTests;
import de.invesdwin.context.test.ATest;

@NotThreadSafe
public class Py4jScriptTaskRunnerPythonTest extends ATest {

    @Inject
    private Py4jScriptTaskRunnerPython runner;

    @Test
    public void test() {
        new InputsAndResultsTests(runner).test();
    }

    @Test
    public void testParallel() {
        new InputsAndResultsTests(runner).testParallel();
    }

}
