package org.geepawhill.jltk.flow;

import org.yaml.snakeyaml.*;

import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

import static java.util.Collections.*;

public class ActionInfo {
    public static final String JLTK_FOLDER = ".jltk";
    public static final String JLTK_KEY = "jltk.key";

    public final Path root;
    public final String type;
    public final String email;
    public final String username;
    public final String branch;
    public final String timestamp;
    public final String filetime;
    public final List<String> passes = new ArrayList<>();
    public final List<String> fails = new ArrayList<>();
    public final List<String> disables = new ArrayList<>();
    public final List<String> aborts = new ArrayList<>();

    /**
     * @param git       -- the GitInfo used to initialize the class.
     * @param type      -- the type of the action
     * @param timestamp -- the timestamp as string
     * @param filetime  -- the filetime, that is, the corr
     * @param passes    -- list of class.test strings for passing tests
     * @param fails     -- list of class.test strings for failing tests
     * @param disables  -- list of class.test strings for disabled tests
     * @param aborts    -- list of class.test strings aborted tests
     */
    public ActionInfo(GitInfo git,
                      String type,
                      String timestamp,
                      String filetime,
                      List<String> passes,
                      List<String> fails,
                      List<String> disables,
                      List<String> aborts
    ) {
        this.root = git.root;
        this.branch = git.branch;
        this.username = git.username;
        this.email = git.email;
        this.type = type;
        this.timestamp = timestamp;
        this.filetime = filetime;
        this.passes.addAll(passes);
        this.fails.addAll(fails);
        this.disables.addAll(disables);
        this.aborts.addAll(aborts);
    }

    ActionInfo(GitInfo gitInfo, String type, LocalDateTime timestamp, List<String> passes, List<String> fails, List<String> disables, List<String> aborts) {
        this(gitInfo, type, timestampFrom(timestamp), fileTimeFrom(timestamp), passes, fails, disables, aborts);
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

    Path rootJltk() {
        return root.resolve(JLTK_FOLDER);
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

    /**
     * Convert your final data into a multi-line yaml string.
     *
     * @return resulting yaml string
     */
    public String toYaml() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("branch", branch);
        map.put("committer", username);
        map.put("email", email);
        map.put("type", type);
        map.put("timestamp", timestamp);
        if (type == "test") {
            map.put("passes", passes);
            map.put("fails", fails);
            map.put("disables", disables);
            map.put("aborts", aborts);
        }
        return dumpMap(map);
    }

    private String dumpMap(LinkedHashMap<String, Object> map) {
        DumperOptions options = new DumperOptions();
        options.setExplicitStart(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        return yaml.dump(map);
    }
}
