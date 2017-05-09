package de.invesdwin.context.python.runtime.py4j.pool.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.apache.commons.io.FileUtils;

import com.github.rcaller.TempFileService;

import de.invesdwin.context.ContextProperties;
import de.invesdwin.util.lang.UniqueNameGenerator;

/**
 * Fixes memory leak with temp files and redirects to invesdwin process temp dir
 * 
 * @author subes
 *
 */
@NotThreadSafe
public class ModifiedTempFileService extends TempFileService {

    private static final UniqueNameGenerator FOLDER_UNIQUE_NAME_GENERATOR = new UniqueNameGenerator() {
        @Override
        protected long getInitialValue() {
            return 1;
        }
    };
    private final UniqueNameGenerator fileUniqueNameGenerator = new UniqueNameGenerator() {
        @Override
        protected long getInitialValue() {
            return 1;
        }
    };

    private final List<File> tempFiles = new ArrayList<File>();
    private final File folder = new File(ContextProperties.TEMP_DIRECTORY,
            FOLDER_UNIQUE_NAME_GENERATOR.get(ModifiedTempFileService.class.getSimpleName()));

    public ModifiedTempFileService() {
        try {
            FileUtils.forceMkdir(folder);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File createTempFile(final String prefix, final String suffix) throws IOException {
        final File file = new File(folder, fileUniqueNameGenerator.get(prefix) + suffix);
        FileUtils.touch(file);
        tempFiles.add(file);
        return file;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        FileUtils.deleteQuietly(folder);
    }

    @Override
    public void deleteRCallerTempFiles() {
        super.deleteRCallerTempFiles();
        for (final File tempFile : tempFiles) {
            FileUtils.deleteQuietly(tempFile);
        }
        //prevent memory leak
        tempFiles.clear();
    }

}
