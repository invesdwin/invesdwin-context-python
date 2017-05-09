print("getDouble")
getDouble <- putDouble
print(typeof(getDouble))
print(getDouble)
if(typeof(getDouble) != "double"){
	stop("getDouble not double!")
}

print("getDoubleVector")
getDoubleVector <- putDoubleVector
print(typeof(getDoubleVector))
print(getDoubleVector)
if(typeof(getDoubleVector) != "double"){
	stop("getDoubleVector not double!")
}

print("getDoubleVectorAsList")
getDoubleVectorAsList <- putDoubleVectorAsList
print(typeof(getDoubleVectorAsList))
print(getDoubleVectorAsList)
if(typeof(getDoubleVectorAsList) != "double"){
	stop("getDoubleVectorAsList not double!")
}

print("getDoubleMatrix")
getDoubleMatrix <- putDoubleMatrix
print(typeof(getDoubleMatrix))
print(getDoubleMatrix)
if(typeof(getDoubleMatrix) != "double"){
	stop("getDoubleMatrix not double!")
}

print("getDoubleMatrixAsList")
getDoubleMatrixAsList <- putDoubleMatrixAsList
print(typeof(getDoubleMatrixAsList))
print(getDoubleMatrixAsList)
if(typeof(getDoubleMatrixAsList) != "double"){
	stop("getDoubleMatrixAsList not double!")
}
