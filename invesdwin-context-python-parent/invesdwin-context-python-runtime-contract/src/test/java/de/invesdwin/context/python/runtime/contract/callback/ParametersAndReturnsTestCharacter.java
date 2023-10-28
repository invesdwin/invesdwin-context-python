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
import de.invesdwin.util.math.Characters;

@NotThreadSafe
public class ParametersAndReturnsTestCharacter {

    private final IScriptTaskRunnerPython runner;

    public ParametersAndReturnsTestCharacter(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testCharacter() {
        new AScriptTaskPython<Void>() {

            @Override
            public IScriptTaskCallback getCallback() {
                final ParametersAndReturnsTestCharacterCallback callback = new ParametersAndReturnsTestCharacterCallback();
                return new ReflectiveScriptTaskCallback(callback);
            }

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {}

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                final ClassPathResource resource = new ClassPathResource(
                        ParametersAndReturnsTestCharacter.class.getSimpleName() + ".py",
                        ParametersAndReturnsTestCharacter.class);
                try (InputStream in = resource.getInputStream()) {
                    String str = IOUtils.toString(in, StandardCharsets.UTF_8);
                    engine.eval("import sys");
                    if (engine.getResults().getBoolean("sys.version_info >= (3, 0)")) {
                        str = str.replace("str", "bytes").replace("unicode", "str");
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

    public static class ParametersAndReturnsTestCharacterCallback {

        //putCharacter
        private final char putCharacter;

        //putCharacterVector
        private final char[] putCharacterVector;

        //putCharacterVectorAsList
        private final List<Character> putCharacterVectorAsList;

        //putCharacterMatrix
        private final char[][] putCharacterMatrix;

        //putCharacterMatrixAsList
        private final List<List<Character>> putCharacterMatrixAsList;

        public ParametersAndReturnsTestCharacterCallback() {
            //putCharacter
            this.putCharacter = 'a';

            //putCharacterVector
            this.putCharacterVector = new char[3];
            for (int i = 0; i < putCharacterVector.length; i++) {
                putCharacterVector[i] = Characters.checkedCast('A' + i);
            }

            //putCharacterVectorAsList
            this.putCharacterVectorAsList = Characters.asList(putCharacterVector);

            //putCharacterMatrix
            this.putCharacterMatrix = new char[4][];
            for (int i = 0; i < putCharacterMatrix.length; i++) {
                final char[] vector = new char[3];
                for (int j = 0; j < vector.length; j++) {
                    vector[j] = Characters.checkedCast('A' + i + j);
                }
                putCharacterMatrix[i] = vector;
            }

            //putCharacterMatrixAsList
            this.putCharacterMatrixAsList = new ArrayList<List<Character>>(putCharacterMatrix.length);
            for (final char[] vector : putCharacterMatrix) {
                putCharacterMatrixAsList.add(Characters.asList(vector));
            }
        }

        public char getCharacter() {
            return putCharacter;
        }

        public void setCharacter(final char putCharacter) {
            Assertions.assertThat(this.putCharacter).isEqualTo(putCharacter);
        }

        public char[] getCharacterVector() {
            return putCharacterVector;
        }

        public void setCharacterVector(final char[] putCharacterVector) {
            Assertions.assertThat(this.putCharacterVector).isEqualTo(putCharacterVector);
        }

        public List<Character> getCharacterVectorAsList() {
            return putCharacterVectorAsList;
        }

        public void setCharacterVectorAsList(final List<Character> putCharacterVectorAsList) {
            Assertions.assertThat(this.putCharacterVectorAsList).isEqualTo(putCharacterVectorAsList);
        }

        public char[][] getCharacterMatrix() {
            return putCharacterMatrix;
        }

        public void setCharacterMatrix(final char[][] putCharacterMatrix) {
            Assertions.assertThat(this.putCharacterMatrix).isEqualTo(putCharacterMatrix);
        }

        public List<List<Character>> getCharacterMatrixAsList() {
            return putCharacterMatrixAsList;
        }

        public void setCharacterMatrixAsList(final List<List<Character>> putCharacterMatrixAsList) {
            Assertions.assertThat(this.putCharacterMatrixAsList).isEqualTo(putCharacterMatrixAsList);
        }

    }

}
