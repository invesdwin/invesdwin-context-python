print("putUuid")
print(putUuid)

getSecret = gateway.jvm.de.invesdwin.context.python.runtime.py4j.CallJavaTest.getSecret(putUuid)

print("getSecret")
print(getSecret)

java_import(gateway.jvm,'de.invesdwin.context.python.runtime.py4j.CallJavaTest')
getSecretImport = gateway.jvm.CallJavaTest.getSecret(putUuid)

print("getSecretImport")
print(getSecretImport)