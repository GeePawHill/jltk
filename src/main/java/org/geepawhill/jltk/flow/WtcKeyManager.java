package org.geepawhill.jltk.flow;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;

public class WtcKeyManager {

    final public Path root;
    final public Path home;
    final public Path localWtc;
    final public Path localKey;

    WtcKeyManager(Path root, Path home) {
        this.root = root;
        this.home = home;
        this.localWtc = root.resolve(ActionInfo.JLTK_FOLDER);
        this.localKey = localWtc.resolve(ActionInfo.JLTK_KEY);
    }

    public String findOrMakeKey() {
        try {
            if (!localKey.toFile().exists()) makeLocalKey();
            String key = Files.readString(localKey).trim();
            makeHomeKey(key);
            return key;
        } catch (Exception wrapped) {
            throw new RuntimeException("Cannot find or make key.", wrapped);
        }
    }

    public void makeLocalKey() throws IOException {
        Files.createDirectories(localWtc);
        String key = fileTimeFrom(LocalDateTime.now());
        Files.createFile(localKey);
        Files.writeString(localKey, key);
    }

    private void makeHomeKey(String key) throws IOException {
        Path homeKey = home.resolve(
                Path.of(ActionInfo.JLTK_FOLDER, key)
        );
        Files.createDirectories(homeKey);
    }

    /**
     * Convert a LocalDateTime into a string of the form YYYYMMDDHHMMSS
     *
     * @param time
     * @return
     */
    static String fileTimeFrom(LocalDateTime time) {
        return time.format(filetimeFormatter);
    }

    static DateTimeFormatter filetimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddkkmmss");

}
