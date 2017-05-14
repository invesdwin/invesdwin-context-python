import time 
import sys
from py4j.java_gateway import (
    JavaGateway, CallbackServerParameters, GatewayParameters)

class Py4jInterpreter(object):

    def eval(self, expression):
        return eval(expression)

    def put(variable, value):
        globals()[variable] = value
        
    def close(self):
        stopped = True
        sys.exit()
        
    def cleanup(self):
        restoreContext()
        ready = True
        
    def waitForReady(self):
        while not ready:
            time.sleep(0.01)

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
stopped = False
ready = False

py4jInterpreter = Py4jInterpreter()
gateway = JavaGateway(
    gateway_parameters=GatewayParameters(address=sys.argv[1], port=sys.argv[2]),
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

saveContext()
ready = True

while not stopped:
    time.sleep(1)