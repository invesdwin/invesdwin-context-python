import time 
import sys
import logging
from py4j.java_gateway import (JavaGateway, CallbackServerParameters, GatewayParameters, java_import)

class Py4jInterpreter(object):

    def get(self, variable):
        return eval(variable)
    
    def eval(self, expression):
        exec(expression, globals(), locals())

    def put(self, variable, value):
        globals()[variable] = value
        
    def cleanup(self):
        restoreContext()
        
    class Java:
        implements = ["de.invesdwin.context.python.runtime.py4j.pool.internal.IPy4jInterpreter"]

# http://stackoverflow.com/questions/3543833/how-do-i-clear-all-variables-in-the-middle-of-a-python-script
__saved_context__ = {}

def saveContext():
    __saved_context__.update(sys.modules[__name__].__dict__)

def restoreContext():
    names = list(sys.modules[__name__].__dict__.keys())
    for n in names:
        if n not in __saved_context__:
            del sys.modules[__name__].__dict__[n]
            
# connect python side to Java side with Java dynamic port and start python
# callback server with a dynamic port
py4jInterpreter = Py4jInterpreter()

logger = logging.getLogger("py4j")
logger.setLevel(logging.ERROR)
#logger.addHandler(logging.StreamHandler())

gateway = JavaGateway(
    gateway_parameters=GatewayParameters(address=sys.argv[1], port=int(sys.argv[2])),
    callback_server_parameters=CallbackServerParameters(port=0), 
    python_server_entry_point=py4jInterpreter)


# retrieve the port on which the python callback server was bound to.
python_port = gateway.get_callback_server().get_listening_port()

# tell the Java side to connect to the python callback server with the new
# python port. Note that we use the java_gateway_server attribute that
# retrieves the GatewayServer instance.
gateway.java_gateway_server.resetCallbackClient(
    gateway.java_gateway_server.getCallbackClient().getAddress(),
    python_port)


def callJava(methodName, *parameters):
    if 'py4jScriptTaskCallbackContext' not in globals():
        if 'py4jScriptTaskCallbackContextUuid' in locals() or 'py4jScriptTaskCallbackContextUuid' in globals():
            global py4jScriptTaskCallbackContext
            py4jScriptTaskCallbackContext = gateway.jvm.de.invesdwin.context.python.runtime.py4j.Py4jScriptTaskCallbackContext.getContext(py4jScriptTaskCallbackContextUuid)
        else:
            raise Exception("IScriptTaskCallback not available")
    parametersArray = gateway.new_array(gateway.jvm.java.lang.Object,len(parameters)+1)
    parametersArray[0] = methodName
    for i in range(len(parameters)):
        parametersArray[i+1] = parameters[i]
    return py4jScriptTaskCallbackContext.invoke(parametersArray)

saveContext()
gateway.entry_point.ready()