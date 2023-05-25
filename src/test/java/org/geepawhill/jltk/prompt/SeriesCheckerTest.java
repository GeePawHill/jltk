package org.geepawhill.jltk.prompt;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SeriesCheckerTest {

    ArrayList<Reply> replies = new ArrayList<>();

    @Test
    void successfulCommaSeparatedInts() {
        Checker checker = new SeriesChecker(",", new IntegerChecker(), new IntegerChecker());

        assertEquals(true, checker.isSatisfied("1,2", replies));
        assertEquals(1, replies.get(0).asInteger());
        assertEquals(2, replies.get(1).asInteger());
    }

    @Test
    void failedNotEnoughItems() {
        Checker checker = new SeriesChecker(",", new IntegerChecker(), new IntegerChecker());
        assertEquals(false, checker.isSatisfied("1,", replies));
    }

    @Test
    void failedWrongType() {
        Checker checker = new SeriesChecker(",", new IntegerChecker(), new IntegerChecker());
        assertEquals(false, checker.isSatisfied("1,abcd", replies));
    }

    @Test
    void failedTooLong() {
        // Note: Not literally too long, rather "1" and "2,3" aren't both integers.
        Checker checker = new SeriesChecker(",", new IntegerChecker(), new IntegerChecker());
        assertEquals(false, checker.isSatisfied("1,2,3", replies));
    }
}
