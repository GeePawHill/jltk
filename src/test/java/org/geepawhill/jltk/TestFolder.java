package org.geepawhill.jltk;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.geepawhill.jltk.ActionInfo.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestFolder {

    public final Path root;
    public final Path home;
    public final Path rootWtc;
    public final Path homeWtc;
    public final Path rootWtcKey;

    public TestFolder(Path root, Path home) {
        this.root = root;
        this.home = home;
        this.rootWtc = root.resolve(WTC_FOLDER);
        this.homeWtc = home.resolve(WTC_FOLDER);
        this.rootWtcKey = rootWtc.resolve(WTC_KEYFILE);
        try {
            wipeHome();
            wipeRoot();
            Files.createDirectories(root);
            Files.createDirectories(home);
        } catch (Exception wrapped) {
            throw new RuntimeException("TestFolder construction failed.", wrapped);
        }
    }

    public TestFolder() {
        this(
                Path.of("testFolder", "root"),
                Path.of("testFolder", "home")
        );
    }

    public TestFolder(Path root) {
        this(root, Path.of("testData", "home"));
    }

    public void writeExistingKey(String key) {
        try {
            Files.createDirectories(rootWtc);
            Files.writeString(rootWtcKey, key);
        } catch (Exception wrapped) {
            throw new RuntimeException("Cannot write existing key.", wrapped);
        }
    }

    public void assertRootFiles() {
        assertTrue(root.toFile().exists() && root.toFile().isDirectory());
        assertTrue(rootWtc.toFile().exists() && rootWtc.toFile().isDirectory(), "Missing rootWtc [" + rootWtc.toString() + "]");
        assertTrue(rootWtcKey.toFile().exists() && rootWtcKey.toFile().isFile(), "Missing root/.wtc/wtc.key file.");
    }

    public String readRootWtcKey() {
        try {
            return Files.readString(rootWtcKey);
        } catch (Exception wrapped) {
            throw new RuntimeException("Cannot write existing key.", wrapped);
        }
    }

    public void assertHomeFiles(String key) {
        assertTrue(home.toFile().exists() && home.toFile().isDirectory());
        assertTrue(homeWtc.toFile().exists() && homeWtc.toFile().isDirectory());
        Path homeWtcKeyFolder = homeWtc.resolve(key.trim());
        assertTrue(homeWtcKeyFolder.toFile().exists() && homeWtcKeyFolder.toFile().isDirectory());
    }

    void wipeHome() {
        recursivelyWipe(home.toFile());
    }

    public List<String> readLog(String key, String branch, String shortEmail) {
        try {
            Path homeWtcKeyFolder = homeWtc.resolve(key);
            Path homeWtcLog = homeWtcKeyFolder.resolve(branch + "_" + shortEmail + ".wtc");
            return Files.readAllLines(homeWtcLog);
        } catch (Exception wrapped) {
            throw new RuntimeException("Cannot read log.", wrapped);
        }
    }

    File[] homeWtcFiles() {
        return home.resolve(WTC_FOLDER).toFile().listFiles();
    }

    File[] homeFiles() {
        return home.toFile().listFiles();
    }

    File[] rootFiles() {
        return root.toFile().listFiles();
    }

    public File[] rootWtcFiles() {
        return root.resolve(WTC_FOLDER).toFile().listFiles();
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

}