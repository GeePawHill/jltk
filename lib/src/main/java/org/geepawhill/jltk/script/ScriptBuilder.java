package org.geepawhill.jltk.script;

import java.io.*;
import java.util.*;

public class ScriptBuilder {
    private Script script = new Script();

    private InputStream originalIn = System.in;
    private PrintStream originalOut = System.out;

    public ScriptBuilder say(String line) {
        StackTraceElement caller = new Throwable().getStackTrace()[1];
        script.add(new SayAction(line, caller.getFileName(), caller.getLineNumber()));
        return this;
    }

    public ScriptBuilder expectln(String line) {
        StackTraceElement caller = new Throwable().getStackTrace()[1];
        script.add(new ExpectLineAction(line, caller.getFileName(), caller.getLineNumber()));
        return this;
    }

    public void validate(Runnable function) {
        try {
            System.setIn(new ScriptInputStream(script));
            System.setOut(new PrintStream(new ScriptOutputStream(script)));
            function.run();
        } catch (NoSuchElementException possibleScannerException) {
            possibleScannerUnderflow(possibleScannerException);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }

    private static void possibleScannerUnderflow(NoSuchElementException possibleScannerException) {
        if ("No line found".equals(possibleScannerException.getMessage())) {
            throw new ScriptUnderflowException("Script underflow detected. Function wanted more input than script.");
        } else throw possibleScannerException;
    }
}
