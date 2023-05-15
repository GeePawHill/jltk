package org.geepawhill.jltk;

public class ScriptException extends RuntimeException {

    ScriptException(String filename, int lineNumber, String message) {
        super(message);
    }
}
