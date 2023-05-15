package org.geepawhill.jltk;

public interface ScriptAction {
    int read();

    void write(int value);

    boolean isFinished();

    void dump();
}
