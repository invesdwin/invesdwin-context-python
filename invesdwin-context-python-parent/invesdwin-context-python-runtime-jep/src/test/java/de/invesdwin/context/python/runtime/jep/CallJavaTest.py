print("putUuid")
print(putUuid)

from de.invesdwin.context.python.runtime.jep import CallJavaTest
getSecretStaticImport = CallJavaTest.getSecretStatic(putUuid)
print("getSecretStaticImport")
print(getSecretStaticImport)

getSecretStaticCallJava = callJava("getSecretStatic", putUuid)
print("getSecretStaticCallJava")
print(getSecretStaticCallJava)

getSecretCallJava = callJava("getSecret", putUuid)
print("getSecretCallJava")
print(getSecretCallJava)

getSecretExpressionCallJava = callJava("getSecretExpression", putUuid)
print("getSecretExpressionCallJava")
print(getSecretExpressionCallJava)