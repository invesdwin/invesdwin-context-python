print("getInteger")
if 'getInteger' in locals():
	raise Exception("getInteger already defined!")
getInteger = putInteger
getIntegerType = type(getInteger)
print(getIntegerType)
print(getInteger)
if(getIntegerType is not int):
	raise Exception("getInteger not int!")

print("getIntegerVector")
if 'getIntegerVector' in locals():
	raise Exception("getIntegerVector already defined!")
getIntegerVector = putIntegerVector
getIntegerVectorType = type(getIntegerVector[0])
print(getIntegerVectorType)
print(getIntegerVector)
if(getIntegerVectorType is not int):
	raise Exception("getIntegerVector not int!")

print("getIntegerVectorAsList")
if 'getIntegerVectorAsList' in locals():
	raise Exception("getIntegerVectorAsList already defined!")
getIntegerVectorAsList = putIntegerVectorAsList
getIntegerVectorAsListType = type(getIntegerVectorAsList[0])
print(getIntegerVectorAsListType)
print(getIntegerVectorAsList)
if(getIntegerVectorAsListType is not int):
	raise Exception("getIntegerVectorAsList not int!")

print("getIntegerMatrix")
if 'getIntegerMatrix' in locals():
	raise Exception("getIntegerMatrix already defined!")
getIntegerMatrix = putIntegerMatrix
getIntegerMatrixType = type(getIntegerMatrix[0][0])
print(getIntegerMatrixType)
print(getIntegerMatrix)
if(getIntegerMatrixType is not int):
	raise Exception("getIntegerMatrix not int!")

print("getIntegerMatrixAsList")
if 'getIntegerMatrixAsList' in locals():
	raise Exception("getIntegerMatrixAsList already defined!")
getIntegerMatrixAsList = putIntegerMatrixAsList
getIntegerMatrixAsListType = type(getIntegerMatrixAsList[0][0])
print(getIntegerMatrixAsListType)
print(getIntegerMatrixAsList)
if(getIntegerMatrixAsListType is not int):
	raise Exception("getIntegerMatrixAsList not int!")
