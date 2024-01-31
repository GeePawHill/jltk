package za.co.wethinkcode.script;

public interface ScriptAction {
    int read();

    void write(int value);

    boolean isFinished();
}
