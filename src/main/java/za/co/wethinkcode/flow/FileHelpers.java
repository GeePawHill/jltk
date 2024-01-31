package za.co.wethinkcode.flow;

import java.io.*;
import java.nio.file.*;

public class FileHelpers {
    public static final String JLTK_FOLDER = ".jltk";
    public static final String JLTK_LOG_SUFFIX = ".jltl";
    public static final String JLTK_TMP_SUFFIX = ".jltt";

    public static File[] temporaryFiles(Path root) {
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(JLTK_TMP_SUFFIX);
            }
        };
        File[] result = root.resolve(JLTK_FOLDER).toFile().listFiles(filter);
        if (result == null) return new File[0];
        return result;
    }

    public static File[] finalFiles(Path root) {
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(JLTK_LOG_SUFFIX);
            }
        };
        File[] result = root.resolve(JLTK_FOLDER).toFile().listFiles(filter);
        if (result == null) return new File[0];
        return result;
    }
}
