package de.invesdwin.context.python.runtime.graalpy.jsr223;

import javax.annotation.concurrent.Immutable;

import org.graalvm.polyglot.Context.Builder;

import de.invesdwin.context.graalvm.jsr223.PolyglotScriptEngineFactory;
import de.invesdwin.context.python.runtime.graalpy.GraalpyProperties;

@Immutable
public final class GraalpyScriptEngineFactory extends PolyglotScriptEngineFactory {

    public static final GraalpyScriptEngineFactory INSTANCE = new GraalpyScriptEngineFactory();

    public GraalpyScriptEngineFactory() {
        super("python");
    }

    /**
     * Support for using GraalPy venv:
     * https://github.com/graalvm/graal-languages-demos/blob/main/graalpy/graalpy-custom-venv-guide/README.md
     * 
     * The venv could be packaged inside the jar and extracted on application launch, then the PYTHON_COMMAND system
     * property can be set automatically to point to the venv. This should suffice for embedding python modules and
     * matches the approach of python4j that also allows
     * 
     * We currently do not support python-embedding with graalpy-maven-plugin but could do so in the future based on:
     * https://github.com/graalvm/graal-languages-demos/tree/main/graalpy/graalpy-javase-guide
     * 
     * For now zipping a venv as described above should suffice.
     */
    @Override
    public Builder customizeContextBuilder(final Builder builder) {
        if (GraalpyProperties.PYTHON_COMMAND != null) {
            builder.option("python.Executable", GraalpyProperties.PYTHON_COMMAND);
            builder.option("python.ForceImportSite", "true");
        }
        return super.customizeContextBuilder(builder);
    }

}
