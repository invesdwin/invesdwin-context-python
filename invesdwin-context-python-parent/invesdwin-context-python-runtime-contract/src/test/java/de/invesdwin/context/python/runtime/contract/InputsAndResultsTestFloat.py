print("getFloat")
if 'getFloat' in locals():
	raise Exception("getFloat already defined!")
getFloat = putFloat
getFloatType = type(getFloat)
print(getFloatType)
print(getFloat)
if(getFloatType is not float):
	raise Exception("getFloat not float!")

print("getFloatVector")
if 'getFloatVector' in locals():
	raise Exception("getFloatVector already defined!")
getFloatVector = putFloatVector
getFloatVectorType = type(getFloatVector[0])
print(getFloatVectorType)
print(getFloatVector)
if(getFloatVectorType is not float):
	raise Exception("getFloatVector not float!")

print("getFloatVectorAsList")
if 'getFloatVectorAsList' in locals():
	raise Exception("getFloatVectorAsList already defined!")
getFloatVectorAsList = putFloatVectorAsList
getFloatVectorAsListType = type(getFloatVectorAsList[0])
print(getFloatVectorAsListType)
print(getFloatVectorAsList)
if(getFloatVectorAsListType is not float):
	raise Exception("getFloatVectorAsList not float!")

print("getFloatMatrix")
if 'getFloatMatrix' in locals():
	raise Exception("getFloatMatrix already defined!")
getFloatMatrix = putFloatMatrix
getFloatMatrixType = type(getFloatMatrix[0][0])
print(getFloatMatrixType)
print(getFloatMatrix)
if(getFloatMatrixType is not float):
	raise Exception("getFloatMatrix not float!")

print("getFloatMatrixAsList")
if 'getFloatMatrixAsList' in locals():
	raise Exception("getFloatMatrixAsList already defined!")
getFloatMatrixAsList = putFloatMatrixAsList
getFloatMatrixAsListType = type(getFloatMatrixAsList[0][0])
print(getFloatMatrixAsListType)
print(getFloatMatrixAsList)
if(getFloatMatrixAsListType is not float):
	raise Exception("getFloatMatrixAsList not float!")
