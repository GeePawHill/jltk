package org.geepawhill.jltk.flow;

import java.util.*;

import static java.util.Collections.*;

public class ActionInfo implements MapAppender {
    public static final String JLTK_FOLDER = ".jltk";
    public static final String JLTK_KEY = "jltk.key";

    public final String type;
    public final List<String> passes = new ArrayList<>();
    public final List<String> fails = new ArrayList<>();
    public final List<String> disables = new ArrayList<>();
    public final List<String> aborts = new ArrayList<>();

    /**
     * @param type     -- the type of the action
     * @param passes   -- list of class.test strings for passing tests
     * @param fails    -- list of class.test strings for failing tests
     * @param disables -- list of class.test strings for disabled tests
     * @param aborts   -- list of class.test strings aborted tests
     */
    public ActionInfo(String type,
                      List<String> passes,
                      List<String> fails,
                      List<String> disables,
                      List<String> aborts
    ) {
        this.type = type;
        this.passes.addAll(passes);
        this.fails.addAll(fails);
        this.disables.addAll(disables);
        this.aborts.addAll(aborts);
    }

    /**
     * Primary constructor for simple actions w/no subdata other than type.
     *
     * @param type -- string identifying what type of action this is
     */
    ActionInfo(String type) {
        this(type, emptyList(), emptyList(), emptyList(), emptyList());
    }


    @Override
    public void putTo(YamlMap map) {
        map.put("type", type);
        if (type == "test") {
            map.put("passes", passes);
            map.put("fails", fails);
            map.put("disables", disables);
            map.put("aborts", aborts);
        }
    }
}
