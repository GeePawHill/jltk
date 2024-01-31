package za.co.wethinkcode.script;

public class ComputerPrompts implements ScriptAction {

    private final ScriptLocation location;
    final String whatToExpect;
    String accumulator = "";
    boolean sawAccumulator = false;

    public ComputerPrompts(String whatToExpect) {
        this.whatToExpect = whatToExpect;
        this.location = new ScriptLocation();
    }

    @Override
    public int read() {
        throw new ScriptUnexpectedRead(location);
    }

    @Override
    public void write(int value) {
        accumulator = accumulator + (char) value;
        if (!whatToExpect.startsWith(accumulator)) {
            throw new ScriptException(location, "Script expected [" + whatToExpect + "] but Target wrote [" + accumulator + "]");
        }
        if (whatToExpect.equals(accumulator)) sawAccumulator = true;
    }

    @Override
    public boolean isFinished() {
        return sawAccumulator;
    }
}
