package org.geepawhill.jltk.prompt;

import java.util.*;

public class SeriesChecker implements Checker {

    private final String splitter;
    private final Checker[] checkers;

    public SeriesChecker(String splitter, Checker... checkers) {
        this.splitter = splitter;
        this.checkers = checkers;
    }

    @Override
    public boolean isSatisfied(String candidate, ArrayList<Reply> replies) {
        String[] tokens = candidate.split(splitter, checkers.length);
        if (tokens.length < checkers.length) return false;
        for (int index = 0; index < checkers.length; index++) {
            Checker checker = checkers[index];
            String item = tokens[index];
            if (!checker.isSatisfied(item, replies)) return false;
        }
        return true;
    }
}
