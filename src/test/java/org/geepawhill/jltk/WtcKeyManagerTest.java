package org.geepawhill.jltk;

import org.junit.jupiter.api.*;

import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

public class WtcKeyManagerTest {
    public static final Path TEST_WORKING_FOLDER = Path.of("src", "bash");
    public static final String TEST_ROOT_FOLDER = "../../testFolder/root";
    public static final String TEST_HOME_FOLDER = "../../testFolder/home";

    TestFolder folder = new TestFolder();

    @Test
    void findsExistingKey() {
        folder.writeExistingKey("12345");
        WtcKeyManager manager = new WtcKeyManager(folder.root, folder.home);
        assertEquals("12345", manager.findOrMakeKey());
        folder.assertRootFiles();
        String key = folder.readRootWtcKey();
        folder.assertHomeFiles(key);
    }

    @Test
    void makesNonExistingKey() {
        WtcKeyManager manager = new WtcKeyManager(folder.root, folder.home);
        manager.findOrMakeKey();
        folder.assertRootFiles();
        String key = folder.readRootWtcKey();
        folder.assertHomeFiles(key);
    }

    @Test
    void bashFindsExistingKey() {
        folder.writeExistingKey("12345");
        BashRunner runner = new BashRunner("./wtc_key_manager.sh", TEST_ROOT_FOLDER, TEST_HOME_FOLDER);
        int result = runner.execute(TEST_WORKING_FOLDER);
        assertEquals(0, result);
        folder.assertRootFiles();
        String key = folder.readRootWtcKey();
        folder.assertHomeFiles(key);
    }

    @Test
    void bashMakesNonExistingKey() {
        BashRunner runner = new BashRunner("./wtc_key_manager.sh", TEST_ROOT_FOLDER, TEST_HOME_FOLDER);
        int result = runner.execute(TEST_WORKING_FOLDER);
        assertEquals(0, result);
        folder.assertRootFiles();
        String key = folder.readRootWtcKey();
        folder.assertHomeFiles(key);
    }

    @Test
    void bashPreCommitMakesNonExistingKey() {
        BashRunner runner = new BashRunner("./wtc_pre_commit.sh", TEST_ROOT_FOLDER, TEST_HOME_FOLDER);
        int result = runner.execute(TEST_WORKING_FOLDER);
        System.out.println(runner.io);
        assertEquals(0, result);
        folder.assertRootFiles();
        String key = folder.readRootWtcKey();
        folder.assertHomeFiles(key);
    }
}
