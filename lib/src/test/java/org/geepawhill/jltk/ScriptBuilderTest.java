package org.geepawhill.jltk;

import org.junit.jupiter.api.*;

import java.util.*;

public class ScriptBuilderTest {
    public static void readTwoStrings() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.nextLine();
    }

    public static void writeTwoStrings() {
        System.out.println("First line.");
        System.out.println("Second line.");
    }

    public static void writeReadWriteReadWrite() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("First line.");
        scanner.nextLine();
        System.out.println("Second line.");
        scanner.nextLine();
        System.out.println("Done");
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
                .expect("First line.")
                .expect("Second line.")
                .validate(ScriptBuilderTest::writeTwoStrings);
    }

    @Test
    void mixedWithMismatch() {
        new ScriptBuilder()
                .expect("First line.")
                .say("response")
                .expect("Second line.")
                .say("second")
                .expect("Done")
                .validate(ScriptBuilderTest::writeReadWriteReadWrite);
    }

    @Test
    void underflowExpectOnly() {
        Assertions.assertThrows(ScriptUnderflowException.class,
                () -> {
                    new ScriptBuilder()
                            .expect("First line.")
                            .validate(ScriptBuilderTest::writeTwoStrings);
                }
        );
    }

    @Test
    void underflowSayOnly() {
        Assertions.assertThrows(ScriptUnderflowException.class,
                () -> {
                    new ScriptBuilder()
                            .say("first")
                            .validate(ScriptBuilderTest::readTwoStrings);
                }
        );
    }

    @Test
    void wrongExpectValue() {
        Assertions.assertThrows(ScriptException.class,
                () -> {
                    new ScriptBuilder()
                            .expect("Nope.")
                            .validate(ScriptBuilderTest::helloWorld);
                }
        );
    }

    @Disabled("Double-check JUnit output.")
    @Test
    void wrongExpectValueUnsafe() {
        new ScriptBuilder()
                .expect("Nope.")
                .validate(ScriptBuilderTest::helloWorld);
    }

    @Disabled("Double-check JUnit output.")
    @Test
    void underflowOnSayUnsafe() {
        new ScriptBuilder()
                .say("Nope.")
                .validate(ScriptBuilderTest::readTwoStrings);
    }

    @Disabled("Double-check JUnit output.")
    @Test
    void underflowOnExpectUnsafe() {
        new ScriptBuilder()
                .expect("First line.")
                .validate(ScriptBuilderTest::writeTwoStrings);
    }

}
