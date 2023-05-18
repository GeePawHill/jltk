package org.geepawhill.jltk;

import java.util.*;

public class Script {
    ArrayList<ScriptAction> actions = new ArrayList<>();
    int current = 0;

    void add(ScriptAction action) {
        actions.add(action);
    }

    int read() {
        if (current == actions.size()) throw new ScriptUnderflowException("Not enough input.");
        ScriptAction action = actions.get(current);
        int result = action.read();
        if (action.isFinished()) current += 1;
        return result;
    }

    void write(int value) {
        if (current == actions.size())
            throw new ScriptUnderflowException("Function wrote more than was expected in script.");
        ScriptAction action = actions.get(current);
        action.write(value);
        if (action.isFinished()) current += 1;
    }
}
