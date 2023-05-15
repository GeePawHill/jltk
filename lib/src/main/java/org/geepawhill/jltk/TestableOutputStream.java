package org.geepawhill.jltk;

import java.io.*;

public class TestableOutputStream extends OutputStream {

    private final Script script;

    TestableOutputStream(Script script) {
        this.script = script;
    }

    @Override
    public void write(int value) throws IOException {
        script.write(value);
    }
}
