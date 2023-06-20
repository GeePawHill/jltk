package org.geepawhill.jltk.flow;

import org.junit.jupiter.api.*;
import org.yaml.snakeyaml.*;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

import static java.util.Collections.*;
import static org.junit.jupiter.api.Assertions.*;

public class ActionInfoTest {

    @Test
    void timestampConstructorWorks() {
        LocalDateTime time = LocalDateTime.of(2020, Month.JANUARY, 2, 12, 13, 14, 15);
        ActionInfo info = new ActionInfo(
                new GitInfo(),
                "test",
                time,
                emptyList(),
                emptyList(),
                emptyList(),
                emptyList()
        );
        assertEquals("20200102121314", info.filetime);
        assertEquals("2020-01-02T12:13:14", info.timestamp);
    }

    @Test
    void knowsFolders() throws IOException {
        ActionInfo info = new ActionInfo(new GitInfo(), "run");
        String expectedRootWtc = Path.of(".wtc").toFile().getCanonicalPath();
        assertEquals(expectedRootWtc, info.rootWtc().toFile().getCanonicalPath());
        String expectedHomeWtc = Path.of(System.getProperty("user.home"), ".wtc").toFile().getCanonicalPath();
        assertEquals(expectedHomeWtc, info.homeWtc().toFile().getCanonicalPath());
    }

    @Test
    void loadsOnConstruction() {
        ActionInfo info = new ActionInfo(new GitInfo(), "run");
        assertEquals(info.type, "run");
        assertTrue(info.passes.isEmpty());
        assertTrue(info.fails.isEmpty());
        assertTrue(info.disables.isEmpty());
        assertTrue(info.aborts.isEmpty());
    }

    @Test
    void loadsOnConstructionForTest() {
        List<String> passes = new ArrayList<>();
        passes.add("pass");
        List<String> fails = new ArrayList<>();
        fails.add("fail");
        List<String> disables = new ArrayList<>();
        disables.add("disable");
        List<String> aborts = new ArrayList<>();
        aborts.add("abort");
        ActionInfo info = new ActionInfo(new GitInfo(), passes, fails, disables, aborts);
        assertEquals(info.type, "test");
        assertTrue(info.passes.contains("pass"));
        assertTrue(info.fails.contains("fail"));
        assertTrue(info.disables.contains("disable"));
        assertTrue(info.aborts.contains("abort"));
    }

    @Test
    void yamlRoundtrip() {
        List<String> passes = new ArrayList<>();
        passes.add("pass");
        List<String> fails = new ArrayList<>();
        fails.add("fail");
        List<String> disables = new ArrayList<>();
        disables.add("disable");
        List<String> aborts = new ArrayList<>();
        aborts.add("abort");
        ActionInfo info = new ActionInfo(new GitInfo(), passes, fails, disables, aborts);
        String infoAsYaml = info.toYaml();
        Yaml yaml = new Yaml();
        Map<String, Object> actual = yaml.load(infoAsYaml);

        assertArrayContains(actual, "passes", "pass");
        assertArrayContains(actual, "fails", "fail");
        assertArrayContains(actual, "disables", "disable");
        assertArrayContains(actual, "aborts", "abort");
    }

    void assertArrayContains(Map<String, Object> result, String key, String value) {
        assertTrue(result.get(key) instanceof ArrayList<?>);
        ArrayList<?> actual = (ArrayList<?>) result.get(key);
        assertTrue(actual.contains(value), "Could not find " + key);
    }

}
