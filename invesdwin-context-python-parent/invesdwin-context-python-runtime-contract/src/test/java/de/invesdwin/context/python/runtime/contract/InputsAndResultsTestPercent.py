print("getPercent")
if 'getPercent' in locals():
	raise Exception("getPercent already defined!")
getPercent = putPercent
getPercentType = type(getPercent)
print(getPercentType)
print(getPercent)
if(getPercentType is not float):
	raise Exception("getPercent not float!")

print("getPercentVector")
if 'getPercentVector' in locals():
	raise Exception("getPercentVector already defined!")
getPercentVector = putPercentVector
getPercentVectorType = type(getPercentVector[0])
print(getPercentVectorType)
print(getPercentVector)
if(getPercentVectorType is not float):
	raise Exception("getPercentVector not float!")

print("getPercentVectorAsList")
if 'getPercentVectorAsList' in locals():
	raise Exception("getPercentVectorAsList already defined!")
getPercentVectorAsList = putPercentVectorAsList
getPercentVectorAsListType = type(getPercentVectorAsList[0])
print(getPercentVectorAsListType)
print(getPercentVectorAsList)
if(getPercentVectorAsListType is not float):
	raise Exception("getPercentVectorAsList not float!")

print("getPercentMatrix")
if 'getPercentMatrix' in locals():
	raise Exception("getPercentMatrix already defined!")
getPercentMatrix = putPercentMatrix
getPercentMatrixType = type(getPercentMatrix[0][0])
print(getPercentMatrixType)
print(getPercentMatrix)
if(getPercentMatrixType is not float):
	raise Exception("getPercentMatrix not float!")

print("getPercentMatrixAsList")
if 'getPercentMatrixAsList' in locals():
	raise Exception("getPercentMatrixAsList already defined!")
getPercentMatrixAsList = putPercentMatrixAsList
getPercentMatrixAsListType = type(getPercentMatrixAsList[0][0])
print(getPercentMatrixAsListType)
print(getPercentMatrixAsList)
if(getPercentMatrixAsListType is not float):
	raise Exception("getPercentMatrixAsList not float!")
