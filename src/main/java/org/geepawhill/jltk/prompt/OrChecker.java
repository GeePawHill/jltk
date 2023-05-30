package org.geepawhill.jltk.prompt;

import java.util.*;

public class OrChecker implements Checker {
    private Checker[] checkers;

    public OrChecker(Checker... checkers) {
        this.checkers = checkers;
    }

    @Override
    public boolean isSatisfied(String candidate, ArrayList<Reply> replies) {
        if (checkers == null || checkers.length == 0) return true;
        boolean result = false;
        for (Checker checker : checkers) {
            if (checker.isSatisfied(candidate, replies)) return true;
        }
        return false;
    }
}
