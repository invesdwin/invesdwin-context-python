print("getDecimal")
if 'getDecimal' in locals():
	raise Exception("getDecimal already defined!")
getDecimal = callback("getDecimal")
getDecimalType = type(getDecimal)
print(getDecimalType)
print(getDecimal)
if(getDecimalType is not float):
	raise Exception("getDecimal not float!")
callback("setDecimal", getDecimal)

print("getDecimalVector")
if 'getDecimalVector' in locals():
	raise Exception("getDecimalVector already defined!")
getDecimalVector = callback("getDecimalVector")
getDecimalVectorType = type(getDecimalVector[0])
print(getDecimalVectorType)
print(getDecimalVector)
if(getDecimalVectorType is not float):
	raise Exception("getDecimalVector not float!")
callback("setDecimalVector", getDecimalVector)

print("getDecimalVectorAsList")
if 'getDecimalVectorAsList' in locals():
	raise Exception("getDecimalVectorAsList already defined!")
getDecimalVectorAsList = callback("getDecimalVectorAsList")
getDecimalVectorAsListType = type(getDecimalVectorAsList[0])
print(getDecimalVectorAsListType)
print(getDecimalVectorAsList)
if(getDecimalVectorAsListType is not float):
	raise Exception("getDecimalVectorAsList not float!")
callback("setDecimalVectorAsList", getDecimalVectorAsList)

print("getDecimalMatrix")
if 'getDecimalMatrix' in locals():
	raise Exception("getDecimalMatrix already defined!")
getDecimalMatrix = callback("getDecimalMatrix")
getDecimalMatrixType = type(getDecimalMatrix[0][0])
print(getDecimalMatrixType)
print(getDecimalMatrix)
if(getDecimalMatrixType is not float):
	raise Exception("getDecimalMatrix not float!")
callback("setDecimalMatrix", getDecimalMatrix)

print("getDecimalMatrixAsList")
if 'getDecimalMatrixAsList' in locals():
	raise Exception("getDecimalMatrixAsList already defined!")
getDecimalMatrixAsList = callback("getDecimalMatrixAsList")
getDecimalMatrixAsListType = type(getDecimalMatrixAsList[0][0])
print(getDecimalMatrixAsListType)
print(getDecimalMatrixAsList)
if(getDecimalMatrixAsListType is not float):
	raise Exception("getDecimalMatrixAsList not float!")
callback("setDecimalMatrixAsList", getDecimalMatrixAsList)
