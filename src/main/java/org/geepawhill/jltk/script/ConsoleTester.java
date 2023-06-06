package org.geepawhill.jltk.script;

import java.io.*;
import java.util.*;

/**
 * ConsoleTester is a class for writing test scripts that work on
 */

public class ConsoleTester {
    private Script script = new Script();

    private InputStream originalIn = System.in;
    private PrintStream originalOut = System.out;

    public ConsoleTester() {
        System.setIn(new ScriptInputStream(script));
        System.setOut(new PrintStream(new ScriptOutputStream(script)));
    }

    public ConsoleTester humanSays(String line) {
        script.add(new HumanSays(line));
        return this;
    }

    public ConsoleTester computerPrompts(String line) {
        script.add(new ComputerPrompts(line));
        return this;
    }

    public ConsoleTester computerChatters(int count) {
        script.add(new ComputerChatters(count));
        return this;
    }

    public ConsoleTester computerChattersUntil(String whatToExpect, int maxLines) {
        script.add(new ComputerChattersUntil(whatToExpect, maxLines));
        return this;
    }

    public ConsoleTester computerSays(String line) {
        script.add(new ComputerSays(line));
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
