package org.geepawhill.jltk.script;

public class ScriptUnexpectedWrite extends ScriptException {
    public ScriptUnexpectedWrite(ScriptLocation location) {
        super(location.fileName, location.lineNumber, "Computer said when Script expected Human to say.");
    }
}
