package za.co.wethinkcode.script;

public class ScriptUnexpectedWrite extends ScriptException {
    public ScriptUnexpectedWrite(ScriptLocation location) {
        super(location, "Computer said when Script expected Human to say.");
    }
}
