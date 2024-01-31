package za.co.wethinkcode.script;

public class ComputerSays implements ScriptAction {

    final String whatToExpect;
    private final ScriptLocation location;
    String accumulator = "";
    boolean sawAccumulator = false;

    ComputerSays(String whatToExpect) {
        this.whatToExpect = whatToExpect;
        this.location = new ScriptLocation();
    }

    @Override
    public int read() {
        throw new ScriptUnexpectedRead(location);
    }

    @Override
    public void write(int value) {
        if (value == '\r') return;
        if (value == '\n') {
            if (!whatToExpect.equals(accumulator)) {
                throw new ScriptException(location, "Mismatched Output. Script Wanted [" + whatToExpect + "] Computer said [" + accumulator + "]");
            }
            sawAccumulator = true;
        } else {
            accumulator = accumulator + (char) value;
        }
    }

    @Override
    public boolean isFinished() {
        return sawAccumulator;
    }
}
