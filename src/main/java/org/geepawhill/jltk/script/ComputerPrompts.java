package org.geepawhill.jltk.script;

public class ComputerPrompts implements ScriptAction {

    final String whatToExpect;
    final String filename;
    final int lineNumber;
    String accumulator = "";
    boolean sawAccumulator = false;

    ComputerPrompts(String whatToExpect, String filename, int lineNumber) {
        this.whatToExpect = whatToExpect;
        this.filename = filename;
        this.lineNumber = lineNumber;
    }

    @Override
    public int read() {
        throw new ScriptException(filename, lineNumber, "Target read when Script was expecting a write.");
    }

    @Override
    public void write(int value) {
        accumulator = accumulator + (char) value;
        if (!whatToExpect.startsWith(accumulator)) {
            throw new ScriptException(filename, lineNumber, "Script expected [" + whatToExpect + "] but Target wrote [" + accumulator + "]");
        }
        if (whatToExpect.equals(accumulator)) sawAccumulator = true;
    }

    @Override
    public boolean isFinished() {
        return sawAccumulator;
    }
}
