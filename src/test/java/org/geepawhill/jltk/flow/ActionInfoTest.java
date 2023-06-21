package org.geepawhill.jltk.flow;

import org.junit.jupiter.api.*;
import org.yaml.snakeyaml.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ActionInfoTest {

    @Test
    void gitInfoAndTypeConstructorWorks() {
        ActionInfo info = new ActionInfo("run");
        assertEquals(info.type, "run");
        assertTrue(info.passes.isEmpty());
        assertTrue(info.fails.isEmpty());
        assertTrue(info.disables.isEmpty());
        assertTrue(info.aborts.isEmpty());
    }

    @Test
    void testListsConstructorWorks() {
        List<String> passes = Collections.singletonList("pass");
        List<String> fails = Collections.singletonList("fail");
        List<String> disables = Collections.singletonList("disable");
        List<String> aborts = Collections.singletonList("abort");
        ActionInfo info = new ActionInfo("test", passes, fails, disables, aborts);
        assertEquals(info.type, "test");
        assertTrue(info.passes.contains("pass"));
        assertTrue(info.fails.contains("fail"));
        assertTrue(info.disables.contains("disable"));
        assertTrue(info.aborts.contains("abort"));
    }

    @Test
    void yamlRoundtrip() {
        List<String> passes = Collections.singletonList("pass");
        List<String> fails = Collections.singletonList("fail");
        List<String> disables = Collections.singletonList("disable");
        List<String> aborts = Collections.singletonList("abort");
        ActionInfo info = new ActionInfo("test", passes, fails, disables, aborts);
        YamlMap map = new YamlMap();
        info.putTo(map);
        String infoAsYaml = map.asString();

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
