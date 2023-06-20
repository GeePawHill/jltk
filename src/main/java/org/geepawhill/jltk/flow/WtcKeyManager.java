package org.geepawhill.jltk.flow;

import java.io.*;
import java.nio.file.*;
import java.time.*;

public class WtcKeyManager {

    final public Path root;
    final public Path home;
    final public Path localWtc;
    final public Path localKey;

    WtcKeyManager(Path root, Path home) {
        this.root = root;
        this.home = home;
        this.localWtc = root.resolve(ActionInfo.WTC_FOLDER);
        this.localKey = localWtc.resolve(ActionInfo.WTC_KEYFILE);
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
        String key = ActionInfo.fileTimeFrom(LocalDateTime.now());
        Files.createFile(localKey);
        Files.writeString(localKey, key);
    }

    private void makeHomeKey(String key) throws IOException {
        Path homeKey = home.resolve(
                Path.of(ActionInfo.WTC_FOLDER, key)
        );
        Files.createDirectories(homeKey);
    }
}
