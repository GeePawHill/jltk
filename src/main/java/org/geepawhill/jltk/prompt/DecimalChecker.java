package org.geepawhill.jltk.prompt;

import java.math.*;
import java.util.*;

public class DecimalChecker implements Checker {
    @Override
    public boolean isSatisfied(String candidate, ArrayList<Reply> replies) {
        try {
            new BigDecimal(candidate);
        } catch (Exception unused) {
            return false;
        }
        replies.add(new Reply(candidate));
        return true;
    }
}
