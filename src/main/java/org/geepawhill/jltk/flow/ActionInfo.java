package org.geepawhill.jltk.flow;

import java.time.*;
import java.time.format.*;
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
     * @param type      -- the type of the action
     * @param timestamp -- the timestamp as string
     * @param filetime  -- the filetime, that is, the corr
     * @param passes    -- list of class.test strings for passing tests
     * @param fails     -- list of class.test strings for failing tests
     * @param disables  -- list of class.test strings for disabled tests
     * @param aborts    -- list of class.test strings aborted tests
     */
    public ActionInfo(String type,
                      String timestamp,
                      String filetime,
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

    ActionInfo(GitInfo gitInfo, String type, LocalDateTime timestamp, List<String> passes, List<String> fails, List<String> disables, List<String> aborts) {
        this(type, timestampFrom(timestamp), fileTimeFrom(timestamp), passes, fails, disables, aborts);
    }

    /**
     * Primary constructor for simple actions w/no subdata other than type.
     *
     * @param gitInfo -- describe gitinfo for this object
     * @param type    -- string identifying what type of action this is
     */
    ActionInfo(GitInfo gitInfo, String type) {
        this(gitInfo, type, LocalDateTime.now(), emptyList(), emptyList(), emptyList(), emptyList());
    }

    /**
     * Primary constructor for test actions.
     *
     * @param gitInfo  -- git info for action
     * @param passes   -- tests that passed
     * @param fails    -- tests that failed
     * @param disables -- tests that were disabled
     * @param aborts   -- tests that were aborted
     */

    ActionInfo(GitInfo gitInfo, List<String> passes, List<String> fails, List<String> disables, List<String> aborts) {
        this(gitInfo, "test", LocalDateTime.now(), passes, fails, disables, aborts);
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

    /**
     * Convert a LocalDateTime into a timestamp w/o fractional seconds, i.e. YYYY-MM-DD-HH-MM-SS
     *
     * @param time
     * @return
     */
    static String timestampFrom(LocalDateTime time) {
        return time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).split("\\.")[0];
    }

    static DateTimeFormatter filetimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddkkmmss");

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
