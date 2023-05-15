package org.geepawhill.jltk;

import org.junit.jupiter.api.*;
import org.opentest4j.*;

import java.util.*;

public class ScriptTest {
    public static void readTwoStrings() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.nextLine();
    }

    public static void helloWorld() {
        System.out.println("I'm static.");
    }

    @Test
    void successfulSayOnly() {
        new ScriptBuilder()
                .say("first")
                .say("second")
                .validate(ScriptTest::readTwoStrings);
    }

    @Test
    void underflowSayOnly() {
        Assertions.assertThrows(AssertionFailedError.class,
                () -> {
                    new ScriptBuilder()
                            .say("first")
                            .validate(ScriptTest::readTwoStrings);
                }
        );
    }
}
