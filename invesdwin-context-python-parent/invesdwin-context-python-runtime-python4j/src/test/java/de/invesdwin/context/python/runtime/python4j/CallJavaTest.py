print("putUuid")
print(putUuid)

getSecretStaticCallJava = callJava("getSecretStatic", putUuid)
print("getSecretStaticCallJava")
print(getSecretStaticCallJava)

getSecretCallJava = callJava("getSecret", putUuid)
print("getSecretCallJava")
print(getSecretCallJava)

getSecretExpressionCallJava = callJava("getSecretExpression", putUuid)
print("getSecretExpressionCallJava")
print(getSecretExpressionCallJava)

callJava("voidMethod")