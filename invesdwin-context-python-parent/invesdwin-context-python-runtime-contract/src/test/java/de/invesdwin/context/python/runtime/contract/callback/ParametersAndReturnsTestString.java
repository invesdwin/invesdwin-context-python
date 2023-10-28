package de.invesdwin.context.python.runtime.contract.callback;

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
import de.invesdwin.context.integration.script.callback.IScriptTaskCallback;
import de.invesdwin.context.integration.script.callback.ReflectiveScriptTaskCallback;
import de.invesdwin.context.python.runtime.contract.AScriptTaskPython;
import de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython;
import de.invesdwin.util.assertions.Assertions;
import de.invesdwin.util.collections.Arrays;
import de.invesdwin.util.lang.Objects;

@NotThreadSafe
public class ParametersAndReturnsTestString {

    private final IScriptTaskRunnerPython runner;

    public ParametersAndReturnsTestString(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testString() {
        new AScriptTaskPython<Void>() {

            @Override
            public IScriptTaskCallback getCallback() {
                final ParametersAndReturnsTestStringCallback callback = new ParametersAndReturnsTestStringCallback();
                return new ReflectiveScriptTaskCallback(callback);
            }

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {}

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                final ClassPathResource resource = new ClassPathResource(
                        ParametersAndReturnsTestString.class.getSimpleName() + ".py",
                        ParametersAndReturnsTestString.class);
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
                return null;
            }
        }.run(runner);
    }

    public static class ParametersAndReturnsTestStringCallback {

        //putString
        private final String putString;
        private final String putStringWithNull;

        //putStringVector
        private final String[] putStringVector;
        private final String[] putStringVectorWithNull;

        //putStringVectorAsList
        private final List<String> putStringVectorAsList;
        private final List<String> putStringVectorAsListWithNull;

        //putStringMatrix
        private final String[][] putStringMatrix;
        private final String[][] putStringMatrixWithNull;

        //putStringMatrixAsList
        private final List<List<String>> putStringMatrixAsList;
        private final List<List<String>> putStringMatrixAsListWithNull;

        public ParametersAndReturnsTestStringCallback() {
            //putString
            this.putString = "asdf";
            this.putStringWithNull = null;

            //putStringVector
            this.putStringVector = new String[3];
            for (int i = 0; i < putStringVector.length; i++) {
                putStringVector[i] = i + "-" + i;
            }
            this.putStringVectorWithNull = Objects.deepClone(putStringVector);
            putStringVectorWithNull[1] = null;

            //putStringVectorAsList
            this.putStringVectorAsList = Arrays.asList(putStringVector);
            this.putStringVectorAsListWithNull = Objects.deepClone(putStringVectorAsList);
            putStringVectorAsListWithNull.set(1, null);

            //putStringMatrix
            this.putStringMatrix = new String[4][];
            for (int i = 0; i < putStringMatrix.length; i++) {
                final String[] vector = new String[3];
                for (int j = 0; j < vector.length; j++) {
                    vector[j] = i + "" + j + "-" + i + "" + j;
                }
                putStringMatrix[i] = vector;
            }
            this.putStringMatrixWithNull = Objects.deepClone(putStringMatrix);
            for (int i = 0; i < putStringMatrixWithNull[0].length; i++) {
                putStringMatrixWithNull[i][i] = null;
            }

            //putStringMatrixAsList
            this.putStringMatrixAsList = new ArrayList<List<String>>(putStringMatrix.length);
            for (final String[] vector : putStringMatrix) {
                putStringMatrixAsList.add(Arrays.asList(vector));
            }
            this.putStringMatrixAsListWithNull = Objects.deepClone(putStringMatrixAsList);
            for (int i = 0; i < putStringMatrixAsListWithNull.get(0).size(); i++) {
                putStringMatrixAsListWithNull.get(i).set(i, null);
            }
        }

        public String getString() {
            return putString;
        }

        public void setString(final String putString) {
            Assertions.assertThat(this.putString).isEqualTo(putString);
        }

        public String getStringWithNull() {
            return putStringWithNull;
        }

        public void setStringWithNull(final String putStringWithNull) {
            Assertions.assertThat(this.putStringWithNull).isEqualTo(putStringWithNull);
        }

        public String[] getStringVector() {
            return putStringVector;
        }

        public void setStringVector(final String[] putStringVector) {
            Assertions.assertThat(this.putStringVector).isEqualTo(putStringVector);
        }

        public String[] getStringVectorWithNull() {
            return putStringVectorWithNull;
        }

        public void setStringVectorWithNull(final String[] putStringVectorWithNull) {
            Assertions.assertThat(this.putStringVectorWithNull).isEqualTo(putStringVectorWithNull);
        }

        public List<String> getStringVectorAsList() {
            return putStringVectorAsList;
        }

        public void setStringVectorAsList(final List<String> putStringVectorAsList) {
            Assertions.assertThat(this.putStringVectorAsList).isEqualTo(putStringVectorAsList);
        }

        public List<String> getStringVectorAsListWithNull() {
            return putStringVectorAsListWithNull;
        }

        public void setStringVectorAsListWithNull(final List<String> putStringVectorAsListWithNull) {
            Assertions.assertThat(this.putStringVectorAsListWithNull).isEqualTo(putStringVectorAsListWithNull);
        }

        public String[][] getStringMatrix() {
            return putStringMatrix;
        }

        public void setStringMatrix(final String[][] putStringMatrix) {
            Assertions.assertThat(this.putStringMatrix).isEqualTo(putStringMatrix);
        }

        public String[][] getStringMatrixWithNull() {
            return putStringMatrixWithNull;
        }

        public void setStringMatrixWithNull(final String[][] putStringMatrixWithNull) {
            Assertions.assertThat(this.putStringMatrixWithNull).isEqualTo(putStringMatrixWithNull);
        }

        public List<List<String>> getStringMatrixAsList() {
            return putStringMatrixAsList;
        }

        public void setStringMatrixAsList(final List<List<String>> putStringMatrixAsList) {
            Assertions.assertThat(this.putStringMatrixAsList).isEqualTo(putStringMatrixAsList);
        }

        public List<List<String>> getStringMatrixAsListWithNull() {
            return putStringMatrixAsListWithNull;
        }

        public void setStringMatrixAsListWithNull(final List<List<String>> putStringMatrixAsListWithNull) {
            Assertions.assertThat(this.putStringMatrixAsListWithNull).isEqualTo(putStringMatrixAsListWithNull);
        }

    }

}
