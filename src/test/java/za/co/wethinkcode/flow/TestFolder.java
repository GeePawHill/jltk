package za.co.wethinkcode.flow;

import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestFolder {

    public final Path root;
    public final Path rootWtc;

    public TestFolder(Path root) {
        this.root = root;
        this.rootWtc = root.resolve(FileHelpers.JLTK_FOLDER);
        try {
            wipeRoot();
            Files.createDirectories(root);
        } catch (Exception wrapped) {
            throw new RuntimeException("TestFolder construction failed.", wrapped);
        }
    }

    public TestFolder() {
        this(Path.of("testFolder", "root"));
    }

    public void assertRootTree() {
        assertTrue(root.toFile().exists() && root.toFile().isDirectory());
        assertTrue(rootWtc.toFile().exists() && rootWtc.toFile().isDirectory(), "Missing rootWtc [" + rootWtc.toString() + "]");
    }

    void wipeRoot() {
        recursivelyWipe(root.toFile());
    }

    void recursivelyDelete(File directoryToBeDeleted) {
        recursivelyWipe(directoryToBeDeleted);
        directoryToBeDeleted.delete();
    }

    private void recursivelyWipe(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                recursivelyDelete(file);
            }
        }
    }

    public File[] temporaryFiles() {
        return FileHelpers.temporaryFiles(root);
    }

    public File[] finalFiles() {
        return FileHelpers.finalFiles(root);
    }
}
