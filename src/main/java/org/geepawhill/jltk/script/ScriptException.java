package org.geepawhill.jltk.script;

public class ScriptException extends RuntimeException {

    ScriptException(String filename, int lineNumber, String message) {
        super("(" + filename + ":" + lineNumber + ") " + message);
    }
}
