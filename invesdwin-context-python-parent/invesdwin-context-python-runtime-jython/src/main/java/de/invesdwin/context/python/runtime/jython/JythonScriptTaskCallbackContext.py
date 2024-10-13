from de.invesdwin.context.python.runtime.jython import JythonScriptTaskCallbackContext
 
def callback(methodName, *parameters):
    if 'jythonScriptTaskCallbackContext' not in globals():
        if 'jythonScriptTaskCallbackContextUuid' in locals() or 'jythonScriptTaskCallbackContextUuid' in globals():
            global jythonScriptTaskCallbackContext
            jythonScriptTaskCallbackContext = JythonScriptTaskCallbackContext.getContext(jythonScriptTaskCallbackContextUuid)
        else:
            raise Exception("IScriptTaskCallback not available")
    returnValue = jythonScriptTaskCallbackContext.invoke(methodName, parameters)
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


