package de.invesdwin.context.python.runtime.contract.callback.socket;

import java.io.IOException;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.context.integration.channel.async.IAsynchronousHandler;
import de.invesdwin.context.integration.channel.async.IAsynchronousHandlerContext;
import de.invesdwin.util.lang.string.Strings;

@NotThreadSafe
public class SocketScriptTaskCallbackServerHandler implements IAsynchronousHandler<String, String> {

    private SocketScriptTaskCallbackContext callbackContext;

    @Override
    public String open(final IAsynchronousHandlerContext<String> context) throws IOException {
        return null;
    }

    @Override
    public String idle(final IAsynchronousHandlerContext<String> context) throws IOException {
        return null;
    }

    @Override
    public String handle(final IAsynchronousHandlerContext<String> context, final String input) throws IOException {
        if (callbackContext == null) {
            callbackContext = SocketScriptTaskCallbackContext.getContext(input);
            if (callbackContext == null) {
                throw new IllegalArgumentException(
                        SocketScriptTaskCallbackContext.class.getSimpleName() + " not found for uuid: " + input);
            }
            return null;
        }
        final int indexOfSeparator = Strings.indexOf(input, ";");
        if (indexOfSeparator <= 0) {
            throw new IllegalArgumentException("requiring request format [<methodName>;<args>]: " + input);
        }
        final String methodName = input.substring(0, indexOfSeparator);
        final String args = input.substring(indexOfSeparator + 1, input.length());
        return callbackContext.invoke(methodName, args);
    }

    @Override
    public void outputFinished(final IAsynchronousHandlerContext<String> context) throws IOException {}

    @Override
    public void close() throws IOException {}

}
