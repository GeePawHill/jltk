package org.geepawhill.jltk;

import java.io.*;

public class TestableInputStream extends InputStream {

    private final Script script;

    TestableInputStream(Script script) {
        this.script = script;
    }

    @Override
    public int read() throws IOException {
        return script.read();
    }
}
