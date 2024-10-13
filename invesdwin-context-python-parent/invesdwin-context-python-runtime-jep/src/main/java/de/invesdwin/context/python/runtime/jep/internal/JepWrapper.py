import sys
from de.invesdwin.context.python.runtime.jep import JepScriptTaskCallbackContext

class StdOutToJava(object):
    "Redirects Python's sys.stdout to Slf4j"

    def __init__(self):
        from de.invesdwin.context.python.runtime.jep import JepProperties
        self.oldout = sys.stdout
        self.printmethod = getattr(JepProperties.REDIRECTED_OUT, 'print')
        self.flushmethod = getattr(JepProperties.REDIRECTED_OUT, 'flush')

    def write(self, msg):
        self.printmethod(msg)

    def flush(self):
        self.flushmethod()


class StdErrToJava(object):
    "Redirects Python's sys.stderr to Slf4j"

    def __init__(self):
        from de.invesdwin.context.python.runtime.jep import JepProperties
        self.oldout = sys.stdout
        self.printmethod = getattr(JepProperties.REDIRECTED_ERR, 'print')
        self.flushmethod = getattr(JepProperties.REDIRECTED_ERR, 'flush')

    def write(self, msg):
        self.printmethod(msg)

    def flush(self):
        self.flushmethod()

sys.stdout = StdOutToJava()
sys.stderr = StdErrToJava()

# http://stackoverflow.com/questions/3543833/how-do-i-clear-all-variables-in-the-middle-of-a-python-script
__saved_context__ = {}

def saveContext():
    __saved_context__.update(sys.modules[__name__].__dict__)

def restoreContext():
    names = list(sys.modules[__name__].__dict__.keys())
    for n in names:
        if n not in __saved_context__:
            del sys.modules[__name__].__dict__[n]
            
def callback(methodName, *parameters):
    if 'jepScriptTaskCallbackContext' not in globals():
        if 'jepScriptTaskCallbackContextUuid' in locals() or 'jepScriptTaskCallbackContextUuid' in globals():
            global jepScriptTaskCallbackContext
            jepScriptTaskCallbackContext = JepScriptTaskCallbackContext.getContext(jepScriptTaskCallbackContextUuid)
        else:
            raise Exception("IScriptTaskCallback not available")
    returnValue = jepScriptTaskCallbackContext.invoke(methodName, parameters)
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
            
saveContext()
    


