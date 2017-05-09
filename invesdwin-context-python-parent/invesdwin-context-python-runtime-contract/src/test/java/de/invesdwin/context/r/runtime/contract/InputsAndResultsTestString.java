package de.invesdwin.context.r.runtime.contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import de.invesdwin.context.integration.script.IScriptTaskInputs;
import de.invesdwin.context.integration.script.IScriptTaskResults;
import de.invesdwin.util.assertions.Assertions;
import de.invesdwin.util.lang.Objects;

@NotThreadSafe
public class InputsAndResultsTestString {

    private final IScriptTaskRunnerPython runner;

    public InputsAndResultsTestString(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testString() {
        //putString
        final String putString = "asdf";
        final String putStringNull = null;

        //putStringVector
        final String[] putStringVector = new String[3];
        for (int i = 0; i < putStringVector.length; i++) {
            putStringVector[i] = i + "-" + i;
        }
        final String[] putStringVectorNull = Objects.clone(putStringVector);
        putStringVectorNull[1] = null;

        //putStringVectorAsList
        final List<String> putStringVectorAsList = Arrays.asList(putStringVector);
        final List<String> putStringVectorAsListNull = Objects.clone(putStringVectorAsList);
        putStringVectorAsListNull.set(1, null);

        //putStringMatrix
        final String[][] putStringMatrix = new String[4][];
        for (int i = 0; i < putStringMatrix.length; i++) {
            final String[] vector = new String[3];
            for (int j = 0; j < vector.length; j++) {
                vector[j] = i + "" + j + "-" + i + "" + j;
            }
            putStringMatrix[i] = vector;
        }
        final String[][] putStringMatrixNull = Objects.clone(putStringMatrix);
        for (int i = 0; i < putStringMatrixNull[0].length; i++) {
            putStringMatrixNull[i][i] = null;
        }

        //putStringMatrixAsList
        final List<List<String>> putStringMatrixAsList = new ArrayList<List<String>>(putStringMatrix.length);
        for (final String[] vector : putStringMatrix) {
            putStringMatrixAsList.add(Arrays.asList(vector));
        }
        final List<List<String>> putStringMatrixAsListNull = Objects.clone(putStringMatrixAsList);
        for (int i = 0; i < putStringMatrixAsListNull.get(0).size(); i++) {
            putStringMatrixAsListNull.get(i).set(i, null);
        }

        new AScriptTaskPython<Void>() {

            @Override
            public Resource getScriptResource() {
                return new ClassPathResource(InputsAndResultsTestString.class.getSimpleName() + ".R",
                        InputsAndResultsTestString.class);
            }

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {
                inputs.putString("putString", putString);
                inputs.putString("putStringNull", putStringNull);

                inputs.putStringVector("putStringVector", putStringVector);
                inputs.putStringVector("putStringVectorNull", putStringVectorNull);

                inputs.putStringVectorAsList("putStringVectorAsList", putStringVectorAsList);
                inputs.putStringVectorAsList("putStringVectorAsListNull", putStringVectorAsListNull);

                inputs.putStringMatrix("putStringMatrix", putStringMatrix);
                inputs.putStringMatrix("putStringMatrixNull", putStringMatrixNull);

                inputs.putStringMatrixAsList("putStringMatrixAsList", putStringMatrixAsList);
                inputs.putStringMatrixAsList("putStringMatrixAsListNull", putStringMatrixAsListNull);
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                //getString
                final String getString = results.getString("getString");
                Assertions.assertThat(putString).isEqualTo(getString);
                final String getStringNull = results.getString("getStringNull");
                Assertions.assertThat(putStringNull).isEqualTo(getStringNull);

                //getStringVector
                final String[] getStringVector = results.getStringVector("getStringVector");
                Assertions.assertThat(putStringVector).isEqualTo(getStringVector);
                final String[] getStringVectorNull = results.getStringVector("getStringVectorNull");
                Assertions.assertThat(putStringVectorNull).isEqualTo(getStringVectorNull);

                //getStringVectorAsList
                final List<String> getStringVectorAsList = results.getStringVectorAsList("getStringVectorAsList");
                Assertions.assertThat(putStringVectorAsList).isEqualTo(getStringVectorAsList);
                final List<String> getStringVectorAsListNull = results
                        .getStringVectorAsList("getStringVectorAsListNull");
                Assertions.assertThat(putStringVectorAsListNull).isEqualTo(getStringVectorAsListNull);

                //getStringMatrix
                final String[][] getStringMatrix = results.getStringMatrix("getStringMatrix");
                Assertions.assertThat(putStringMatrix).isEqualTo(getStringMatrix);
                final String[][] getStringMatrixNull = results.getStringMatrix("getStringMatrixNull");
                Assertions.assertThat(putStringMatrixNull).isEqualTo(getStringMatrixNull);

                //getStringMatrixAsList
                final List<List<String>> getStringMatrixAsList = results.getStringMatrixAsList("getStringMatrixAsList");
                Assertions.assertThat(putStringMatrixAsList).isEqualTo(getStringMatrixAsList);
                final List<List<String>> getStringMatrixAsListNull = results
                        .getStringMatrixAsList("getStringMatrixAsListNull");
                Assertions.assertThat(putStringMatrixAsListNull).isEqualTo(getStringMatrixAsListNull);
                return null;
            }
        }.run(runner);
    }

}
