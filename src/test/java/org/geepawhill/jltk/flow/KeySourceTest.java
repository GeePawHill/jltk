package org.geepawhill.jltk.flow;

import org.junit.jupiter.api.*;

import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

public class KeySourceTest {
    public static final Path TEST_WORKING_FOLDER = Path.of("src", "bash");
    public static final String TEST_ROOT_FOLDER = "../../testFolder/root";
    public static final String TEST_HOME_FOLDER = "../../testFolder/home";

    TestFolder folder = new TestFolder();

    @Test
    void findsExistingKey() {
        folder.writeExistingKey("12345");
        KeySource manager = new KeySource(folder.root, folder.home);
        assertEquals("12345", manager.findOrMakeKey());
        folder.assertRootFiles();
        String key = folder.readRootWtcKey();
        folder.assertHomeFiles(key);
    }

    @Test
    void makesNonExistingKey() {
        KeySource manager = new KeySource(folder.root, folder.home);
        manager.findOrMakeKey();
        folder.assertRootFiles();
        String key = folder.readRootWtcKey();
        folder.assertHomeFiles(key);
    }
}
