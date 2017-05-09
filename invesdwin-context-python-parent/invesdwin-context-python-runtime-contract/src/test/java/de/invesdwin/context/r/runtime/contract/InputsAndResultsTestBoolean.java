package de.invesdwin.context.r.runtime.contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import de.invesdwin.context.integration.script.IScriptTaskInputs;
import de.invesdwin.context.integration.script.IScriptTaskResults;
import de.invesdwin.util.assertions.Assertions;

@NotThreadSafe
public class InputsAndResultsTestBoolean {

    private final IScriptTaskRunnerPython runner;

    public InputsAndResultsTestBoolean(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testBoolean() {
        //putBoolean
        final boolean putBoolean = true;

        //putBooleanVector
        final boolean[] putBooleanVector = new boolean[3];
        for (int i = 0; i < putBooleanVector.length; i++) {
            putBooleanVector[i] = i % 2 == 0;
        }

        //putBooleanVectorAsList
        final List<Boolean> putBooleanVectorAsList = Arrays.asList(ArrayUtils.toObject(putBooleanVector));

        //putBooleanMatrix
        final boolean[][] putBooleanMatrix = new boolean[4][];
        for (int i = 0; i < putBooleanMatrix.length; i++) {
            final boolean[] vector = new boolean[3];
            for (int j = 0; j < vector.length; j++) {
                vector[j] = j % 2 == 0;
            }
            putBooleanMatrix[i] = vector;
        }

        //putBooleanMatrixAsList
        final List<List<Boolean>> putBooleanMatrixAsList = new ArrayList<List<Boolean>>(putBooleanMatrix.length);
        for (final boolean[] vector : putBooleanMatrix) {
            putBooleanMatrixAsList.add(Arrays.asList(ArrayUtils.toObject(vector)));
        }

        new AScriptTaskPython<Void>() {

            @Override
            public Resource getScriptResource() {
                return new ClassPathResource(InputsAndResultsTestBoolean.class.getSimpleName() + ".R",
                        InputsAndResultsTestBoolean.class);
            }

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {
                inputs.putBoolean("putBoolean", putBoolean);

                inputs.putBooleanVector("putBooleanVector", putBooleanVector);

                inputs.putBooleanVectorAsList("putBooleanVectorAsList", putBooleanVectorAsList);

                inputs.putBooleanMatrix("putBooleanMatrix", putBooleanMatrix);

                inputs.putBooleanMatrixAsList("putBooleanMatrixAsList", putBooleanMatrixAsList);
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                //getBoolean
                final Boolean getBoolean = results.getBoolean("getBoolean");
                Assertions.assertThat(putBoolean).isEqualTo(getBoolean);

                //getBooleanVector
                final boolean[] getBooleanVector = results.getBooleanVector("getBooleanVector");
                Assertions.assertThat(putBooleanVector).isEqualTo(getBooleanVector);

                //getBooleanVectorAsList
                final List<Boolean> getBooleanVectorAsList = results.getBooleanVectorAsList("getBooleanVectorAsList");
                Assertions.assertThat(putBooleanVectorAsList).isEqualTo(getBooleanVectorAsList);

                //getBooleanMatrix
                final boolean[][] getBooleanMatrix = results.getBooleanMatrix("getBooleanMatrix");
                Assertions.assertThat(putBooleanMatrix).isEqualTo(getBooleanMatrix);

                //getBooleanMatrixAsList
                final List<List<Boolean>> getBooleanMatrixAsList = results
                        .getBooleanMatrixAsList("getBooleanMatrixAsList");
                Assertions.assertThat(putBooleanMatrixAsList).isEqualTo(getBooleanMatrixAsList);
                return null;
            }
        }.run(runner);
    }

}
