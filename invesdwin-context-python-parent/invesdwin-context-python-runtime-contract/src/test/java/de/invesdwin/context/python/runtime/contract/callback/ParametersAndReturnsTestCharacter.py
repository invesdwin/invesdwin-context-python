print("getCharacter")
if 'getCharacter' in locals():
	raise Exception("getCharacter already defined!")
getCharacter = callback("getCharacter")
getCharacterType = type(getCharacter)
print(getCharacterType)
print(getCharacter)
if(not isinstance(getCharacter, (unicode, str))):
	raise Exception("getCharacter not unicode or str!")
callback("setCharacter", getCharacter)

print("getCharacterVector")
if 'getCharacterVector' in locals():
	raise Exception("getCharacterVector already defined!")
getCharacterVector = callback("getCharacterVector")
getCharacterVectorType = type(getCharacterVector[0])
print(getCharacterVectorType)
print(getCharacterVector)
if(not isinstance(getCharacterVector[0], (unicode, str))):
	raise Exception("getCharacterVector not unicode or str!")
callback("setCharacterVector", getCharacterVector)

print("getCharacterVectorAsList")
if 'getCharacterVectorAsList' in locals():
	raise Exception("getCharacterVectorAsList already defined!")
getCharacterVectorAsList = callback("getCharacterVectorAsList")
getCharacterVectorAsListType = type(getCharacterVectorAsList[0])
print(getCharacterVectorAsListType)
print(getCharacterVectorAsList)
if(not isinstance(getCharacterVectorAsList[0], (unicode, str))):
	raise Exception("getCharacterVectorAsList not unicode or str!")
callback("setCharacterVectorAsList", getCharacterVectorAsList)

print("getCharacterMatrix")
if 'getCharacterMatrix' in locals():
	raise Exception("getCharacterMatrix already defined!")
getCharacterMatrix = callback("getCharacterMatrix")
getCharacterMatrixType = type(getCharacterMatrix[0][0])
print(getCharacterMatrixType)
print(getCharacterMatrix)
if(not isinstance(getCharacterMatrix[0][0], (unicode, str))):
	raise Exception("getCharacterMatrix not unicode or str!")
callback("setCharacterMatrix", getCharacterMatrix)

print("getCharacterMatrixAsList")
if 'getCharacterMatrixAsList' in locals():
	raise Exception("getCharacterMatrixAsList already defined!")
getCharacterMatrixAsList = callback("getCharacterMatrixAsList")
getCharacterMatrixAsListType = type(getCharacterMatrixAsList[0][0])
print(getCharacterMatrixAsListType)
print(getCharacterMatrixAsList)
if(not isinstance(getCharacterMatrixAsList[0][0], (unicode, str))):
	raise Exception("getCharacterMatrixAsList not unicode or str!")
callback("setCharacterMatrixAsList", getCharacterMatrixAsList)
