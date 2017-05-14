print("getLong")
if 'getLong' in locals():
	raise Exception("getLong already defined!")
getLong = putLong
getLongType = type(getLong)
print(getLongType)
print(getLong)
if(getLongType is not long):
	raise Exception("getLong not long!")

print("getLongVector")
if 'getLongVector' in locals():
	raise Exception("getLongVector already defined!")
getLongVector = putLongVector
getLongVectorType = type(getLongVector[0])
print(getLongVectorType)
print(getLongVector)
if(getLongVectorType is not long):
	raise Exception("getLongVector not long!")

print("getLongVectorAsList")
if 'getLongVectorAsList' in locals():
	raise Exception("getLongVectorAsList already defined!")
getLongVectorAsList = putLongVectorAsList
getLongVectorAsListType = type(getLongVectorAsList[0])
print(getLongVectorAsListType)
print(getLongVectorAsList)
if(getLongVectorAsListType is not long):
	raise Exception("getLongVectorAsList not long!")

print("getLongMatrix")
if 'getLongMatrix' in locals():
	raise Exception("getLongMatrix already defined!")
getLongMatrix = putLongMatrix
getLongMatrixType = type(getLongMatrix[0][0])
print(getLongMatrixType)
print(getLongMatrix)
if(getLongMatrixType is not long):
	raise Exception("getLongMatrix not long!")

print("getLongMatrixAsList")
if 'getLongMatrixAsList' in locals():
	raise Exception("getLongMatrixAsList already defined!")
getLongMatrixAsList = putLongMatrixAsList
getLongMatrixAsListType = type(getLongMatrixAsList[0][0])
print(getLongMatrixAsListType)
print(getLongMatrixAsList)
if(getLongMatrixAsListType is not long):
	raise Exception("getLongMatrixAsList not long!")
