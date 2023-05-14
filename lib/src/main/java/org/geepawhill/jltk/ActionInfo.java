package org.geepawhill.jltk;

import org.yaml.snakeyaml.*;

import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

import static java.util.Collections.*;

public class ActionInfo {
    public static final String WTC_FOLDER = ".wtc";
    public static final String WTC_KEYFILE = "wtc.key";

    public Path root;
    public Path home;
    public String type;
    public String email;
    public String committer;
    public String branch;
    public String timestamp;
    public String filetime;
    public List<String> passes = new ArrayList<>();
    public List<String> fails = new ArrayList<>();
    public List<String> disables = new ArrayList<>();
    public List<String> aborts = new ArrayList<>();

    public ActionInfo(GitInfo git,
                      String type,
                      String timestamp,
                      String filetime,
                      List<String> passes,
                      List<String> fails,
                      List<String> disables,
                      List<String> aborts
    ) {
        this.home = git.home;
        this.root = git.root;
        this.branch = git.branch;
        this.committer = git.committer;
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

    ActionInfo(GitInfo gitInfo, String type) {
        this(gitInfo, type, LocalDateTime.now(), emptyList(), emptyList(), emptyList(), emptyList());
    }

    ActionInfo(GitInfo gitInfo, List<String> passes, List<String> fails, List<String> disables, List<String> aborts) {
        this(gitInfo, "test", LocalDateTime.now(), passes, fails, disables, aborts);
    }

    Path homeWtc() {
        return home.resolve(WTC_FOLDER);
    }

    Path rootWtc() {
        return root.resolve(WTC_FOLDER);
    }

    static String fileTimeFrom(LocalDateTime time) {
        return time.format(filetimeFormatter);
    }

    static String timestampFrom(LocalDateTime time) {
        return time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).split("\\.")[0];
    }

    static DateTimeFormatter filetimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddkkmmss");

    public String toYaml() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("branch", branch);
        map.put("committer", committer);
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
