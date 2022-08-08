package de.invesdwin.context.python.runtime.contract;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.integration.script.IScriptTaskInputs;
import de.invesdwin.context.integration.script.IScriptTaskResults;
import de.invesdwin.util.assertions.Assertions;
import de.invesdwin.util.collections.Arrays;
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
        final String putStringWithNull = null;

        //putStringVector
        final String[] putStringVector = new String[3];
        for (int i = 0; i < putStringVector.length; i++) {
            putStringVector[i] = i + "-" + i;
        }
        final String[] putStringVectorWithNull = Objects.deepClone(putStringVector);
        putStringVectorWithNull[1] = null;

        //putStringVectorAsList
        final List<String> putStringVectorAsList = Arrays.asList(putStringVector);
        final List<String> putStringVectorAsListWithNull = Objects.deepClone(putStringVectorAsList);
        putStringVectorAsListWithNull.set(1, null);

        //putStringMatrix
        final String[][] putStringMatrix = new String[4][];
        for (int i = 0; i < putStringMatrix.length; i++) {
            final String[] vector = new String[3];
            for (int j = 0; j < vector.length; j++) {
                vector[j] = i + "" + j + "-" + i + "" + j;
            }
            putStringMatrix[i] = vector;
        }
        final String[][] putStringMatrixWithNull = Objects.deepClone(putStringMatrix);
        for (int i = 0; i < putStringMatrixWithNull[0].length; i++) {
            putStringMatrixWithNull[i][i] = null;
        }

        //putStringMatrixAsList
        final List<List<String>> putStringMatrixAsList = new ArrayList<List<String>>(putStringMatrix.length);
        for (final String[] vector : putStringMatrix) {
            putStringMatrixAsList.add(Arrays.asList(vector));
        }
        final List<List<String>> putStringMatrixAsListWithNull = Objects.deepClone(putStringMatrixAsList);
        for (int i = 0; i < putStringMatrixAsListWithNull.get(0).size(); i++) {
            putStringMatrixAsListWithNull.get(i).set(i, null);
        }

        new AScriptTaskPython<Void>() {

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {
                inputs.putString("putString", putString);
                inputs.putString("putStringWithNull", putStringWithNull);

                inputs.putStringVector("putStringVector", putStringVector);
                inputs.putStringVector("putStringVectorWithNull", putStringVectorWithNull);

                inputs.putStringVectorAsList("putStringVectorAsList", putStringVectorAsList);
                inputs.putStringVectorAsList("putStringVectorAsListWithNull", putStringVectorAsListWithNull);

                inputs.putStringMatrix("putStringMatrix", putStringMatrix);
                inputs.putStringMatrix("putStringMatrixWithNull", putStringMatrixWithNull);

                inputs.putStringMatrixAsList("putStringMatrixAsList", putStringMatrixAsList);
                inputs.putStringMatrixAsList("putStringMatrixAsListWithNull", putStringMatrixAsListWithNull);
            }

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                final ClassPathResource resource = new ClassPathResource(
                        InputsAndResultsTestString.class.getSimpleName() + ".py", InputsAndResultsTestString.class);
                try (InputStream in = resource.getInputStream()) {
                    String str = IOUtils.toString(in, StandardCharsets.UTF_8);
                    engine.eval("import sys");
                    if (engine.getResults().getBoolean("sys.version_info >= (3, 0)")) {
                        str = str.replace("unicode", "str");
                    }
                    engine.eval(str);
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public Void extractResults(final IScriptTaskResults results) {
                //getString
                final String getString = results.getString("getString");
                Assertions.assertThat(putString).isEqualTo(getString);
                final String getStringWithNull = results.getString("getStringWithNull");
                Assertions.assertThat(putStringWithNull).isEqualTo(getStringWithNull);

                //getStringVector
                final String[] getStringVector = results.getStringVector("getStringVector");
                Assertions.assertThat(putStringVector).isEqualTo(getStringVector);
                final String[] getStringVectorWithNull = results.getStringVector("getStringVectorWithNull");
                Assertions.assertThat(putStringVectorWithNull).isEqualTo(getStringVectorWithNull);

                //getStringVectorAsList
                final List<String> getStringVectorAsList = results.getStringVectorAsList("getStringVectorAsList");
                Assertions.assertThat(putStringVectorAsList).isEqualTo(getStringVectorAsList);
                final List<String> getStringVectorAsListWithNull = results
                        .getStringVectorAsList("getStringVectorAsListWithNull");
                Assertions.assertThat(putStringVectorAsListWithNull).isEqualTo(getStringVectorAsListWithNull);

                //getStringMatrix
                final String[][] getStringMatrix = results.getStringMatrix("getStringMatrix");
                Assertions.assertThat(putStringMatrix).isEqualTo(getStringMatrix);
                final String[][] getStringMatrixWithNull = results.getStringMatrix("getStringMatrixWithNull");
                Assertions.assertThat(putStringMatrixWithNull).isEqualTo(getStringMatrixWithNull);

                //getStringMatrixAsList
                final List<List<String>> getStringMatrixAsList = results.getStringMatrixAsList("getStringMatrixAsList");
                Assertions.assertThat(putStringMatrixAsList).isEqualTo(getStringMatrixAsList);
                final List<List<String>> getStringMatrixAsListWithNull = results
                        .getStringMatrixAsList("getStringMatrixAsListWithNull");
                Assertions.assertThat(putStringMatrixAsListWithNull).isEqualTo(getStringMatrixAsListWithNull);
                return null;
            }
        }.run(runner);
    }

}
