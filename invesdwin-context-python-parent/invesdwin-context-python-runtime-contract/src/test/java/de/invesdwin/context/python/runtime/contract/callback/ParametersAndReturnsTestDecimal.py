print("getDecimal")
if 'getDecimal' in locals():
	raise Exception("getDecimal already defined!")
getDecimal = callJava("getDecimal")
getDecimalType = type(getDecimal)
print(getDecimalType)
print(getDecimal)
if(getDecimalType is not float):
	raise Exception("getDecimal not float!")
callJava("setDecimal", getDecimal)

print("getDecimalVector")
if 'getDecimalVector' in locals():
	raise Exception("getDecimalVector already defined!")
getDecimalVector = callJava("getDecimalVector")
getDecimalVectorType = type(getDecimalVector[0])
print(getDecimalVectorType)
print(getDecimalVector)
if(getDecimalVectorType is not float):
	raise Exception("getDecimalVector not float!")
callJava("setDecimalVector", getDecimalVector)

print("getDecimalVectorAsList")
if 'getDecimalVectorAsList' in locals():
	raise Exception("getDecimalVectorAsList already defined!")
getDecimalVectorAsList = callJava("getDecimalVectorAsList")
getDecimalVectorAsListType = type(getDecimalVectorAsList[0])
print(getDecimalVectorAsListType)
print(getDecimalVectorAsList)
if(getDecimalVectorAsListType is not float):
	raise Exception("getDecimalVectorAsList not float!")
callJava("setDecimalVectorAsList", getDecimalVectorAsList)

print("getDecimalMatrix")
if 'getDecimalMatrix' in locals():
	raise Exception("getDecimalMatrix already defined!")
getDecimalMatrix = callJava("getDecimalMatrix")
getDecimalMatrixType = type(getDecimalMatrix[0][0])
print(getDecimalMatrixType)
print(getDecimalMatrix)
if(getDecimalMatrixType is not float):
	raise Exception("getDecimalMatrix not float!")
callJava("setDecimalMatrix", getDecimalMatrix)

print("getDecimalMatrixAsList")
if 'getDecimalMatrixAsList' in locals():
	raise Exception("getDecimalMatrixAsList already defined!")
getDecimalMatrixAsList = callJava("getDecimalMatrixAsList")
getDecimalMatrixAsListType = type(getDecimalMatrixAsList[0][0])
print(getDecimalMatrixAsListType)
print(getDecimalMatrixAsList)
if(getDecimalMatrixAsListType is not float):
	raise Exception("getDecimalMatrixAsList not float!")
callJava("setDecimalMatrixAsList", getDecimalMatrixAsList)
