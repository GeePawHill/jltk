package org.geepawhill.jltk;

import java.util.*;

public class Script {
    ArrayList<SayAction> says = new ArrayList<>();
    int currentSay = 0;

    ArrayList<ExpectAction> expects = new ArrayList<>();
    int currentWrite = 0;

    void add(ExpectAction action) {
        expects.add(action);
    }

    void add(SayAction action) {
        says.add(action);
    }

    int read() {
        if (currentSay == says.size()) return -1;
        ScriptAction action = says.get(currentSay);
        int result = action.read();
        if (action.isFinished()) currentSay += 1;
        return result;
    }

    void write(int value) {
        if (currentWrite == expects.size())
            throw new ScriptUnderflowException("Function wrote more than was expected in script.");
        ScriptAction action = expects.get(currentWrite);
        action.write(value);
        if (action.isFinished()) currentWrite += 1;
    }
}
