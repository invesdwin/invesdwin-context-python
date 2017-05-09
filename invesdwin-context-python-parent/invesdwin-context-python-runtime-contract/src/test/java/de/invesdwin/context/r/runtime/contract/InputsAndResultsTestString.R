print("getString")
getString <- putString
print(typeof(getString))
print(getString)
if(typeof(getString) != "character"){
	stop("getString not character!")
}

print("getStringNull")
getStringNull <- putStringNull
print(typeof(getStringNull))
print(getStringNull)
if(typeof(getStringNull) != "character"){
	stop("getStringNull not character!")
}
if(!is.na(getStringNull)){
	stop("getStringNull not na!")
}

print("getStringVector")
getStringVector <- putStringVector
print(typeof(getStringVector))
print(getStringVector)
if(typeof(getStringVector) != "character"){
	stop("getStringVector not character!")
}


print("getStringVectorNull")
getStringVectorNull <- putStringVectorNull
print(typeof(getStringVectorNull))
print(getStringVectorNull)
if(typeof(getStringVectorNull) != "character"){
	stop("getStringVectorNull not character!")
}
if(!is.na(getStringVectorNull[2])){
	stop("getStringVectorNull[2] not na!")
}

print("getStringVectorAsList")
getStringVectorAsList <- putStringVectorAsList
print(typeof(getStringVectorAsList))
print(getStringVectorAsList)
if(typeof(getStringVectorAsList) != "character"){
	stop("getStringVectorAsList not character!")
}

print("getStringVectorAsListNull")
getStringVectorAsListNull <- putStringVectorAsListNull
print(typeof(getStringVectorAsListNull))
print(getStringVectorAsListNull)
if(typeof(getStringVectorAsListNull) != "character"){
	stop("getStringVectorAsListNull not character!")
}
if(!is.na(getStringVectorAsListNull[2])){
	stop("getStringVectorAsListNull[2] not na!")
}

print("getStringMatrix")
getStringMatrix <- putStringMatrix
print(typeof(getStringMatrix))
print(getStringMatrix)
if(typeof(getStringMatrix) != "character"){
	stop("getStringMatrix not character!")
}


print("getStringMatrixNull")
getStringMatrixNull <- putStringMatrixNull
print(typeof(getStringMatrixNull))
print(getStringMatrixNull)
if(typeof(getStringMatrixNull) != "character"){
	stop("getStringMatrixNull not character!")
}
if(!is.na(getStringMatrixNull[1][1])){
	stop("getStringMatrixNull[1][1] not na!")
}
if(!is.na(getStringMatrixNull[2][2])){
	stop("getStringMatrixNull[2][2] not na!")
}
if(!is.na(getStringMatrixNull[3][3])){
	stop("getStringMatrixNull[3][3] not na!")
}

print("getStringMatrixAsList")
getStringMatrixAsList <- putStringMatrixAsList
print(typeof(getStringMatrixAsList))
print(getStringMatrixAsList)
if(typeof(getStringMatrixAsList) != "character"){
	stop("getStringMatrixAsList not character!")
}

print("getStringMatrixAsListNull")
getStringMatrixAsListNull <- putStringMatrixAsListNull
print(typeof(getStringMatrixAsListNull))
print(getStringMatrixAsListNull)
if(typeof(getStringMatrixAsListNull) != "character"){
	stop("getStringMatrixAsListNull not character!")
}
if(!is.na(getStringMatrixAsListNull[1][1])){
	stop("getStringMatrixAsListNull[1][1] not na!")
}
if(!is.na(getStringMatrixAsListNull[2][2])){
	stop("getStringMatrixAsListNull[2][2] not na!")
}
if(!is.na(getStringMatrixAsListNull[3][3])){
	stop("getStringMatrixAsListNull[3][3] not na!")
}
