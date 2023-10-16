print("putUuid")
print(putUuid)

getSecretStaticGateway = gateway.jvm.de.invesdwin.context.python.runtime.py4j.CallJavaTest.getSecretStatic(putUuid)
print("getSecretStaticGateway")
print(getSecretStaticGateway)

java_import(gateway.jvm,'de.invesdwin.context.python.runtime.py4j.CallJavaTest')
getSecretStaticImport = gateway.jvm.CallJavaTest.getSecretStatic(putUuid)
print("getSecretStaticImport")
print(getSecretStaticImport)

getSecretStaticCallJava = callJava("getSecretStatic", putUuid)
print("getSecretStaticCallJava")
print(getSecretStaticCallJava)

getSecretCallJava = callJava("getSecret", putUuid)
print("getSecretCallJava")
print(getSecretCallJava)