print("getLong")
if 'getLong' in locals():
	raise Exception("getLong already defined!")
getLong = callback("getLong")
getLongType = type(getLong)
print(getLongType)
print(getLong)
if(getLongType is not long):
	raise Exception("getLong not long!")
callback("setLong", getLong)

print("getLongVector")
if 'getLongVector' in locals():
	raise Exception("getLongVector already defined!")
getLongVector = callback("getLongVector")
getLongVectorType = type(getLongVector[0])
print(getLongVectorType)
print(getLongVector)
if(getLongVectorType is not long):
	raise Exception("getLongVector not long!")
callback("setLongVector", getLongVector)

print("getLongVectorAsList")
if 'getLongVectorAsList' in locals():
	raise Exception("getLongVectorAsList already defined!")
getLongVectorAsList = callback("getLongVectorAsList")
getLongVectorAsListType = type(getLongVectorAsList[0])
print(getLongVectorAsListType)
print(getLongVectorAsList)
if(getLongVectorAsListType is not long):
	raise Exception("getLongVectorAsList not long!")
callback("setLongVectorAsList", getLongVectorAsList)

print("getLongMatrix")
if 'getLongMatrix' in locals():
	raise Exception("getLongMatrix already defined!")
getLongMatrix = callback("getLongMatrix")
getLongMatrixType = type(getLongMatrix[0][0])
print(getLongMatrixType)
print(getLongMatrix)
if(getLongMatrixType is not long):
	raise Exception("getLongMatrix not long!")
callback("setLongMatrix", getLongMatrix)

print("getLongMatrixAsList")
if 'getLongMatrixAsList' in locals():
	raise Exception("getLongMatrixAsList already defined!")
getLongMatrixAsList = callback("getLongMatrixAsList")
getLongMatrixAsListType = type(getLongMatrixAsList[0][0])
print(getLongMatrixAsListType)
print(getLongMatrixAsList)
if(getLongMatrixAsListType is not long):
	raise Exception("getLongMatrixAsList not long!")
callback("setLongMatrixAsList", getLongMatrixAsList)
