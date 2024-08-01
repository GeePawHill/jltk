package za.co.wethinkcode.prompt;

import java.util.*;

public class FloatChecker implements Checker {
    @Override
    public boolean isSatisfied(String candidate, ArrayList<Reply> replies) {
        try {
            Float.parseFloat(candidate);
        } catch (Exception unused) {
            return false;
        }
        replies.add(new Reply(candidate));
        return true;
    }
}
