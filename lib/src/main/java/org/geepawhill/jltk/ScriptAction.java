package org.geepawhill.jltk;

import java.io.*;

public interface ScriptAction {
    int read();

    void write(int value);

    boolean isFinished();

    void dump(PrintStream destination);
}
