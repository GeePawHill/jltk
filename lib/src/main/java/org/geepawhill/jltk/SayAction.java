package org.geepawhill.jltk;

import java.io.*;

public class SayAction implements ScriptAction {

    final String whatToSay;
    final String filename;
    final int lineNumber;
    int current = 0;

    SayAction(String whatToSay, String filename, int lineNumber) {
        this.whatToSay = whatToSay;
        this.filename = filename;
        this.lineNumber = lineNumber;
    }

    @Override
    public int read() {
        int result = nextChar();
        current += 1;
        return result;
    }

    private int nextChar() {
        if (current < whatToSay.length()) return whatToSay.charAt(current);
        if (current == whatToSay.length()) return '\n';
        return -1;
    }

    @Override
    public void write(int value) {
        throw new RuntimeException("Wrote when you thought we were reading.");
    }

    @Override
    public boolean isFinished() {
        return current > whatToSay.length() + 1;
    }

    @Override
    public void dump(PrintStream destination) {
        destination.println("Said:  [" + whatToSay + "]");
    }
}
