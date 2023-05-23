package org.geepawhill.jltk.prompt;

import java.util.*;

@FunctionalInterface
public interface Checker {
    boolean isSatisfied(String candidate, ArrayList<Reply> replies);
}
