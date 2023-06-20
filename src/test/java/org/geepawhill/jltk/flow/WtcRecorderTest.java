package org.geepawhill.jltk.flow;

import org.junit.jupiter.api.*;

import java.util.*;

import static java.util.Collections.*;
import static org.junit.jupiter.api.Assertions.*;

public class WtcRecorderTest {

    TestFolder folder = new TestFolder();
    GitInfo info = new GitInfo(folder.home, folder.root, "branch", "committer", "email@somewhere.com");

    @Test
    void roundTripWithKey() {
        folder.writeExistingKey("12345");
        WtcRecorder first = new WtcRecorder(info);
        first.run();
        String firstKey = folder.readRootWtcKey();
        WtcRecorder second = new WtcRecorder(info);
        second.tests(singletonList("passed"), singletonList("failed"), singletonList("disabled"), singletonList("aborted"));
        String secondKey = folder.readRootWtcKey();
        roundTripIsCorrect(firstKey, secondKey);
    }

    @Test
    void roundTripWithNoKey() {
        folder.wipeRoot();
        folder.wipeHome();
        WtcRecorder first = new WtcRecorder(info);
        first.run();
        String firstKey = folder.readRootWtcKey();
        WtcRecorder second = new WtcRecorder(info);
        second.tests(singletonList("passed"), singletonList("failed"), singletonList("disabled"), singletonList("aborted"));
        String secondKey = folder.readRootWtcKey();
        roundTripIsCorrect(firstKey, secondKey);
    }

    private void roundTripIsCorrect(String firstKey, String secondKey) {
        assertEquals(firstKey, secondKey);
        folder.assertRootFiles();
        folder.assertHomeFiles(firstKey);
        List<String> lines = folder.readLog(firstKey, "branch", "email");
        assertEquals(2, lines.size());
        String runResult = new String(Base64.getDecoder().decode(lines.get(0)));
        String[] runResultLines = runResult.split("\n");
        assertEquals(runResultLines[4], "type: run");
        String testResult = new String(Base64.getDecoder().decode(lines.get(1)));
        String[] testResultLines = testResult.split("\n");
        assertEquals(testResultLines[4], "type: test");
    }
}
