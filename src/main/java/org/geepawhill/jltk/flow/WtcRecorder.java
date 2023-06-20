package org.geepawhill.jltk.flow;

import org.yaml.snakeyaml.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class WtcRecorder {
    private GitInfo gitInfo;

    public WtcRecorder(GitInfo gitInfo) {
        // change to force commit.
        this.gitInfo = gitInfo;
    }

    public WtcRecorder() {
        this(new GitInfo());
    }

    public void run() {
        try {
            ActionInfo action = new ActionInfo(gitInfo, "run");
            String yaml = makeRunYaml(action);
            String encoded = Base64.getEncoder().encodeToString(yaml.getBytes());
            appendToLogFile(action, encoded);
        } catch (Exception e) {
            System.err.println("Error: Could not record run!");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void tests(List<String> passes, List<String> fails, List<String> disables, List<String> aborts) {
        try {
            ActionInfo action = new ActionInfo(gitInfo, passes, fails, disables, aborts);
            String yaml = makeTestYaml(action, passes, fails, disables, aborts);
            String encoded = Base64.getEncoder().encodeToString(yaml.getBytes());
            appendToLogFile(action, encoded);
        } catch (Exception e) {
            System.out.println("Error: Could not record run!");
            e.printStackTrace();
            System.exit(-1);
        }
    }


    private void appendToLogFile(ActionInfo committer, String yaml) throws IOException {
        PrintWriter log = new PrintWriter(
                Files.newBufferedWriter(
                        computeLogPathFor(committer),
                        StandardOpenOption.WRITE,
                        StandardOpenOption.APPEND,
                        StandardOpenOption.CREATE)
        );
        log.println(yaml);
        log.flush();
        log.close();
    }

    private Path computeLogPathFor(ActionInfo committer) throws IOException {
        String key = findOrMakeKey(committer.root, committer.home);
        String shortEmail = committer.email.split("@")[0];
        String leafName = committer.branch
                + "_" + shortEmail
                + ".wtc";
        return committer.home.resolve(Path.of(ActionInfo.WTC_FOLDER, key, leafName));
    }

    String makeRunYaml(ActionInfo info) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("branch", info.branch);
        map.put("committer", info.committer);
        map.put("email", info.email);
        map.put("type", "run");
        map.put("timestamp", info.timestamp);
        return dumpMap(map);
    }

    private String dumpMap(LinkedHashMap<String, Object> map) {
        DumperOptions options = new DumperOptions();
        options.setExplicitStart(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        return yaml.dump(map);
    }


    private String makeTestYaml(ActionInfo info, List<String> passes, List<String> fails, List<String> disables, List<String> aborts) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("branch", info.branch);
        map.put("committer", info.committer);
        map.put("email", info.email);
        map.put("type", "test");
        map.put("timestamp", info.timestamp);
        map.put("passes", passes);
        map.put("fails", fails);
        map.put("disables", disables);
        map.put("aborts", aborts);
        return dumpMap(map);
    }

    public String findOrMakeKey(Path root, Path home) {
        WtcKeyManager manager = new WtcKeyManager(root, home);
        return manager.findOrMakeKey();
    }
}
