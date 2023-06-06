package org.geepawhill.jltk.prompt;

import java.util.*;

public class DoubleChecker implements Checker {
    @Override
    public boolean isSatisfied(String candidate, ArrayList<Reply> replies) {
        try {
            Double.parseDouble(candidate);
        } catch (Exception unused) {
            return false;
        }
        replies.add(new Reply(candidate));
        return true;
    }
}
