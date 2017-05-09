package de.invesdwin.context.python.runtime.cli.pool.internal;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.NotThreadSafe;

import org.fest.reflect.field.Invoker;

import com.github.rcaller.EventHandler;
import com.github.rcaller.MessageSaver;
import com.github.rcaller.TempFileService;
import com.github.rcaller.exception.ExecutionException;
import com.github.rcaller.rstuff.FailurePolicy;
import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCallerOptions;
import com.github.rcaller.rstuff.RCode;
import com.github.rcaller.rstuff.ROutputParser;
import com.github.rcaller.rstuff.RStreamHandler;

import de.invesdwin.context.python.runtime.cli.CliScriptTaskRunnerPython;
import de.invesdwin.context.r.runtime.contract.IScriptTaskRunnerR;
import de.invesdwin.util.lang.Reflections;
import de.invesdwin.util.lang.Strings;

@NotThreadSafe
public class ModifiedRCaller extends RCaller {

    private final TempFileService tempFileServiceCopy;
    private final MessageSaver errorMessageSaverCopy;
    @GuardedBy("curThreadForInterruptLock")
    private Thread curThreadForInterrupt;
    @GuardedBy("curThreadForInterruptLock")
    private boolean interruped;
    private final Object curThreadForInterruptLock = new Object();

    public ModifiedRCaller() {
        super(null, new ROutputParser(), newOutputStreamHandler(), newErrorStreamHandler(), new MessageSaver(),
                new ModifiedTempFileService(), RCallerOptions.create(FailurePolicy.CONTINUE, Long.MAX_VALUE));
        final Invoker<TempFileService> rcallerTempFileServiceField = Reflections.field("tempFileService")
                .ofType(TempFileService.class)
                .in(this);
        this.tempFileServiceCopy = rcallerTempFileServiceField.get();
        final Invoker<MessageSaver> rcallerErrorMessageSaverField = Reflections.field("errorMessageSaver")
                .ofType(MessageSaver.class)
                .in(this);
        this.errorMessageSaverCopy = rcallerErrorMessageSaverField.get();
        setRCode(RCode.create());
        quitOnErrorSetup();
    }

    private void quitOnErrorSetup() {
        //sleep 1 second in between so that the logs can be processed properly before the process quits
        getRCode().addRCode(
                "options(error = function() { warning(geterrmessage()); Sys.sleep(1); quit(save = \"no\", status = 1, runLast = FALSE) } )");
        getRCode().addRCode(CliScriptTaskRunnerPython.INTERNAL_RESULT_VARIABLE + " <- c()");
        runAndReturnResultOnline(CliScriptTaskRunnerPython.INTERNAL_RESULT_VARIABLE);
    }

    private static RStreamHandler newOutputStreamHandler() {
        final RStreamHandler output = new ModifiedRStreamHandler(null, "Output");
        output.addEventHandler(new EventHandler() {
            @Override
            public void messageReceived(final String senderName, final String msg) {
                if (Strings.isNotBlank(msg)) {
                    IScriptTaskRunnerR.LOG.debug(msg);
                }
            }
        });
        return output;
    }

    private static RStreamHandler newErrorStreamHandler() {
        final RStreamHandler error = new ModifiedRStreamHandler(null, "Error");
        error.addEventHandler(new EventHandler() {
            @Override
            public void messageReceived(final String senderName, final String msg) {
                if (Strings.isNotBlank(msg)) {
                    IScriptTaskRunnerR.LOG.warn(msg);
                }
            }
        });
        return error;
    }

    @Override
    public void setRCode(final RCode rcode) {
        super.setRCode(modifyRCode(rcode));
    }

    private RCode modifyRCode(final RCode rcode) {
        final Invoker<TempFileService> rcodeTempFileServiceField = Reflections.field("tempFileService")
                .ofType(TempFileService.class)
                .in(rcode);
        final TempFileService existingTempFileService = rcodeTempFileServiceField.get();
        if (existingTempFileService != null) {
            existingTempFileService.deleteRCallerTempFiles();
        }
        rcodeTempFileServiceField.set(tempFileServiceCopy);
        return rcode;
    }

    @Override
    public void deleteTempFiles() {
        super.deleteTempFiles();
        quitOnErrorSetup();
        errorMessageSaverCopy.resetMessage();
    }

    @Override
    public void startStreamConsumers(final Process process) {
        super.startStreamConsumers(process);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.waitFor();
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    synchronized (curThreadForInterruptLock) {
                        if (curThreadForInterrupt != null) {
                            interruped = true;
                            curThreadForInterrupt.interrupt();
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void runAndReturnResultOnline(final String var) throws ExecutionException {
        try {
            synchronized (curThreadForInterruptLock) {
                this.curThreadForInterrupt = Thread.currentThread();
            }
            try {
                super.runAndReturnResultOnline(var);
                synchronized (curThreadForInterruptLock) {
                    if (interruped) {
                        throw new IllegalStateException(errorMessageSaverCopy.getMessage().trim());
                    }
                }
            } catch (final Throwable t) {
                synchronized (curThreadForInterruptLock) {
                    if (interruped) {
                        throw new IllegalStateException(errorMessageSaverCopy.getMessage().trim(), t);
                    }
                }
            } finally {
                synchronized (curThreadForInterruptLock) {
                    this.curThreadForInterrupt = null;
                    this.interruped = false;
                }
            }
        } finally {
            getRCode().clearOnline();
        }
    }

}
