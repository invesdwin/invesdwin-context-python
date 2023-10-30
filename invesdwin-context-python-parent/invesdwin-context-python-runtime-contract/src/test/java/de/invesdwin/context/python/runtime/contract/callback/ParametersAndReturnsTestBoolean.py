print("getBoolean")
if 'getBoolean' in locals():
	raise Exception("getBoolean already defined!")
getBoolean = callback("getBoolean")
getBooleanType = type(getBoolean)
print(getBooleanType)
print(getBoolean)
if(getBooleanType is not bool):
	raise Exception("getBoolean not bool!")
callback("setBoolean", getBoolean)

print("getBooleanVector")
if 'getBooleanVector' in locals():
	raise Exception("getBooleanVector already defined!")
getBooleanVector = callback("getBooleanVector")
getBooleanVectorType = type(getBooleanVector[0])
print(getBooleanVectorType)
print(getBooleanVector)
if(getBooleanVectorType is not bool):
	raise Exception("getBooleanVector not bool!")
callback("setBooleanVector", getBooleanVector)

print("getBooleanVectorAsList")
if 'getBooleanVectorAsList' in locals():
	raise Exception("getBooleanVectorAsList already defined!")
getBooleanVectorAsList = callback("getBooleanVectorAsList")
getBooleanVectorAsListType = type(getBooleanVectorAsList[0])
print(getBooleanVectorAsListType)
print(getBooleanVectorAsList)
if(getBooleanVectorAsListType is not bool):
	raise Exception("getBooleanVectorAsList not bool!")
callback("setBooleanVectorAsList", getBooleanVectorAsList)

print("getBooleanMatrix")
if 'getBooleanMatrix' in locals():
	raise Exception("getBooleanMatrix already defined!")
getBooleanMatrix = callback("getBooleanMatrix")
getBooleanMatrixType = type(getBooleanMatrix[0][0])
print(getBooleanMatrixType)
print(getBooleanMatrix)
if(getBooleanMatrixType is not bool):
	raise Exception("getBooleanMatrix not bool!")
callback("setBooleanMatrix", getBooleanMatrix)

print("getBooleanMatrixAsList")
if 'getBooleanMatrixAsList' in locals():
	raise Exception("getBooleanMatrixAsList already defined!")
getBooleanMatrixAsList = callback("getBooleanMatrixAsList")
getBooleanMatrixAsListType = type(getBooleanMatrixAsList[0][0])
print(getBooleanMatrixAsListType)
print(getBooleanMatrixAsList)
if(getBooleanMatrixAsListType is not bool):
	raise Exception("getBooleanMatrixAsList not bool!")
callback("setBooleanMatrixAsList", getBooleanMatrixAsList)
