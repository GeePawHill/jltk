package org.geepawhill.jltk.script;

public class HumanSays implements ScriptAction {

    final String whatToSay;
    final ScriptLocation location;
    int current = 0;

    public HumanSays(String whatToSay) {
        this.location = new ScriptLocation();
        this.whatToSay = whatToSay;
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
        throw new ScriptUnexpectedWrite(location);
    }

    @Override
    public boolean isFinished() {
        return current > whatToSay.length() + 1;
    }
}
