package org.geepawhill.jltk;

import java.io.*;
import java.util.*;

public class ScriptBuilder {
    private Script script = new Script();

    private InputStream originalIn = System.in;
    private PrintStream originalOut = System.out;

    ScriptBuilder say(String line) {
        StackTraceElement caller = new Throwable().getStackTrace()[1];
        script.add(new SayAction(line, caller.getFileName(), caller.getLineNumber()));
        return this;
    }

    ScriptBuilder expect(String line) {
        StackTraceElement caller = new Throwable().getStackTrace()[1];
        script.add(new ExpectAction(line, caller.getFileName(), caller.getLineNumber()));
        return this;
    }

    void validate(Runnable function) {
        try {
            System.setIn(new TestableInputStream(script));
            System.setOut(new PrintStream(new TestableOutputStream(script)));
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
