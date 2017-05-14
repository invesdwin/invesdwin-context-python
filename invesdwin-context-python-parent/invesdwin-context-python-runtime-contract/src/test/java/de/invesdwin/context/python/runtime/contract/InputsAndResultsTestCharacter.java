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
import de.invesdwin.util.math.Characters;

@NotThreadSafe
public class InputsAndResultsTestCharacter {

    private final IScriptTaskRunnerPython runner;

    public InputsAndResultsTestCharacter(final IScriptTaskRunnerPython runner) {
        this.runner = runner;
    }

    public void testCharacter() {
        //putCharacter
        final char putCharacter = 'a';

        //putCharacterVector
        final char[] putCharacterVector = new char[3];
        for (int i = 0; i < putCharacterVector.length; i++) {
            putCharacterVector[i] = Characters.checkedCast('A' + i);
        }

        //putCharacterVectorAsList
        final List<Character> putCharacterVectorAsList = Characters.asList(putCharacterVector);

        //putCharacterMatrix
        final char[][] putCharacterMatrix = new char[4][];
        for (int i = 0; i < putCharacterMatrix.length; i++) {
            final char[] vector = new char[3];
            for (int j = 0; j < vector.length; j++) {
                vector[j] = Characters.checkedCast('A' + i + j);
            }
            putCharacterMatrix[i] = vector;
        }

        //putCharacterMatrixAsList
        final List<List<Character>> putCharacterMatrixAsList = new ArrayList<List<Character>>(
                putCharacterMatrix.length);
        for (final char[] vector : putCharacterMatrix) {
            putCharacterMatrixAsList.add(Characters.asList(vector));
        }

        new AScriptTaskPython<Void>() {

            @Override
            public void populateInputs(final IScriptTaskInputs inputs) {
                inputs.putCharacter("putCharacter", putCharacter);

                inputs.putCharacterVector("putCharacterVector", putCharacterVector);

                inputs.putCharacterVectorAsList("putCharacterVectorAsList", putCharacterVectorAsList);

                inputs.putCharacterMatrix("putCharacterMatrix", putCharacterMatrix);

                inputs.putCharacterMatrixAsList("putCharacterMatrixAsList", putCharacterMatrixAsList);
            }

            @Override
            public void executeScript(final IScriptTaskEngine engine) {
                final ClassPathResource resource = new ClassPathResource(
                        InputsAndResultsTestCharacter.class.getSimpleName() + ".py",
                        InputsAndResultsTestCharacter.class);
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
                //getCharacter
                final char getCharacter = results.getCharacter("getCharacter");
                Assertions.assertThat(putCharacter).isEqualTo(getCharacter);

                //getCharacterVector
                final char[] getCharacterVector = results.getCharacterVector("getCharacterVector");
                Assertions.assertThat(putCharacterVector).isEqualTo(getCharacterVector);

                //getCharacterVectorAsList
                final List<Character> getCharacterVectorAsList = results
                        .getCharacterVectorAsList("getCharacterVectorAsList");
                Assertions.assertThat(putCharacterVectorAsList).isEqualTo(getCharacterVectorAsList);

                //getCharacterMatrix
                final char[][] getCharacterMatrix = results.getCharacterMatrix("getCharacterMatrix");
                Assertions.assertThat(putCharacterMatrix).isEqualTo(getCharacterMatrix);

                //getCharacterMatrixAsList
                final List<List<Character>> getCharacterMatrixAsList = results
                        .getCharacterMatrixAsList("getCharacterMatrixAsList");
                Assertions.assertThat(putCharacterMatrixAsList).isEqualTo(getCharacterMatrixAsList);
                return null;
            }
        }.run(runner);
    }

}
