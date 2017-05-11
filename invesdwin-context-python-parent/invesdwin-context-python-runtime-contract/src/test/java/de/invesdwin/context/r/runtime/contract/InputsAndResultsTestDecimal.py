print("getDecimal")
if 'getDecimal' in locals():
	raise Exception("getDecimal already defined!")
getDecimal = putDecimal
getDecimalType = type(getDecimal)
print(getDecimalType)
print(getDecimal)
if(getDecimalType is not float):
	raise Exception("getDecimal not float!")

print("getDecimalVector")
if 'getDecimalVector' in locals():
	raise Exception("getDecimalVector already defined!")
getDecimalVector = putDecimalVector
getDecimalVectorType = type(getDecimalVector[0])
print(getDecimalVectorType)
print(getDecimalVector)
if(getDecimalVectorType is not float):
	raise Exception("getDecimalVector not float!")

print("getDecimalVectorAsList")
if 'getDecimalVectorAsList' in locals():
	raise Exception("getDecimalVectorAsList already defined!")
getDecimalVectorAsList = putDecimalVectorAsList
getDecimalVectorAsListType = type(getDecimalVectorAsList[0])
print(getDecimalVectorAsListType)
print(getDecimalVectorAsList)
if(getDecimalVectorAsListType is not float):
	raise Exception("getDecimalVectorAsList not float!")

print("getDecimalMatrix")
if 'getDecimalMatrix' in locals():
	raise Exception("getDecimalMatrix already defined!")
getDecimalMatrix = putDecimalMatrix
getDecimalMatrixType = type(getDecimalMatrix[0][0])
print(getDecimalMatrixType)
print(getDecimalMatrix)
if(getDecimalMatrixType is not float):
	raise Exception("getDecimalMatrix not float!")

print("getDecimalMatrixAsList")
if 'getDecimalMatrixAsList' in locals():
	raise Exception("getDecimalMatrixAsList already defined!")
getDecimalMatrixAsList = putDecimalMatrixAsList
getDecimalMatrixAsListType = type(getDecimalMatrixAsList[0][0])
print(getDecimalMatrixAsListType)
print(getDecimalMatrixAsList)
if(getDecimalMatrixAsListType is not float):
	raise Exception("getDecimalMatrixAsList not float!")
