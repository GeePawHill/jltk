package org.geepawhill.jltk.prompt;

import java.util.*;

public class StringChecker implements Checker {
    @Override
    public boolean isSatisfied(String candidate, ArrayList<Reply> replies) {
        replies.add(new Reply(candidate));
        return true;
    }
}
