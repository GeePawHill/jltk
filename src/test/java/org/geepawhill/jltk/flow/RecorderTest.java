package org.geepawhill.jltk.flow;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RecorderTest {

    TestFolder folder = new TestFolder();
    GitInfo info = new GitInfo(folder.root, "branch", "committer", "email@somewhere.com", "last");

    @Test
    void roundTripTemporaryLog() throws IOException {
        File[] before = folder.temporaryFiles();
        assertEquals(0, before.length);
        Recorder first = new Recorder(info);
        first.logRun();
        File[] temps = folder.temporaryFiles();
        assertEquals(1, temps.length);
        List<String> lines = Files.readAllLines(temps[0].toPath());
        assertEquals(1, lines.size());
        String firstEntry = new String(Base64.getDecoder().decode(lines.get(0)));
        String[] resultYaml = firstEntry.split("\n");
        assertEquals(resultYaml[5], "type: run");
    }
}
