print("getShort")
if 'getShort' in locals():
	raise Exception("getShort already defined!")
getShort = putShort
getShortType = type(getShort)
print(getShortType)
print(getShort)
if(getShortType is not int):
	raise Exception("getShort not int!")

print("getShortVector")
if 'getShortVector' in locals():
	raise Exception("getShortVector already defined!")
getShortVector = putShortVector
getShortVectorType = type(getShortVector[0])
print(getShortVectorType)
print(getShortVector)
if(getShortVectorType is not int):
	raise Exception("getShortVector not int!")

print("getShortVectorAsList")
if 'getShortVectorAsList' in locals():
	raise Exception("getShortVectorAsList already defined!")
getShortVectorAsList = putShortVectorAsList
getShortVectorAsListType = type(getShortVectorAsList[0])
print(getShortVectorAsListType)
print(getShortVectorAsList)
if(getShortVectorAsListType is not int):
	raise Exception("getShortVectorAsList not int!")

print("getShortMatrix")
if 'getShortMatrix' in locals():
	raise Exception("getShortMatrix already defined!")
getShortMatrix = putShortMatrix
getShortMatrixType = type(getShortMatrix[0][0])
print(getShortMatrixType)
print(getShortMatrix)
if(getShortMatrixType is not int):
	raise Exception("getShortMatrix not int!")

print("getShortMatrixAsList")
if 'getShortMatrixAsList' in locals():
	raise Exception("getShortMatrixAsList already defined!")
getShortMatrixAsList = putShortMatrixAsList
getShortMatrixAsListType = type(getShortMatrixAsList[0][0])
print(getShortMatrixAsListType)
print(getShortMatrixAsList)
if(getShortMatrixAsListType is not int):
	raise Exception("getShortMatrixAsList not int!")
