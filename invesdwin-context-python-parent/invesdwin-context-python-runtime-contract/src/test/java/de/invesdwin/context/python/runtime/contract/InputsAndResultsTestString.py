print("getString")
if 'getString' in locals():
	raise Exception("getString already defined!")
getString = putString
getStringType = type(getString)
print(getStringType)
print(getString)
if(getStringType is not unicode):
	raise Exception("getString not unicode!")

print("getStringWithNull")
if 'getStringWithNull' in locals():
	raise Exception("getStringWithNull already defined!")
getStringWithNull = putStringWithNull
getStringWithNullType = type(getStringWithNull)
print(getStringWithNullType)
print(getStringWithNull)
if(getStringWithNull is not None):
	raise Exception("getStringWithNull not None!")

print("getStringVector")
if 'getStringVector' in locals():
	raise Exception("getStringVector already defined!")
getStringVector = putStringVector
getStringVectorType = type(getStringVector[0])
print(getStringVectorType)
print(getStringVector)
if(getStringVectorType is not unicode):
	raise Exception("getStringVector not unicode!")

print("getStringVectorWithNull")
if 'getStringVectorWithNull' in locals():
	raise Exception("getStringVectorWithNull already defined!")
getStringVectorWithNull = putStringVectorWithNull
getStringVectorWithNullType = type(getStringVectorWithNull[0])
print(getStringVectorWithNullType)
print(getStringVectorWithNull)
if(getStringVectorWithNullType is not unicode):
	raise Exception("getStringVectorWithNull not unicode!")
if(getStringVectorWithNull[1] is not None):
	raise Exception("getStringVectorWithNull[2] not None!")

print("getStringVectorAsList")
if 'getStringVectorAsList' in locals():
	raise Exception("getStringVectorAsList already defined!")
getStringVectorAsList = putStringVectorAsList
getStringVectorAsListType = type(getStringVectorAsList[0])
print(getStringVectorAsListType)
print(getStringVectorAsList)
if(getStringVectorAsListType is not unicode):
	raise Exception("getStringVectorAsList not unicode!")

print("getStringVectorAsListWithNull")
if 'getStringVectorAsListWithNull' in locals():
	raise Exception("getStringVectorAsListWithNull already defined!")
getStringVectorAsListWithNull = putStringVectorAsListWithNull
getStringVectorAsListWithNullType = type(getStringVectorAsListWithNull[0])
print(getStringVectorAsListWithNullType)
print(getStringVectorAsListWithNull)
if(getStringVectorAsListWithNullType is not unicode):
	raise Exception("getStringVectorAsListWithNull not unicode!")
if(getStringVectorAsListWithNull[1] is not None):
	raise Exception("getStringVectorAsListWithNull[1] not None!")

print("getStringMatrix")
if 'getStringMatrix' in locals():
	raise Exception("getStringMatrix already defined!")
getStringMatrix = putStringMatrix
getStringMatrixType = type(getStringMatrix[0][0])
print(getStringMatrixType)
print(getStringMatrix)
if(getStringMatrixType is not unicode):
	raise Exception("getStringMatrix not unicode!")

print("getStringMatrixWithNull")
if 'getStringMatrixWithNull' in locals():
	raise Exception("getStringMatrixWithNull already defined!")
getStringMatrixWithNull = putStringMatrixWithNull
getStringMatrixWithNullType = type(getStringMatrixWithNull[0][1])
print(getStringMatrixWithNullType)
print(getStringMatrixWithNull)
if(getStringMatrixWithNullType is not unicode):
	raise Exception("getStringMatrixWithNull not unicode!")
if(getStringMatrixWithNull[0][0] is not None):
	raise Exception("getStringMatrixWithNull[0][0] not None!")
if(getStringMatrixWithNull[1][1] is not None):
	raise Exception("getStringMatrixWithNull[1][1] not None!")
if(getStringMatrixWithNull[2][2] is not None):
	raise Exception("getStringMatrixWithNull[2][2] not None!")

print("getStringMatrixAsList")
if 'getStringMatrixAsList' in locals():
	raise Exception("getStringMatrixAsList already defined!")
getStringMatrixAsList = putStringMatrixAsList
getStringMatrixAsListType = type(getStringMatrixAsList[0][0])
print(getStringMatrixAsListType)
print(getStringMatrixAsList)
if(getStringMatrixAsListType is not unicode):
	raise Exception("getStringMatrixAsList not unicode!")

print("getStringMatrixAsListWithNull")
if 'getStringMatrixAsListWithNull' in locals():
	raise Exception("getStringMatrixAsListWithNull already defined!")
getStringMatrixAsListWithNull = putStringMatrixAsListWithNull
getStringMatrixAsListWithNullType = type(getStringMatrixAsListWithNull[0][1])
print(getStringMatrixAsListWithNullType)
print(getStringMatrixAsListWithNull)
if(getStringMatrixAsListWithNullType is not unicode):
	raise Exception("getStringMatrixAsListWithNull not unicode!")
if(getStringMatrixAsListWithNull[0][0] is not None):
	raise Exception("getStringMatrixAsListWithNull[0][0] not None!")
if(getStringMatrixAsListWithNull[1][1] is not None):
	raise Exception("getStringMatrixAsListWithNull[2][2] not None!")
if(getStringMatrixAsListWithNull[2][2] is not None):
	raise Exception("getStringMatrixAsListWithNull[3][3] not naNone!")
