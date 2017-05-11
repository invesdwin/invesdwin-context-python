print("getCharacter")
if 'getCharacter' in locals():
	raise Exception("getCharacter already defined!")
getCharacter = putCharacter
getCharacterType = type(getCharacter)
print(getCharacterType)
print(getCharacter)
if(getCharacterType is not unicode):
	raise Exception("getCharacter not unicode!")

print("getCharacterVector")
if 'getCharacterVector' in locals():
	raise Exception("getCharacterVector already defined!")
getCharacterVector = putCharacterVector
getCharacterVectorType = type(getCharacterVector[0])
print(getCharacterVectorType)
print(getCharacterVector)
if(getCharacterVectorType is not unicode):
	raise Exception("getCharacterVector not unicode!")

print("getCharacterVectorAsList")
if 'getCharacterVectorAsList' in locals():
	raise Exception("getCharacterVectorAsList already defined!")
getCharacterVectorAsList = putCharacterVectorAsList
getCharacterVectorAsListType = type(getCharacterVectorAsList[0])
print(getCharacterVectorAsListType)
print(getCharacterVectorAsList)
if(getCharacterVectorAsListType is not unicode):
	raise Exception("getCharacterVectorAsList not unicode!")

print("getCharacterMatrix")
if 'getCharacterMatrix' in locals():
	raise Exception("getCharacterMatrix already defined!")
getCharacterMatrix = putCharacterMatrix
getCharacterMatrixType = type(getCharacterMatrix[0][0])
print(getCharacterMatrixType)
print(getCharacterMatrix)
if(getCharacterMatrixType is not unicode):
	raise Exception("getCharacterMatrix not unicode!")

print("getCharacterMatrixAsList")
if 'getCharacterMatrixAsList' in locals():
	raise Exception("getCharacterMatrixAsList already defined!")
getCharacterMatrixAsList = putCharacterMatrixAsList
getCharacterMatrixAsListType = type(getCharacterMatrixAsList[0][0])
print(getCharacterMatrixAsListType)
print(getCharacterMatrixAsList)
if(getCharacterMatrixAsListType is not unicode):
	raise Exception("getCharacterMatrixAsList not unicode!")