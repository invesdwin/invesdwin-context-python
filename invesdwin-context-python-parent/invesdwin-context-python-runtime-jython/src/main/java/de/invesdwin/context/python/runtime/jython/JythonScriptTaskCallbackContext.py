from de.invesdwin.context.python.runtime.jython import JythonScriptTaskCallbackContext
 
def callJava(methodName, *parameters):
    if 'jythonScriptTaskCallbackContext' not in globals():
        if 'jythonScriptTaskCallbackContextUuid' in locals() or 'jythonScriptTaskCallbackContextUuid' in globals():
            global jythonScriptTaskCallbackContext
            jythonScriptTaskCallbackContext = JythonScriptTaskCallbackContext.getContext(jythonScriptTaskCallbackContextUuid)
        else:
            raise Exception("IScriptTaskCallback not available")
    returnValue = jythonScriptTaskCallbackContext.invoke(methodName, parameters)
    if returnValue.isReturnExpression():
        return eval(returnValue.getReturnValue(), globals())
    else:
        return returnValue.getReturnValue()    


