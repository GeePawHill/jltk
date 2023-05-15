package org.geepawhill.jltk;

import org.junit.jupiter.api.*;
import org.opentest4j.*;

import java.util.*;

public class ScriptBuilderTest {
    public static void readTwoStrings() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.nextLine();
    }

    public static void helloWorld() {
        System.out.println("Hello World!");
    }

    @Test
    void successfulSayOnly() {
        new ScriptBuilder()
                .say("first")
                .say("second")
                .validate(ScriptBuilderTest::readTwoStrings);
    }

    @Test
    void successfulExpectOnly() {
        new ScriptBuilder()
                .expect("Hello World!")
                .validate(ScriptBuilderTest::helloWorld);
    }

    @Test
    void underflowSayOnly() {
        Assertions.assertThrows(AssertionFailedError.class,
                () -> {
                    new ScriptBuilder()
                            .say("first")
                            .validate(ScriptBuilderTest::readTwoStrings);
                }
        );
    }

    @Test
    void wrongExpectValue() {
        Assertions.assertThrows(RuntimeException.class,
                () -> {
                    new ScriptBuilder()
                            .expect("Nope.")
                            .validate(ScriptBuilderTest::helloWorld);
                }
        );
    }

}
