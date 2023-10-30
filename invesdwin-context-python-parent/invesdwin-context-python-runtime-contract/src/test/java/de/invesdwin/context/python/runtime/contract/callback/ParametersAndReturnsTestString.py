print("getString")
if 'getString' in locals():
	raise Exception("getString already defined!")
getString = callback("getString")
getStringType = type(getString)
print(getStringType)
print(getString)
if(getStringType is not unicode):
	raise Exception("getString not unicode!")
callback("setString", getString)

print("getStringWithNull")
if 'getStringWithNull' in locals():
	raise Exception("getStringWithNull already defined!")
getStringWithNull = callback("getStringWithNull")
getStringWithNullType = type(getStringWithNull)
print(getStringWithNullType)
print(getStringWithNull)
if(getStringWithNull is not None):
	raise Exception("getStringWithNull not None!")
callback("setStringWithNull", getStringWithNull)

print("getStringVector")
if 'getStringVector' in locals():
	raise Exception("getStringVector already defined!")
getStringVector = callback("getStringVector")
getStringVectorType = type(getStringVector[0])
print(getStringVectorType)
print(getStringVector)
if(getStringVectorType is not unicode):
	raise Exception("getStringVector not unicode!")
callback("setStringVector", getStringVector)

print("getStringVectorWithNull")
if 'getStringVectorWithNull' in locals():
	raise Exception("getStringVectorWithNull already defined!")
getStringVectorWithNull = callback("getStringVectorWithNull")
getStringVectorWithNullType = type(getStringVectorWithNull[0])
print(getStringVectorWithNullType)
print(getStringVectorWithNull)
if(getStringVectorWithNullType is not unicode):
	raise Exception("getStringVectorWithNull not unicode!")
if(getStringVectorWithNull[1] is not None):
	raise Exception("getStringVectorWithNull[2] not None!")
callback("setStringVectorWithNull", getStringVectorWithNull)

print("getStringVectorAsList")
if 'getStringVectorAsList' in locals():
	raise Exception("getStringVectorAsList already defined!")
getStringVectorAsList = callback("getStringVectorAsList")
getStringVectorAsListType = type(getStringVectorAsList[0])
print(getStringVectorAsListType)
print(getStringVectorAsList)
if(getStringVectorAsListType is not unicode):
	raise Exception("getStringVectorAsList not unicode!")
callback("setStringVectorAsList", getStringVectorAsList)

print("getStringVectorAsListWithNull")
if 'getStringVectorAsListWithNull' in locals():
	raise Exception("getStringVectorAsListWithNull already defined!")
getStringVectorAsListWithNull = callback("getStringVectorAsListWithNull")
getStringVectorAsListWithNullType = type(getStringVectorAsListWithNull[0])
print(getStringVectorAsListWithNullType)
print(getStringVectorAsListWithNull)
if(getStringVectorAsListWithNullType is not unicode):
	raise Exception("getStringVectorAsListWithNull not unicode!")
if(getStringVectorAsListWithNull[1] is not None):
	raise Exception("getStringVectorAsListWithNull[1] not None!")
callback("setStringVectorAsListWithNull", getStringVectorAsListWithNull)

print("getStringMatrix")
if 'getStringMatrix' in locals():
	raise Exception("getStringMatrix already defined!")
getStringMatrix = callback("getStringMatrix")
getStringMatrixType = type(getStringMatrix[0][0])
print(getStringMatrixType)
print(getStringMatrix)
if(getStringMatrixType is not unicode):
	raise Exception("getStringMatrix not unicode!")
callback("setStringMatrix", getStringMatrix)

print("getStringMatrixWithNull")
if 'getStringMatrixWithNull' in locals():
	raise Exception("getStringMatrixWithNull already defined!")
getStringMatrixWithNull = callback("getStringMatrixWithNull")
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
callback("setStringMatrixWithNull", getStringMatrixWithNull)

print("getStringMatrixAsList")
if 'getStringMatrixAsList' in locals():
	raise Exception("getStringMatrixAsList already defined!")
getStringMatrixAsList = callback("getStringMatrixAsList")
getStringMatrixAsListType = type(getStringMatrixAsList[0][0])
print(getStringMatrixAsListType)
print(getStringMatrixAsList)
if(getStringMatrixAsListType is not unicode):
	raise Exception("getStringMatrixAsList not unicode!")
callback("setStringMatrixAsList", getStringMatrixAsList)

print("getStringMatrixAsListWithNull")
if 'getStringMatrixAsListWithNull' in locals():
	raise Exception("getStringMatrixAsListWithNull already defined!")
getStringMatrixAsListWithNull = callback("getStringMatrixAsListWithNull")
getStringMatrixAsListWithNullType = type(getStringMatrixAsListWithNull[0][1])
print(getStringMatrixAsListWithNullType)
print(getStringMatrixAsListWithNull)
if(getStringMatrixAsListWithNullType is not unicode):
	raise Exception("getStringMatrixAsListWithNull not unicode!")
if(getStringMatrixAsListWithNull[0][0] is not None):
	raise Exception("getStringMatrixAsListWithNull[0][0] not None!")
if(getStringMatrixAsListWithNull[1][1] is not None):
	raise Exception("getStringMatrixAsListWithNull[1][1] not None!")
if(getStringMatrixAsListWithNull[2][2] is not None):
	raise Exception("getStringMatrixAsListWithNull[2][2] not None!")
callback("setStringMatrixAsListWithNull", getStringMatrixAsListWithNull)
