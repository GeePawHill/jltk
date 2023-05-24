package org.geepawhill.jltk.script;

import java.io.*;
import java.util.*;

public class ConsoleTester {
    private Script script = new Script();

    private InputStream originalIn = System.in;
    private PrintStream originalOut = System.out;

    public ConsoleTester() {
        System.setIn(new ScriptInputStream(script));
        System.setOut(new PrintStream(new ScriptOutputStream(script)));
    }

    public ConsoleTester humanSays(String line) {
        StackTraceElement caller = new Throwable().getStackTrace()[1];
        script.add(new HumanSays(line));
        return this;
    }

    public ConsoleTester computerPrompts(String line) {
        StackTraceElement caller = new Throwable().getStackTrace()[1];
        script.add(new ComputerPrompts(line, caller.getFileName(), caller.getLineNumber()));
        return this;
    }

    public ConsoleTester computerChatters(int count) {
        StackTraceElement caller = new Throwable().getStackTrace()[1];
        script.add(new ComputerChatters(count, caller.getFileName(), caller.getLineNumber()));
        return this;
    }

    public ConsoleTester computerSays(String line) {
        StackTraceElement caller = new Throwable().getStackTrace()[1];
        script.add(new ComputerSays(line, caller.getFileName(), caller.getLineNumber()));
        return this;
    }

    public void run(Runnable function) {
        try {
            function.run();
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }

    private static void possibleScannerUnderflow(NoSuchElementException possibleScannerException) {
        if ("No line found".equals(possibleScannerException.getMessage())) {
            throw new ScriptUnderflow("Script underflow detected. Target still running, but Script was finished.");
        } else throw possibleScannerException;
    }
}
