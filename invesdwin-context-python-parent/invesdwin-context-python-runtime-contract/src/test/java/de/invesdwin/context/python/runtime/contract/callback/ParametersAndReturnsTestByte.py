print("getByte")
if 'getByte' in locals():
	raise Exception("getByte already defined!")
getByte = callback("getByte")
getByteType = type(getByte)
print(getByteType)
print(getByte)
if(getByteType is not int):
	raise Exception("getByte not int!")
callback("setByte", getByte)

print("getByteVector")
if 'getByteVector' in locals():
	raise Exception("getByteVector already defined!")
getByteVector = callback("getByteVector")
getByteVectorType = type(getByteVector[0])
print(getByteVectorType)
print(getByteVector)
if(getByteVectorType is not int):
	raise Exception("getByteVector not int!")
callback("setByteVector", getByteVector)

print("getByteVectorAsList")
if 'getByteVectorAsList' in locals():
	raise Exception("getByteVectorAsList already defined!")
getByteVectorAsList = callback("getByteVectorAsList")
getByteVectorAsListType = type(getByteVectorAsList[0])
print(getByteVectorAsListType)
print(getByteVectorAsList)
if(getByteVectorAsListType is not int):
	raise Exception("getByteVectorAsList not int!")
callback("setByteVectorAsList", getByteVectorAsList)

print("getByteMatrix")
if 'getByteMatrix' in locals():
	raise Exception("getByteMatrix already defined!")
getByteMatrix = callback("getByteMatrix")
getByteMatrixType = type(getByteMatrix[0][0])
print(getByteMatrixType)
print(getByteMatrix)
if(getByteMatrixType is not int):
	raise Exception("getByteMatrix not int!")
callback("setByteMatrix", getByteMatrix)

print("getByteMatrixAsList")
if 'getByteMatrixAsList' in locals():
	raise Exception("getByteMatrixAsList already defined!")
getByteMatrixAsList = callback("getByteMatrixAsList")
getByteMatrixAsListType = type(getByteMatrixAsList[0][0])
print(getByteMatrixAsListType)
print(getByteMatrixAsList)
if(getByteMatrixAsListType is not int):
	raise Exception("getByteMatrixAsList not int!")
callback("setByteMatrixAsList", getByteMatrixAsList)
