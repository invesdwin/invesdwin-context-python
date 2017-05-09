print("getBoolean")
getBoolean <- putBoolean
print(typeof(getBoolean))
print(getBoolean)
if(typeof(getBoolean) != "logical"){
	stop("getBoolean not logical!")
}

print("getBooleanVector")
getBooleanVector <- putBooleanVector
print(typeof(getBooleanVector))
print(getBooleanVector)
if(typeof(getBooleanVector) != "logical"){
	stop("getBooleanVector not logical!")
}

print("getBooleanVectorAsList")
getBooleanVectorAsList <- putBooleanVectorAsList
print(typeof(getBooleanVectorAsList))
print(getBooleanVectorAsList)
if(typeof(getBooleanVectorAsList) != "logical"){
	stop("getBooleanVectorAsList not logical!")
}

print("getBooleanMatrix")
getBooleanMatrix <- putBooleanMatrix
print(typeof(getBooleanMatrix))
print(getBooleanMatrix)
if(typeof(getBooleanMatrix) != "logical"){
	stop("getBooleanMatrix not logical!")
}

print("getBooleanMatrixAsList")
getBooleanMatrixAsList <- putBooleanMatrixAsList
print(typeof(getBooleanMatrixAsList))
print(getBooleanMatrixAsList)
if(typeof(getBooleanMatrixAsList) != "logical"){
	stop("getBooleanMatrixAsList not logical!")
}
