print("getInteger")
getInteger <- putInteger
print(typeof(getInteger))
print(getInteger)
if(typeof(getInteger) != "integer"){
	stop("getInteger not integer!")
}

print("getIntegerVector")
getIntegerVector <- putIntegerVector
print(typeof(getIntegerVector))
print(getIntegerVector)
if(typeof(getIntegerVector) != "integer"){
	stop("getIntegerVector not integer!")
}

print("getIntegerVectorAsList")
getIntegerVectorAsList <- putIntegerVectorAsList
print(typeof(getIntegerVectorAsList))
print(getIntegerVectorAsList)
if(typeof(getIntegerVectorAsList) != "integer"){
	stop("getIntegerVectorAsList not integer!")
}

print("getIntegerMatrix")
getIntegerMatrix <- putIntegerMatrix
print(typeof(getIntegerMatrix))
print(getIntegerMatrix)
if(typeof(getIntegerMatrix) != "integer"){
	stop("getIntegerMatrix not integer!")
}

print("getIntegerMatrixAsList")
getIntegerMatrixAsList <- putIntegerMatrixAsList
print(typeof(getIntegerMatrixAsList))
print(getIntegerMatrixAsList)
if(typeof(getIntegerMatrixAsList) != "integer"){
	stop("getIntegerMatrixAsList not integer!")
}
