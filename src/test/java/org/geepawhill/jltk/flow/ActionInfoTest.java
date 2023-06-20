package org.geepawhill.jltk.flow;

import org.junit.jupiter.api.*;
import org.yaml.snakeyaml.*;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

import static java.util.Collections.*;
import static org.geepawhill.jltk.flow.ActionInfo.*;
import static org.junit.jupiter.api.Assertions.*;

public class ActionInfoTest {

    @Test
    void gitInfoAndTypeConstructorWorks() {
        ActionInfo info = new ActionInfo(new GitInfo(), "run");
        assertEquals(info.type, "run");
        assertTrue(info.passes.isEmpty());
        assertTrue(info.fails.isEmpty());
        assertTrue(info.disables.isEmpty());
        assertTrue(info.aborts.isEmpty());
    }

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
    void testListsConstructorWorks() {
        List<String> passes = Collections.singletonList("pass");
        List<String> fails = Collections.singletonList("fail");
        List<String> disables = Collections.singletonList("disable");
        List<String> aborts = Collections.singletonList("abort");
        ActionInfo info = new ActionInfo(new GitInfo(), passes, fails, disables, aborts);
        assertEquals(info.type, "test");
        assertTrue(info.passes.contains("pass"));
        assertTrue(info.fails.contains("fail"));
        assertTrue(info.disables.contains("disable"));
        assertTrue(info.aborts.contains("abort"));
    }

    @Test
    void knowsFolders() throws IOException {
        ActionInfo info = new ActionInfo(new GitInfo(), "run");
        String expectedRootJltk = Path.of(JLTK_FOLDER).toFile().getCanonicalPath();
        assertEquals(expectedRootJltk, info.rootJltk().toFile().getCanonicalPath());
        String expectedHomeJltk = Path.of(System.getProperty("user.home"), JLTK_FOLDER).toFile().getCanonicalPath();
        assertEquals(expectedHomeJltk, info.homeJltk().toFile().getCanonicalPath());
    }

    @Test
    void yamlRoundtrip() {
        List<String> passes = Collections.singletonList("pass");
        List<String> fails = Collections.singletonList("fail");
        List<String> disables = Collections.singletonList("disable");
        List<String> aborts = Collections.singletonList("abort");
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
