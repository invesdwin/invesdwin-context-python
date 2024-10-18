from java.de.invesdwin.context.python.runtime.graalpy import GraalpyScriptTaskCallbackContext
 
def callback(methodName, *parameters):
    if 'graalpyScriptTaskCallbackContext' not in globals():
        if 'graalpyScriptTaskCallbackContextUuid' in locals() or 'graalpyScriptTaskCallbackContextUuid' in globals():
            global graalpyScriptTaskCallbackContext
            graalpyScriptTaskCallbackContext = GraalpyScriptTaskCallbackContext.getContext(graalpyScriptTaskCallbackContextUuid)
        else:
            raise Exception("IScriptTaskCallback not available")
    returnValue = graalpyScriptTaskCallbackContext.invoke(methodName, parameters)
    if returnValue.isReturnExpression():
        returnExpression = returnValue.getReturnValue()
        if returnExpression.startswith("raise "):
            exec(returnExpression, globals())
            # fallback
            raise Exception(returnExpression)
        else:
            returnExpressionLines = returnExpression.splitlines()
            returnExpressionLinesLength = len(returnExpressionLines)
            if(returnExpressionLinesLength > 1):
                returnExpressionLinesLastElement = returnExpressionLinesLength-1
                returnExpressionExec = "\n".join(returnExpressionLines[:returnExpressionLinesLastElement])
                returnExpressionEval = returnExpressionLines[returnExpressionLinesLastElement]
                loc = {}
                exec(returnExpressionExec, globals(), loc)
                return eval(returnExpressionEval, globals(), loc)
            else:
                return eval(returnExpression, globals())
    else:
        return returnValue.getReturnValue()    


