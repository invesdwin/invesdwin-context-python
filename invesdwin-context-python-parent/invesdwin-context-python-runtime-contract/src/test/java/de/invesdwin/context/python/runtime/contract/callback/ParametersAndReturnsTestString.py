print("getString")
if 'getString' in locals():
	raise Exception("getString already defined!")
getString = callJava("getString")
getStringType = type(getString)
print(getStringType)
print(getString)
if(getStringType is not unicode):
	raise Exception("getString not unicode!")
callJava("setString", getString)

print("getStringWithNull")
if 'getStringWithNull' in locals():
	raise Exception("getStringWithNull already defined!")
getStringWithNull = callJava("getStringWithNull")
getStringWithNullType = type(getStringWithNull)
print(getStringWithNullType)
print(getStringWithNull)
if(getStringWithNull is not None):
	raise Exception("getStringWithNull not None!")
callJava("setStringWithNull", getStringWithNull)

print("getStringVector")
if 'getStringVector' in locals():
	raise Exception("getStringVector already defined!")
getStringVector = callJava("getStringVector")
getStringVectorType = type(getStringVector[0])
print(getStringVectorType)
print(getStringVector)
if(getStringVectorType is not unicode):
	raise Exception("getStringVector not unicode!")
callJava("setStringVector", getStringVector)

print("getStringVectorWithNull")
if 'getStringVectorWithNull' in locals():
	raise Exception("getStringVectorWithNull already defined!")
getStringVectorWithNull = callJava("getStringVectorWithNull")
getStringVectorWithNullType = type(getStringVectorWithNull[0])
print(getStringVectorWithNullType)
print(getStringVectorWithNull)
if(getStringVectorWithNullType is not unicode):
	raise Exception("getStringVectorWithNull not unicode!")
if(getStringVectorWithNull[1] is not None):
	raise Exception("getStringVectorWithNull[2] not None!")
callJava("setStringVectorWithNull", getStringVectorWithNull)

print("getStringVectorAsList")
if 'getStringVectorAsList' in locals():
	raise Exception("getStringVectorAsList already defined!")
getStringVectorAsList = callJava("getStringVectorAsList")
getStringVectorAsListType = type(getStringVectorAsList[0])
print(getStringVectorAsListType)
print(getStringVectorAsList)
if(getStringVectorAsListType is not unicode):
	raise Exception("getStringVectorAsList not unicode!")
callJava("setStringVectorAsList", getStringVectorAsList)

print("getStringVectorAsListWithNull")
if 'getStringVectorAsListWithNull' in locals():
	raise Exception("getStringVectorAsListWithNull already defined!")
getStringVectorAsListWithNull = callJava("getStringVectorAsListWithNull")
getStringVectorAsListWithNullType = type(getStringVectorAsListWithNull[0])
print(getStringVectorAsListWithNullType)
print(getStringVectorAsListWithNull)
if(getStringVectorAsListWithNullType is not unicode):
	raise Exception("getStringVectorAsListWithNull not unicode!")
if(getStringVectorAsListWithNull[1] is not None):
	raise Exception("getStringVectorAsListWithNull[1] not None!")
callJava("setStringVectorAsListWithNull", getStringVectorAsListWithNull)

print("getStringMatrix")
if 'getStringMatrix' in locals():
	raise Exception("getStringMatrix already defined!")
getStringMatrix = callJava("getStringMatrix")
getStringMatrixType = type(getStringMatrix[0][0])
print(getStringMatrixType)
print(getStringMatrix)
if(getStringMatrixType is not unicode):
	raise Exception("getStringMatrix not unicode!")
callJava("setStringMatrix", getStringMatrix)

print("getStringMatrixWithNull")
if 'getStringMatrixWithNull' in locals():
	raise Exception("getStringMatrixWithNull already defined!")
getStringMatrixWithNull = callJava("getStringMatrixWithNull")
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
callJava("setStringMatrixWithNull", getStringMatrixWithNull)

print("getStringMatrixAsList")
if 'getStringMatrixAsList' in locals():
	raise Exception("getStringMatrixAsList already defined!")
getStringMatrixAsList = callJava("getStringMatrixAsList")
getStringMatrixAsListType = type(getStringMatrixAsList[0][0])
print(getStringMatrixAsListType)
print(getStringMatrixAsList)
if(getStringMatrixAsListType is not unicode):
	raise Exception("getStringMatrixAsList not unicode!")
callJava("setStringMatrixAsList", getStringMatrixAsList)

print("getStringMatrixAsListWithNull")
if 'getStringMatrixAsListWithNull' in locals():
	raise Exception("getStringMatrixAsListWithNull already defined!")
getStringMatrixAsListWithNull = callJava("getStringMatrixAsListWithNull")
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
callJava("setStringMatrixAsListWithNull", getStringMatrixAsListWithNull)
