package de.invesdwin.context.python.runtime.japyb;

import javax.annotation.concurrent.NotThreadSafe;

import com.fasterxml.jackson.databind.JsonNode;

import de.invesdwin.context.python.runtime.contract.AScriptTaskResultsPythonFromJson;

@NotThreadSafe
public class JapybScriptTaskResultsPython extends AScriptTaskResultsPythonFromJson {

    private final JapybScriptTaskEnginePython engine;

    public JapybScriptTaskResultsPython(final JapybScriptTaskEnginePython engine) {
        this.engine = engine;
    }

    @Override
    public JapybScriptTaskEnginePython getEngine() {
        return engine;
    }

    @Override
    protected JsonNode getAsJsonNode(final String variable) {
        return engine.unwrap().getAsJsonNode(variable);
    }

}