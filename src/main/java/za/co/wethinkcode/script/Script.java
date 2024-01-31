package za.co.wethinkcode.script;

import java.util.*;

public class Script {
    ArrayList<ScriptAction> actions = new ArrayList<>();
    int current = 0;

    public void add(ScriptAction action) {
        actions.add(action);
    }

    int read() {
        throwOnUnderflow();
        ScriptAction action = actions.get(current);
        int result = action.read();
        if (action.isFinished()) current += 1;
        return result;
    }

    void write(int value) {
        throwOnUnderflow();
        ScriptAction action = actions.get(current);
        action.write(value);
        if (action.isFinished()) current += 1;
    }

    private void throwOnUnderflow() {
        if (current == actions.size()) throw new ScriptUnderflow("Script is too short.");
    }
}
