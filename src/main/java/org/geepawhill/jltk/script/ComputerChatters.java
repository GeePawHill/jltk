package org.geepawhill.jltk.script;

public class ComputerChatters implements ScriptAction {
    private int count;
    private final ScriptLocation location;

    public ComputerChatters(int count, String filename, int lineNumber) {
        this.count = count;
        this.location = new ScriptLocation();
    }

    @Override
    public int read() {
        throw new ScriptUnexpectedRead(location);
    }

    @Override
    public void write(int value) {
        if (value == '\n') count -= 1;
    }

    @Override
    public boolean isFinished() {
        return count == 0;
    }
}
