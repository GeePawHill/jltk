package za.co.wethinkcode.prompt;

import java.util.*;

public class NonEmptyChecker implements Checker {
    @Override
    public boolean isSatisfied(String candidate, ArrayList<Reply> replies) {
        if (candidate.isBlank()) return false;
        replies.add(new Reply(candidate));
        return true;
    }
}
