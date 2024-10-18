package de.invesdwin.context.python.runtime.graalpy.jsr223;

import javax.annotation.concurrent.Immutable;

import de.invesdwin.context.graalvm.jsr223.APolyglotScriptEngineFactory;

/**
 * Source: https://www.graalvm.org/latest/reference-manual/embed-languages/#compatibility-with-jsr-223-scriptengine
 */
@Immutable
public final class GraalpyScriptEngineFactory extends APolyglotScriptEngineFactory {
    public GraalpyScriptEngineFactory() {
        super("python");
    }
}
