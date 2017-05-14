print("getBoolean")
if 'getBoolean' in locals():
	raise Exception("getBoolean already defined!")
getBoolean = putBoolean
print(type(getBoolean))
print(getBoolean)
getBooleanType = type(getBoolean)
if(getBooleanType is not bool):
	raise Exception("getBoolean not bool!")

print("getBooleanVector")
if 'getBooleanVector' in locals():
	raise Exception("getBooleanVector already defined!")
getBooleanVector = putBooleanVector
getBooleanVectorType = type(getBooleanVector[0])
print(getBooleanVectorType)
print(getBooleanVector)
getBooleanVectorType = getBooleanVectorType
if(getBooleanVectorType is not bool):
	raise Exception("getBooleanVector not bool!")

print("getBooleanVectorAsList")
if 'getBooleanVectorAsList' in locals():
	raise Exception("getBooleanVectorAsList already defined!")
getBooleanVectorAsList = putBooleanVectorAsList
getBooleanVectorAsListType = type(getBooleanVectorAsList[0])
print(getBooleanVectorAsListType)
print(getBooleanVectorAsList)
getBooleanVectorAsListType = getBooleanVectorAsListType
if(getBooleanVectorAsListType is not bool):
	raise Exception("getBooleanVectorAsList not bool!")

print("getBooleanMatrix")
if 'getBooleanMatrix' in locals():
	raise Exception("getBooleanMatrix already defined!")
getBooleanMatrix = putBooleanMatrix
getBooleanMatrixType = type(getBooleanMatrix[0][0])
print(getBooleanMatrixType)
print(getBooleanMatrix)
getBooleanMatrixType = getBooleanMatrixType
if(getBooleanMatrixType is not bool):
	raise Exception("getBooleanMatrix not bool!")

print("getBooleanMatrixAsList")
if 'getBooleanMatrixAsList' in locals():
	raise Exception("getBooleanMatrixAsList already defined!")
getBooleanMatrixAsList = putBooleanMatrixAsList
getBooleanMatrixAsListType = type(getBooleanMatrixAsList[0][0])
print(getBooleanMatrixAsListType)
print(getBooleanMatrixAsList)
getBooleanMatrixAsListType = getBooleanMatrixAsListType
if(getBooleanMatrixAsListType is not bool):
	raise Exception("getBooleanMatrixAsList not bool!")
