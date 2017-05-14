print("getDouble")
if 'getDouble' in locals():
	raise Exception("getDouble already defined!")
getDouble = putDouble
getDoubleType = type(getDouble)
print(getDoubleType)
print(getDouble)
if(getDoubleType is not float):
	raise Exception("getDouble not float!")

print("getDoubleVector")
if 'getDoubleVector' in locals():
	raise Exception("getDoubleVector already defined!")
getDoubleVector = putDoubleVector
getDoubleVectorType = type(getDoubleVector[0])
print(getDoubleVectorType)
print(getDoubleVector)
if(getDoubleVectorType is not float):
	raise Exception("getDoubleVector not float!")

print("getDoubleVectorAsList")
if 'getDoubleVectorAsList' in locals():
	raise Exception("getDoubleVectorAsList already defined!")
getDoubleVectorAsList = putDoubleVectorAsList
getDoubleVectorAsListType = type(getDoubleVectorAsList[0])
print(getDoubleVectorAsListType)
print(getDoubleVectorAsList)
if(getDoubleVectorAsListType is not float):
	raise Exception("getDoubleVectorAsList not float!")

print("getDoubleMatrix")
if 'getDoubleMatrix' in locals():
	raise Exception("getDoubleMatrix already defined!")
getDoubleMatrix = putDoubleMatrix
getDoubleMatrixType = type(getDoubleMatrix[0][0])
print(getDoubleMatrixType)
print(getDoubleMatrix)
if(getDoubleMatrixType is not float):
	raise Exception("getDoubleMatrix not float!")

print("getDoubleMatrixAsList")
if 'getDoubleMatrixAsList' in locals():
	raise Exception("getDoubleMatrixAsList already defined!")
getDoubleMatrixAsList = putDoubleMatrixAsList
getDoubleMatrixAsListType = type(getDoubleMatrixAsList[0][0])
print(getDoubleMatrixAsListType)
print(getDoubleMatrixAsList)
if(getDoubleMatrixAsListType is not float):
	raise Exception("getDoubleMatrixAsList not float!")
