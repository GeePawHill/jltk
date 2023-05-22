package org.geepawhill.jltk.script;

public interface ScriptAction {
    int read();

    void write(int value);

    boolean isFinished();
}
