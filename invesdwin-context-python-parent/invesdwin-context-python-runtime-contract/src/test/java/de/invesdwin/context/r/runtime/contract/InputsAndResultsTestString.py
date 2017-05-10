print("getString")
if 'getString' in globals():
	raise Exception("getString already defined!")
getString = putString
getStringType = type(getString)
print(getStringType)
print(getString)
if(getStringType is not unicode):
	raise Exception("getString not unicode!")

print("getStringNull")
if 'getStringNull' in globals():
	raise Exception("getStringNull already defined!")
getStringNull = putStringNull
getStringNullType = type(getStringNull)
print(getStringNullType)
print(getStringNull)
if(getStringNull is not None):
	raise Exception("getStringNull not None!")

print("getStringVector")
if 'getStringVector' in globals():
	raise Exception("getStringVector already defined!")
getStringVector = putStringVector
getStringVectorType = type(getStringVector[0])
print(getStringVectorType)
print(getStringVector)
if(getStringVectorType is not unicode):
	raise Exception("getStringVector not unicode!")

print("getStringVectorNull")
if 'getStringVectorNull' in globals():
	raise Exception("getStringVectorNull already defined!")
getStringVectorNull = putStringVectorNull
getStringVectorNullType = type(getStringVectorNull[0])
print(getStringVectorNullType)
print(getStringVectorNull)
if(getStringVectorNullType is not unicode):
	raise Exception("getStringVectorNull not unicode!")
if(getStringVectorNull[1] is not None):
	raise Exception("getStringVectorNull[2] not None!")

print("getStringVectorAsList")
if 'getStringVectorAsList' in globals():
	raise Exception("getStringVectorAsList already defined!")
getStringVectorAsList = putStringVectorAsList
getStringVectorAsListType = type(getStringVectorAsList[0])
print(getStringVectorAsListType)
print(getStringVectorAsList)
if(getStringVectorAsListType is not unicode):
	raise Exception("getStringVectorAsList not unicode!")

print("getStringVectorAsListNull")
if 'getStringVectorAsListNull' in globals():
	raise Exception("getStringVectorAsListNull already defined!")
getStringVectorAsListNull = putStringVectorAsListNull
getStringVectorAsListNullType = type(getStringVectorAsListNull[0])
print(getStringVectorAsListNullType)
print(getStringVectorAsListNull)
if(getStringVectorAsListNullType is not unicode):
	raise Exception("getStringVectorAsListNull not unicode!")
if(getStringVectorAsListNull[1] is not None):
	raise Exception("getStringVectorAsListNull[1] not None!")

print("getStringMatrix")
if 'getStringMatrix' in globals():
	raise Exception("getStringMatrix already defined!")
getStringMatrix = putStringMatrix
getStringMatrixType = type(getStringMatrix[0][0])
print(getStringMatrixType)
print(getStringMatrix)
if(getStringMatrixType is not unicode):
	raise Exception("getStringMatrix not unicode!")

print("getStringMatrixNull")
if 'getStringMatrixNull' in globals():
	raise Exception("getStringMatrixNull already defined!")
getStringMatrixNull = putStringMatrixNull
getStringMatrixNullType = type(getStringMatrixNull[0][1])
print(getStringMatrixNullType)
print(getStringMatrixNull)
if(getStringMatrixNullType is not unicode):
	raise Exception("getStringMatrixNull not unicode!")
if(getStringMatrixNull[0][0] is not None):
	raise Exception("getStringMatrixNull[0][0] not None!")
if(getStringMatrixNull[1][1] is not None):
	raise Exception("getStringMatrixNull[1][1] not None!")
if(getStringMatrixNull[2][2] is not None):
	raise Exception("getStringMatrixNull[2][2] not None!")

print("getStringMatrixAsList")
if 'getStringMatrixAsList' in globals():
	raise Exception("getStringMatrixAsList already defined!")
getStringMatrixAsList = putStringMatrixAsList
getStringMatrixAsListType = type(getStringMatrixAsList[0][0])
print(getStringMatrixAsListType)
print(getStringMatrixAsList)
if(getStringMatrixAsListType is not unicode):
	raise Exception("getStringMatrixAsList not unicode!")

print("getStringMatrixAsListNull")
if 'getStringMatrixAsListNull' in globals():
	raise Exception("getStringMatrixAsListNull already defined!")
getStringMatrixAsListNull = putStringMatrixAsListNull
getStringMatrixAsListNullType = type(getStringMatrixAsListNull[0][1])
print(getStringMatrixAsListNullType)
print(getStringMatrixAsListNull)
if(getStringMatrixAsListNullType is not unicode):
	raise Exception("getStringMatrixAsListNull not unicode!")
if(getStringMatrixAsListNull[0][0] is not None):
	raise Exception("getStringMatrixAsListNull[0][0] not None!")
if(getStringMatrixAsListNull[1][1] is not None):
	raise Exception("getStringMatrixAsListNull[2][2] not None!")
if(getStringMatrixAsListNull[2][2] is not None):
	raise Exception("getStringMatrixAsListNull[3][3] not naNone!")
