package org.geepawhill.jltk.script;

/**
 * A script action to cover the situation where we know the computer is
 * going to say things, but we don't care *what* it says, just how many lines.
 */
public class ComputerChatters implements ScriptAction {
    private int count;
    private final ScriptLocation location;

    /**
     * Constructor specifying the number of lines we should ignore.
     *
     * @param lines -- int, how many lines to let the computer say
     */
    public ComputerChatters(int lines) {
        this.count = lines;
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
