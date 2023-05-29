package org.geepawhill.jltk.prompt;

import java.util.*;

/**
 * An interface to check strings and, if suitable, add their contents to a
 * list of replies.
 */
@FunctionalInterface
public interface Checker {
    /**
     * Returns true if the candidate String satisfies, else false.
     * If it *does* return true, it adds a Reply entry for each candidate.
     *
     * @param candidate A string to be tested.
     * @param replies   The destination for any added Reply objedts.
     * @return boolean, whether or not this Checker is satisfied with this candidate.
     */
    boolean isSatisfied(String candidate, ArrayList<Reply> replies);
}
