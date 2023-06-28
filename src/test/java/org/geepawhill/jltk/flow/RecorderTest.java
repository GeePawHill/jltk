package org.geepawhill.jltk.flow;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static java.util.Collections.*;
import static org.junit.jupiter.api.Assertions.*;

public class RecorderTest {

    TestFolder folder = new TestFolder();
    GitInfo info = new GitInfo(folder.root, "branch", "committer", "email@somewhere.com", "last");

    @Test
    void roundTripWithKey() {
        folder.writeExistingKey("12345");
        Recorder first = new Recorder(info, folder.home);
        first.logRun();
        String firstKey = folder.readRootWtcKey();
        Recorder second = new Recorder(info, folder.home);
        second.logTest(singletonList("passed"), singletonList("failed"), singletonList("disabled"), singletonList("aborted"));
        String secondKey = folder.readRootWtcKey();
        roundTripIsCorrect(firstKey, secondKey);
    }

    @Test
    void roundTripWithNoKey() {
        folder.wipeRoot();
        folder.wipeHome();
        Recorder first = new Recorder(info, folder.home);
        first.logRun();
        String firstKey = folder.readRootWtcKey();
        Recorder second = new Recorder(info, folder.home);
        second.logTest(singletonList("passed"), singletonList("failed"), singletonList("disabled"), singletonList("aborted"));
        String secondKey = folder.readRootWtcKey();
        roundTripIsCorrect(firstKey, secondKey);
    }

    @Test
    void roundTripPostCommit() {
        folder.wipeRoot();
        folder.wipeHome();
        Recorder first = new Recorder(info, folder.home);
        first.logPostCommit();
        File[] files = folder.rootWtcFiles();
        assertEquals(2, files.length);
        assertEquals(0, folder.homeKeyFiles().length);
        List<String> lines = folder.readSoloRootLogLines();
        assertEquals(1, lines.size());
        String commitResult = new String(Base64.getDecoder().decode(lines.get(0)));
        String[] runResultLines = commitResult.split("\n");
        assertEquals(runResultLines[5], "type: commit");
    }

    private void roundTripIsCorrect(String firstKey, String secondKey) {
        assertEquals(firstKey, secondKey);
        folder.assertRootFiles();
        folder.assertHomeFiles(firstKey);
        List<String> lines = folder.readLog(firstKey, "branch", "email");
        assertEquals(2, lines.size());
        String runResult = new String(Base64.getDecoder().decode(lines.get(0)));
        String[] runResultLines = runResult.split("\n");
        assertEquals(runResultLines[5], "type: run");
        String testResult = new String(Base64.getDecoder().decode(lines.get(1)));
        String[] testResultLines = testResult.split("\n");
        assertEquals(testResultLines[5], "type: test");
    }


}
