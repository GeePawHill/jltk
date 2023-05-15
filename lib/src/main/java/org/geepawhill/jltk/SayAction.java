package org.geepawhill.jltk;

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
        return '\n';
    }

    @Override
    public void write(int value) {
        throw new RuntimeException("Wrote when you thought we were reading.");
    }

    @Override
    public boolean isFinished() {
        return current > whatToSay.length();
    }

    @Override
    public void dump() {
        System.err.println("Said: " + whatToSay);
    }
}
