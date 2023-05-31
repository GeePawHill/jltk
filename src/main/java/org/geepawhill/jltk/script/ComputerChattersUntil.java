package org.geepawhill.jltk.script;

public class ComputerChattersUntil implements ScriptAction {
    private final ScriptLocation location;
    private final String whatToExpect;
    private final int maxLines;
    private int linesLeft;
    private String accumulator = "";
    private boolean sawTarget = false;

    public ComputerChattersUntil(String whatToExpect, int maxLines) {
        this.maxLines = maxLines;
        this.whatToExpect = whatToExpect;
        this.linesLeft = maxLines;
        this.location = new ScriptLocation();
    }

    @Override
    public int read() {
        throw new ScriptUnexpectedRead(location);
    }

    @Override
    public void write(int value) {
        if (value == '\n') {
            if (accumulator.equals(whatToExpect)) {
                sawTarget = true;
            } else accumulator = "";
            linesLeft -= 1;
            if (linesLeft < 0) {
                throw new ScriptException(location, "Computer chattered too many lines (" + maxLines + ".");
            }
        } else {
            accumulator += (char) value;
        }
    }

    @Override
    public boolean isFinished() {
        return sawTarget;
    }
}
