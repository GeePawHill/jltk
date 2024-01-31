package za.co.wethinkcode.script;

public class ScriptException extends RuntimeException {
    ScriptException(ScriptLocation location, String message) {
        super("(" + location.fileName + ":" + location.lineNumber + ") " + message);
    }
}
