#https://stackoverflow.com/a/65637828
import socket
import json
from asyncio import IncompleteReadError  # only import the exception class

class SocketStreamReader:
    def __init__(self, sock: socket.socket):
        self._sock = sock
        self._recv_buffer = bytearray()

    def read(self, num_bytes: int = -1) -> bytes:
        raise NotImplementedError

    def readexactly(self, num_bytes: int) -> bytes:
        buf = bytearray(num_bytes)
        pos = 0
        while pos < num_bytes:
            n = self._recv_into(memoryview(buf)[pos:])
            if n == 0:
                raise IncompleteReadError(bytes(buf[:pos]), num_bytes)
            pos += n
        return bytes(buf)

    def readline(self) -> bytes:
        return self.readuntil(b"\n")

    def readuntil(self, separator: bytes = b"\n") -> bytes:
        if len(separator) != 1:
            raise ValueError("Only separators of length 1 are supported.")

        chunk = bytearray(4096)
        start = 0
        buf = bytearray(len(self._recv_buffer))
        bytes_read = self._recv_into(memoryview(buf))
        assert bytes_read == len(buf)

        while True:
            idx = buf.find(separator, start)
            if idx != -1:
                break

            start = len(self._recv_buffer)
            bytes_read = self._recv_into(memoryview(chunk))
            buf += memoryview(chunk)[:bytes_read]

        result = bytes(buf[: idx + 1])
        self._recv_buffer = b"".join(
            (memoryview(buf)[idx + 1 :], self._recv_buffer)
        )
        return result

    def _recv_into(self, view: memoryview) -> int:
        bytes_read = min(len(view), len(self._recv_buffer))
        view[:bytes_read] = self._recv_buffer[:bytes_read]
        self._recv_buffer = self._recv_buffer[bytes_read:]
        if bytes_read == len(view):
            return bytes_read
        bytes_read += self._sock.recv_into(view[bytes_read:])
        return bytes_read


def callJava_createSocket():
    global socketScriptTaskCallbackSocket
    socketScriptTaskCallbackSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    socketScriptTaskCallbackSocket.connect((socketScriptTaskCallbackServerHost, socketScriptTaskCallbackServerPort))
    socketScriptTaskCallbackSocket.sendall((socketScriptTaskCallbackContextUuid+"\n").encode('UTF-8'))
    global socketScriptTaskCallbackSocketReader
    socketScriptTaskCallbackSocketReader = SocketStreamReader(socketScriptTaskCallbackSocket)

def callJava_invokeSocket(methodName, parameters):
    socketScriptTaskCallbackSocket.sendall((methodName+";"+json.dumps(parameters)+"\n").encode('UTF-8'))
    returnExpression = socketScriptTaskCallbackSocketReader.readline().decode("UTF-8")
    if returnExpression.startswith("raise "):
        exec(returnExpression, globals())
        # fallback
        raise Exception(returnExpression)
    else:
        return eval(returnExpression, globals())

def callJava(methodName, *parameters):
    if 'socketScriptTaskCallbackContext' not in locals() and 'socketScriptTaskCallbackContext' not in globals():
        if 'socketScriptTaskCallbackContextUuid' in locals() or 'socketScriptTaskCallbackContextUuid' in globals():
            callJava_createSocket()
        else:
            raise Exception("IScriptTaskCallback not available")
    return callJava_invokeSocket(methodName, parameters)
