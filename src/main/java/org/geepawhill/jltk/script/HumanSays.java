package org.geepawhill.jltk.script;

public class HumanSays implements ScriptAction {

    final String whatToSay;
    final String filename;
    final int lineNumber;
    int current = 0;

    HumanSays(String whatToSay, String filename, int lineNumber) {
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
        throw new ScriptUnexpectedRead(filename, lineNumber);
    }

    @Override
    public boolean isFinished() {
        return current > whatToSay.length() + 1;
    }
}
