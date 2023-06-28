package org.geepawhill.jltk.flow;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

import static org.geepawhill.jltk.flow.TestAppender.*;

public class Recorder {
    private final GitInfo gitInfo;
    private final Path logPath;
    private final Path home;

    public Recorder(GitInfo gitInfo, Path home) {
        this.gitInfo = gitInfo;
        this.logPath = gitInfo.computeLogPathFor(home);
        this.home = home;
    }

    public Recorder() {
        this(new GitInfo(), computeUserHome());
    }

    public void logRun() {
        writeToLog(gitInfo, new TimestampAppender(), new RunAppender());
    }

    public void logTest(List<String> passes, List<String> fails, List<String> disables, List<String> aborts) {
        writeToLog(gitInfo,
                new TimestampAppender(),
                new TestAppender("test", passes, fails, disables, aborts));
    }

    public void logPostCommit() {
        writeToLog(gitInfo, new TimestampAppender(), new CommitAppender());
        String repoLogname = computeRepoLogname();
        Path destination = gitInfo.root.resolve(JLTK_FOLDER).resolve(repoLogname);
        try {
            Files.move(logPath, destination);
        } catch (Exception output) {
            output.printStackTrace(System.err);
        }
    }

    private String computeRepoLogname() {
        DateTimeFormatter filetimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddkkmmss");
        String filestamp = LocalDateTime.now().format(filetimeFormatter);
        String shortEmail = gitInfo.email.split("@")[0];
        String leafName = gitInfo.branch
                + "_" + shortEmail
                + "_" + filestamp +
                ".jltk";
        return leafName;
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
