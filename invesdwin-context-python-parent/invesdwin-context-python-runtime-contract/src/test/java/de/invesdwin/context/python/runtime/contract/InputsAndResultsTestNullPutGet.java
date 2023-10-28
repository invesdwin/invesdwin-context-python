package de.invesdwin.context.python.runtime.contract;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.core.io.ClassPathResource;

import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.integration.script.IScriptTaskInputs;
import de.invesdwin.context.integration.script.IScriptTaskResults;
import de.invesdwin.util.assertions.Assertions;
import de.invesdwin.util.math.decimal.scaled.Percent;

@NotThreadSafe
public class InputsAndResultsTestNullPutGet {

    private final IScriptTaskRunnerPython runner;

    public InputsAndResultsTestNullPutGet(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testNullPutGet() {
        new AScriptTaskPython<Void>() {

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {
                inputs.putBooleanVector("putBooleanVector", null);
                inputs.putBooleanVectorAsList("putBooleanVectorAsList", null);
                inputs.putBooleanMatrix("putBooleanMatrix", null);
                inputs.putBooleanMatrixAsList("putBooleanMatrixAsList", null);

                inputs.putByteVector("putByteVector", null);
                inputs.putByteVectorAsList("putByteVectorAsList", null);
                inputs.putByteMatrix("putByteMatrix", null);
                inputs.putByteMatrixAsList("putByteMatrixAsList", null);

                inputs.putCharacterVector("putCharacterVector", null);
                inputs.putCharacterVectorAsList("putCharacterVectorAsList", null);
                inputs.putCharacterMatrix("putCharacterMatrix", null);
                inputs.putCharacterMatrixAsList("putCharacterMatrixAsList", null);

                inputs.putDecimalVector("putDecimalVector", null);
                inputs.putDecimalVectorAsList("putDecimalVectorAsList", null);
                inputs.putDecimalMatrix("putDecimalMatrix", null);
                inputs.putDecimalMatrixAsList("putDecimalMatrixAsList", null);

                inputs.putDoubleVector("putDoubleVector", null);
                inputs.putDoubleVectorAsList("putDoubleVectorAsList", null);
                inputs.putDoubleMatrix("putDoubleMatrix", null);
                inputs.putDoubleMatrixAsList("putDoubleMatrixAsList", null);

                inputs.putFloatVector("putFloatVector", null);
                inputs.putFloatVectorAsList("putFloatVectorAsList", null);
                inputs.putFloatMatrix("putFloatMatrix", null);
                inputs.putFloatMatrixAsList("putFloatMatrixAsList", null);

                inputs.putIntegerVector("putIntegerVector", null);
                inputs.putIntegerVectorAsList("putIntegerVectorAsList", null);
                inputs.putIntegerMatrix("putIntegerMatrix", null);
                inputs.putIntegerMatrixAsList("putIntegerMatrixAsList", null);

                inputs.putLongVector("putLongVector", null);
                inputs.putLongVectorAsList("putLongVectorAsList", null);
                inputs.putLongMatrix("putLongMatrix", null);
                inputs.putLongMatrixAsList("putLongMatrixAsList", null);

                inputs.putDecimalVector("putPercentVector", null);
                inputs.putDecimalVectorAsList("putPercentVectorAsList", null);
                inputs.putDecimalMatrix("putPercentMatrix", null);
                inputs.putDecimalMatrixAsList("putPercentMatrixAsList", null);

                inputs.putShortVector("putShortVector", null);
                inputs.putShortVectorAsList("putShortVectorAsList", null);
                inputs.putShortMatrix("putShortMatrix", null);
                inputs.putShortMatrixAsList("putShortMatrixAsList", null);

                inputs.putStringVector("putStringVector", null);
                inputs.putStringVectorAsList("putStringVectorAsList", null);
                inputs.putStringMatrix("putStringMatrix", null);
                inputs.putStringMatrixAsList("putStringMatrixAsList", null);
            }

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                engine.eval(new ClassPathResource(InputsAndResultsTestNullPutGet.class.getSimpleName() + ".py",
                        InputsAndResultsTestNullPutGet.class));
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                Assertions.checkNull(results.getBooleanVector("getBooleanVector"));
                Assertions.checkNull(results.getBooleanVectorAsList("getBooleanVectorAsList"));
                Assertions.checkNull(results.getBooleanMatrix("getBooleanMatrix"));
                Assertions.checkNull(results.getBooleanMatrixAsList("getBooleanMatrixAsList"));

                Assertions.checkNull(results.getByteVector("getByteVector"));
                Assertions.checkNull(results.getByteVectorAsList("getByteVectorAsList"));
                Assertions.checkNull(results.getByteMatrix("getByteMatrix"));
                Assertions.checkNull(results.getByteMatrixAsList("getByteMatrixAsList"));

                Assertions.checkNull(results.getCharacterVector("getCharacterVector"));
                Assertions.checkNull(results.getCharacterVectorAsList("getCharacterVectorAsList"));
                Assertions.checkNull(results.getCharacterMatrix("getCharacterMatrix"));
                Assertions.checkNull(results.getCharacterMatrixAsList("getCharacterMatrixAsList"));

                Assertions.checkNull(results.getDecimalVector("getDecimalVector"));
                Assertions.checkNull(results.getDecimalVectorAsList("getDecimalVectorAsList"));
                Assertions.checkNull(results.getDecimalMatrix("getDecimalMatrix"));
                Assertions.checkNull(results.getDecimalMatrixAsList("getDecimalMatrixAsList"));

                Assertions.checkNull(results.getDoubleVector("getDoubleVector"));
                Assertions.checkNull(results.getDoubleVectorAsList("getDoubleVectorAsList"));
                Assertions.checkNull(results.getDoubleMatrix("getDoubleMatrix"));
                Assertions.checkNull(results.getDoubleMatrixAsList("getDoubleMatrixAsList"));

                Assertions.checkNull(results.getFloatVector("getFloatVector"));
                Assertions.checkNull(results.getFloatVectorAsList("getFloatVectorAsList"));
                Assertions.checkNull(results.getFloatMatrix("getFloatMatrix"));
                Assertions.checkNull(results.getFloatMatrixAsList("getFloatMatrixAsList"));

                Assertions.checkNull(results.getIntegerVector("getIntegerVector"));
                Assertions.checkNull(results.getIntegerVectorAsList("getIntegerVectorAsList"));
                Assertions.checkNull(results.getIntegerMatrix("getIntegerMatrix"));
                Assertions.checkNull(results.getIntegerMatrixAsList("getIntegerMatrixAsList"));

                Assertions.checkNull(results.getLongVector("getLongVector"));
                Assertions.checkNull(results.getLongVectorAsList("getLongVectorAsList"));
                Assertions.checkNull(results.getLongMatrix("getLongMatrix"));
                Assertions.checkNull(results.getLongMatrixAsList("getLongMatrixAsList"));

                Assertions.checkNull(results.getDecimalVector("getPercentVector", Percent.ZERO_PERCENT));
                Assertions.checkNull(results.getDecimalVectorAsList("getPercentVectorAsList", Percent.ZERO_PERCENT));
                Assertions.checkNull(results.getDecimalMatrix("getPercentMatrix", Percent.ZERO_PERCENT));
                Assertions.checkNull(results.getDecimalMatrixAsList("getPercentMatrixAsList", Percent.ZERO_PERCENT));

                Assertions.checkNull(results.getShortVector("getShortVector"));
                Assertions.checkNull(results.getShortVectorAsList("getShortVectorAsList"));
                Assertions.checkNull(results.getShortMatrix("getShortMatrix"));
                Assertions.checkNull(results.getShortMatrixAsList("getShortMatrixAsList"));

                Assertions.checkNull(results.getStringVector("getStringVector"));
                Assertions.checkNull(results.getStringVectorAsList("getStringVectorAsList"));
                Assertions.checkNull(results.getStringMatrix("getStringMatrix"));
                Assertions.checkNull(results.getStringMatrixAsList("getStringMatrixAsList"));
                return null;
            }
        }.run(runner);
    }

}
