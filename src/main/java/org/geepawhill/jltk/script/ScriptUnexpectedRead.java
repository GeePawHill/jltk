package org.geepawhill.jltk.script;

public class ScriptUnexpectedRead extends ScriptException {
    public ScriptUnexpectedRead(ScriptLocation location) {
        super(location, "Computer waiting for human when Script expected it to talk.");
    }
}
