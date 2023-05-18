package org.geepawhill.jltk;

import java.io.*;

public class ExpectAction implements ScriptAction {

    final String whatToExpect;
    final String filename;
    final int lineNumber;
    String accumulator = "";
    boolean sawNewLine = false;


    ExpectAction(String whatToExpect, String filename, int lineNumber) {
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
        if (value == '\n') {
            if (!whatToExpect.equals(accumulator.trim())) {
                throw new ScriptException(filename, lineNumber, "Mismatched Expect. Wanted [" + whatToExpect + "] but got [" + accumulator.trim() + "]");
            }
            sawNewLine = true;
        } else {
            accumulator = accumulator + (char) value;
        }
    }

    @Override
    public boolean isFinished() {
        return sawNewLine;
    }

    @Override
    public void dump(PrintStream destination) {
        destination.println("Heard: [" + whatToExpect + "]");
    }
}