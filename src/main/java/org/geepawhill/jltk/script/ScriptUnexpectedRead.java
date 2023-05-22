package org.geepawhill.jltk.script;

public class ScriptUnexpectedRead extends ScriptException {
    public ScriptUnexpectedRead(String filename, int linenumber) {
        super(filename, linenumber, "Target read when Script was expecting a write.");
    }
}
