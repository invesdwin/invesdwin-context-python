package de.invesdwin.context.python.runtime.cli.pool.internal;

import java.io.InputStream;
import java.util.ConcurrentModificationException;

import javax.annotation.concurrent.NotThreadSafe;

import com.github.rcaller.rstuff.RStreamHandler;

@NotThreadSafe
public class ModifiedRStreamHandler extends RStreamHandler {

    public ModifiedRStreamHandler(final InputStream stream, final String name) {
        super(stream, name);
    }

    @Override
    public void run() {
        try {
            super.run();
        } catch (final ConcurrentModificationException e) {
            //retry on concurrent modification error because the internal event handlers got modified...
            run();
        }
    }

}
