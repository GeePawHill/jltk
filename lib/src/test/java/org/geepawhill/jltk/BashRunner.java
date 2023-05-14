package org.geepawhill.jltk;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class BashRunner {
    public static final String GIT_BASH_PATH = "C:/Program Files/Git/bin/bash.exe";
    public static final String LINUX_BASH_PATH = "/usr/bin/bash";

    private String[] command;
    public String io = null;
    public ArrayList<String> lines = new ArrayList<>();

    public BashRunner(String... command) {
        this.command = makeFullCommand(command);
    }

    public int execute(Path working) {
        try {
            return unsafeExecute(working);
        } catch (IOException | InterruptedException e) {
            return restoreInterruptedState();
        }
    }

    private int restoreInterruptedState() {
        Thread.currentThread().interrupt();
        return -1;
    }

    private int unsafeExecute(Path working) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(command);
        processBuilder.redirectErrorStream(true);
        processBuilder.directory(working.toFile());

        Process process = processBuilder.start();
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line + "\n");
            lines.add(line);
        }
        io = output.toString();
        return process.waitFor();
    }


    public static String[] makeFullCommand(String... command) {
        String[] full = new String[command.length + 2];
        full[0] = bashPath();
        System.arraycopy(command, 0, full, 1, command.length);
        full[command.length + 1] = scriptPath();
        return full;
    }

    private static String bashPath() {
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) return GIT_BASH_PATH;
        return LINUX_BASH_PATH;
    }

    private static String scriptPath() {
        return "./";
    }

}
