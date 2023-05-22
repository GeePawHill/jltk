package org.geepawhill.jltk.script;

import java.io.*;

public class ScriptOutputStream extends OutputStream {

    private final Script script;

    ScriptOutputStream(Script script) {
        this.script = script;
    }

    @Override
    public void write(int value) throws IOException {
        script.write(value);
    }
}
