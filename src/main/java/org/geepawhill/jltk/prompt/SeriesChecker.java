package org.geepawhill.jltk.prompt;

import java.util.*;

/**
 * A Checker to run other checkers in sequence.
 * <p>The SeriesChecker takes a regex String and a list of Checkers.</p>
 * <p>
 * It splits a line of text into tokens, then runs the first checker on the
 * first token, the second on the second, and so on. If all the Checkers are satisfied,
 * this SeriesChecker is satisfied, otherwise not.</p>
 */
public class SeriesChecker implements Checker {

    private final String regex;
    private final Checker[] checkers;

    /**
     * Constructor to set the regex string and the list of checkers.
     *
     * @param regex    -- Same format as for {@link java.lang.String#split}
     * @param checkers -- One or more other checkers, to be evaluated in sequence
     */
    public SeriesChecker(String regex, Checker... checkers) {
        this.regex = regex;
        this.checkers = checkers;
    }

    @Override
    public boolean isSatisfied(String candidate, ArrayList<Reply> replies) {
        String[] tokens = candidate.split(regex, checkers.length);
        if (tokens.length < checkers.length) return false;
        for (int index = 0; index < checkers.length; index++) {
            Checker checker = checkers[index];
            String item = tokens[index];
            if (!checker.isSatisfied(item, replies)) return false;
        }
        return true;
    }
}
