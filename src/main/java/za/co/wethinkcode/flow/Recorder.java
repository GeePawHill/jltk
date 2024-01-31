package za.co.wethinkcode.flow;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

import static za.co.wethinkcode.flow.FileHelpers.*;

public class Recorder {
    private final GitInfo gitInfo;
    private final Path logPath;

    public Recorder(GitInfo gitInfo) {
        this.gitInfo = gitInfo;
        this.logPath = gitInfo.computeTemporaryPath();
    }

    public Recorder() {
        this(new GitInfo());
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
        String repoLogname = makeFinalLogName();
        Path destination = gitInfo.root.resolve(FileHelpers.JLTK_FOLDER).resolve(repoLogname);
        try {
            Files.move(logPath, destination);
        } catch (Exception output) {
            output.printStackTrace(System.err);
        }
    }

    private String makeFinalLogName() {
        DateTimeFormatter filetimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddkkmmss");
        String filestamp = LocalDateTime.now().format(filetimeFormatter);
        String shortEmail = gitInfo.email.split("@")[0];
        String leafName = gitInfo.branch
                + "_" + shortEmail
                + "_" + filestamp +
                JLTK_LOG_SUFFIX;
        return leafName;
    }

    public void writeToLog(MapAppender... appenders) {
        try {
            forceJltkFolder();
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

    private void forceJltkFolder() throws IOException {
        Files.createDirectories(gitInfo.root.resolve(FileHelpers.JLTK_FOLDER));
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
}
