package org.geepawhill.jltk.flow;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Recorder {
    private final GitInfo gitInfo;
    private final Path logPath;

    public Recorder(GitInfo gitInfo, Path home) {
        this.gitInfo = gitInfo;
        this.logPath = gitInfo.computeLogPathFor(home);
    }

    public Recorder() {
        this(new GitInfo(), computeUserHome());
    }

    public void run() {
        TimestampAppender timestamp = new TimestampAppender();
        ActionInfo action = new ActionInfo("run");
        writeToLog(gitInfo, timestamp, action);
    }

    public void writeToLog(MapAppender... appenders) {
        try {
            YamlMap map = new YamlMap();
            map.append(appenders);
            String yaml = map.asString();
            String encoded = Base64.getEncoder().encodeToString(yaml.getBytes());
            appendToLogFile(encoded);
        } catch (Exception e) {
            System.err.println("Error: Could not record run!");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void tests(List<String> passes, List<String> fails, List<String> disables, List<String> aborts) {
        TimestampAppender timestamp = new TimestampAppender();
        ActionInfo action = new ActionInfo("test", passes, fails, disables, aborts);
        writeToLog(gitInfo, timestamp, action);
    }

    private void appendToLogFile(String yaml) throws IOException {
        PrintWriter log = new PrintWriter(
                Files.newBufferedWriter(
                        logPath,
                        StandardOpenOption.WRITE,
                        StandardOpenOption.APPEND,
                        StandardOpenOption.CREATE)
        );
        log.println(yaml);
        log.flush();
        log.close();
    }

    /**
     * @return the Path object representing the user's home folder.
     */
    static Path computeUserHome() {
        return Path.of(System.getProperty("user.home"));
    }
}
