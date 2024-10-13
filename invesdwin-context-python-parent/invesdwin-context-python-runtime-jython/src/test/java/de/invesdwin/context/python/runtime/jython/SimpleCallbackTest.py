print("putUuid")
print(putUuid)

from de.invesdwin.context.python.runtime.jython import SimpleCallbackTest
getSecretStaticImport = SimpleCallbackTest.getSecretStatic(putUuid)
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

callManyParams = callback("callManyParams", True, 2, 3, '4', 5, 6, 7.0, 8.0, "123456789", 10.0)
if(callManyParams != 55):
	raise Exception("callManyParams unexpected result: "+callManyParams)
callManyParamsExpression = callback("callManyParamsExpression", True, 2, 3, '4', 5, 6, 7.0, 8.0, "123456789", 10.0)
if(callManyParamsExpression != 55):
	raise Exception("callManyParamsExpression unexpected result: "+callManyParamsExpression)
callManyParamsExpressionMultiline = callback("callManyParamsExpressionMultiline", True, 2, 3, '4', 5, 6, 7.0, 8.0, "123456789", 10.0)
if(callManyParamsExpressionMultiline != 55):
	raise Exception("callManyParamsExpressionMultiline unexpected result: "+callManyParamsExpressionMultiline)

getManyParamsExpression = putManyParamsExpression
print("getManyParamsExpression")
print(getManyParamsExpression)
getManyParamsExpressionMultilineWrong = putManyParamsExpressionMultilineWrong
print("getManyParamsExpressionMultilineWrong")
print(getManyParamsExpressionMultilineWrong)
getManyParamsExpressionMultiline = putManyParamsExpressionMultiline
print("getManyParamsExpressionMultiline")
print(getManyParamsExpressionMultiline)
