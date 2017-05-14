print("getCharacter")
if 'getCharacter' in locals():
	raise Exception("getCharacter already defined!")
getCharacter = putCharacter
getCharacterType = type(getCharacter)
print(getCharacterType)
print(getCharacter)
if(not isinstance(getCharacter, (unicode, str))):
	raise Exception("getCharacter not unicode or str!")

print("getCharacterVector")
if 'getCharacterVector' in locals():
	raise Exception("getCharacterVector already defined!")
getCharacterVector = putCharacterVector
getCharacterVectorType = type(getCharacterVector[0])
print(getCharacterVectorType)
print(getCharacterVector)
if(not isinstance(getCharacterVector[0], (unicode, str))):
	raise Exception("getCharacterVector not unicode or str!")

print("getCharacterVectorAsList")
if 'getCharacterVectorAsList' in locals():
	raise Exception("getCharacterVectorAsList already defined!")
getCharacterVectorAsList = putCharacterVectorAsList
getCharacterVectorAsListType = type(getCharacterVectorAsList[0])
print(getCharacterVectorAsListType)
print(getCharacterVectorAsList)
if(not isinstance(getCharacterVectorAsList[0], (unicode, str))):
	raise Exception("getCharacterVectorAsList not unicode or str!")

print("getCharacterMatrix")
if 'getCharacterMatrix' in locals():
	raise Exception("getCharacterMatrix already defined!")
getCharacterMatrix = putCharacterMatrix
getCharacterMatrixType = type(getCharacterMatrix[0][0])
print(getCharacterMatrixType)
print(getCharacterMatrix)
if(not isinstance(getCharacterMatrix[0][0], (unicode, str))):
	raise Exception("getCharacterMatrix not unicode or str!")

print("getCharacterMatrixAsList")
if 'getCharacterMatrixAsList' in locals():
	raise Exception("getCharacterMatrixAsList already defined!")
getCharacterMatrixAsList = putCharacterMatrixAsList
getCharacterMatrixAsListType = type(getCharacterMatrixAsList[0][0])
print(getCharacterMatrixAsListType)
print(getCharacterMatrixAsList)
if(not isinstance(getCharacterMatrixAsList[0][0], (unicode, str))):
	raise Exception("getCharacterMatrixAsList not unicode or str!")