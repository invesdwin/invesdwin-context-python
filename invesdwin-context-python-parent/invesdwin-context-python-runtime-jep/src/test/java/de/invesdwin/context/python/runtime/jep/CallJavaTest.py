print("putUuid")
print(putUuid)

from de.invesdwin.context.python.runtime.jep import CallJavaTest

getSecret = CallJavaTest.getSecret(putUuid)

print("getSecret")
print(getSecret)
