package org.geepawhill.jltk.script;

import java.io.*;
import java.util.*;

public class ScriptBuilder {
    private Script script = new Script();

    private InputStream originalIn = System.in;
    private PrintStream originalOut = System.out;

    public ScriptBuilder() {
        System.setIn(new ScriptInputStream(script));
        System.setOut(new PrintStream(new ScriptOutputStream(script)));
    }

    public ScriptBuilder sayln(String line) {
        StackTraceElement caller = new Throwable().getStackTrace()[1];
        script.add(new SayLineAction(line, caller.getFileName(), caller.getLineNumber()));
        return this;
    }

    public ScriptBuilder expect(String line) {
        StackTraceElement caller = new Throwable().getStackTrace()[1];
        script.add(new ExpectAction(line, caller.getFileName(), caller.getLineNumber()));
        return this;
    }

    public ScriptBuilder ignoreln(int count) {
        StackTraceElement caller = new Throwable().getStackTrace()[1];
        script.add(new IgnoreLineAction(count, caller.getFileName(), caller.getLineNumber()));
        return this;
    }

    public ScriptBuilder expectln(String line) {
        StackTraceElement caller = new Throwable().getStackTrace()[1];
        script.add(new ExpectLineAction(line, caller.getFileName(), caller.getLineNumber()));
        return this;
    }

    public void validate(Runnable function) {
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
