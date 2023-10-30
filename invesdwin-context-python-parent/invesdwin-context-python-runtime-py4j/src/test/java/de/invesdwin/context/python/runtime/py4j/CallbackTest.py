print("putUuid")
print(putUuid)

getSecretStaticGateway = gateway.jvm.de.invesdwin.context.python.runtime.py4j.CallbackTest.getSecretStatic(putUuid)
print("getSecretStaticGateway")
print(getSecretStaticGateway)

java_import(gateway.jvm,'de.invesdwin.context.python.runtime.py4j.CallbackTest')
getSecretStaticImport = gateway.jvm.CallbackTest.getSecretStatic(putUuid)
print("getSecretStaticImport")
print(getSecretStaticImport)

getSecretStaticCallback = callback("getSecretStatic", putUuid)
print("getSecretStaticCallback")
print(getSecretStaticCallback)

getSecretCallback = callback("getSecret", putUuid)
print("getSecretCallback")
print(getSecretCallback)

getSecretExpressionCallback = callback("getSecretExpression", putUuid)
print("getSecretExpressionCallback")
print(getSecretExpressionCallback)

callback("voidMethod")