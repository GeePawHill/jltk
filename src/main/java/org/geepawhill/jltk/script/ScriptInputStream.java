package org.geepawhill.jltk.script;

import java.io.*;

public class ScriptInputStream extends InputStream {

    private final Script script;

    public ScriptInputStream(Script script) {
        this.script = script;
    }

    @Override
    public int read() throws IOException {
        return script.read();
    }
}
