package org.geepawhill.jltk;

import org.junit.jupiter.api.*;

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

    void validate(Runnable function) {
        try {
            System.setIn(new TestableInputStream(script));
            System.setOut(new PrintStream(new TestableOutputStream(script)));
            function.run();
        } catch (NoSuchElementException possibleScannerException) {
            if ("No line found".equals(possibleScannerException.getMessage())) {
                Assertions.fail("Script underflow detected. Function wanted more input.");
            } else throw possibleScannerException;
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}
