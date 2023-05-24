package org.geepawhill.jltk.script;

public class ComputerChatters implements ScriptAction {
    private int count;
    private final String filename;
    private final int lineNumber;

    public ComputerChatters(int count, String filename, int lineNumber) {
        this.count = count;
        this.filename = filename;
        this.lineNumber = lineNumber;
    }

    @Override
    public int read() {
        throw new ScriptUnexpectedRead(filename, lineNumber);
    }

    @Override
    public void write(int value) {
        System.err.println((char) value);
        if (value == '\n') count -= 1;
    }

    @Override
    public boolean isFinished() {
        return count == 0;
    }

}
