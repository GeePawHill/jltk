package org.geepawhill.jltk.script;

import java.io.*;

public class ExpectLineAction implements ScriptAction {

    final String whatToExpect;
    final String filename;
    final int lineNumber;
    String accumulator = "";
    boolean sawAccumulator = false;

    ExpectLineAction(String whatToExpect, String filename, int lineNumber) {
        this.whatToExpect = whatToExpect;
        this.filename = filename;
        this.lineNumber = lineNumber;
    }

    @Override
    public int read() {
        throw new RuntimeException("Function read when script was expecting it to write.");
    }

    @Override
    public void write(int value) {
        if (value == '\r') return;
        if (value == '\n') {
            if (!whatToExpect.equals(accumulator)) {
                throw new ScriptException(filename, lineNumber, "Mismatched Expect. Wanted [" + whatToExpect + "] but got [" + accumulator + "]");
            }
            sawAccumulator = true;
        } else {
            accumulator = accumulator + (char) value;
        }
    }

    @Override
    public boolean isFinished() {
        return sawAccumulator;
    }

    @Override
    public void dump(PrintStream destination) {
        destination.println("Heard: [" + whatToExpect + "]");
    }
}
