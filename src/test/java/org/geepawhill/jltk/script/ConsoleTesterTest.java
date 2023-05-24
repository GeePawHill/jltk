package org.geepawhill.jltk.script;

import org.junit.jupiter.api.*;

import java.util.*;

public class ConsoleTesterTest {
    public static void readTwoStrings() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.nextLine();
    }

    public static void writeTwoLines() {
        System.out.println("First line.");
        System.out.println("Second line.");
    }

    public static void onePrompt() {
        System.out.print("Just this: ");
    }

    public static void twoPrompts() {
        System.out.print("Just this: ");
        System.out.print(" Then that: ");
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
        new ConsoleTester()
                .humanSays("first")
                .humanSays("second")
                .run(ConsoleTesterTest::readTwoStrings);
    }

    @Test
    void successfulExpectlnOnly() {
        new ConsoleTester()
                .computerSays("First line.")
                .computerSays("Second line.")
                .run(ConsoleTesterTest::writeTwoLines);
    }

    @Test
    void successfulExpectOnly() {
        new ConsoleTester()
                .computerPrompts("Just this: ")
                .run(ConsoleTesterTest::onePrompt);
    }

    @Test
    void successfulTwoExpects() {
        new ConsoleTester()
                .computerPrompts("Just this: ")
                .computerPrompts(" Then that: ")
                .run(ConsoleTesterTest::twoPrompts);
    }

    @Test
    void mixedExpectsAndSays() {
        new ConsoleTester()
                .computerSays("First line.")
                .humanSays("response")
                .computerSays("Second line.")
                .humanSays("second")
                .computerSays("Done")
                .run(ConsoleTesterTest::writeReadWriteReadWrite);
    }

    @Test
    void ignoreLines() {
        new ConsoleTester()
                .computerChatters(2)
                .run(ConsoleTesterTest::writeTwoLines);
    }

    public static void simpleQueryEof() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("guess");
        scanner.nextLine();
        System.out.println("You guessed it!");
    }

    @Test
    void weirdLineEnding() {
        new ConsoleTester()
                .computerPrompts("guess")
                .humanSays("34")
                .computerSays("You guessed it!")
                .run(ConsoleTesterTest::simpleQueryEof);
    }

    @Test
    void underflowExpectOnly() {
        Assertions.assertThrows(ScriptUnderflow.class,
                () -> {
                    new ConsoleTester()
                            .computerSays("First line.")
                            .run(ConsoleTesterTest::writeTwoLines);
                }
        );
    }

    @Test
    void underflowSayOnly() {
        Assertions.assertThrows(ScriptUnderflow.class,
                () -> {
                    new ConsoleTester()
                            .humanSays("first")
                            .run(ConsoleTesterTest::readTwoStrings);
                }
        );
    }

    @Test
    void wrongExpectValue() {
        Assertions.assertThrows(ScriptException.class,
                () -> {
                    new ConsoleTester()
                            .computerSays("Nope.")
                            .run(ConsoleTesterTest::helloWorld);
                }
        );
    }

    @Disabled("Double-check JUnit output.")
    @Test
    void unsafeWrongExpectValue() {
        new ConsoleTester()
                .computerSays("Nope.")
                .run(ConsoleTesterTest::helloWorld);
    }

    @Disabled("Double-check JUnit output.")
    @Test
    void unsafeUnderflowOnSay() {
        new ConsoleTester()
                .humanSays("Nope.")
                .run(ConsoleTesterTest::readTwoStrings);
    }

    @Disabled("Double-check JUnit output.")
    @Test
    void unsafeUnderflowOnExpect() {
        new ConsoleTester()
                .computerSays("First line.")
                .run(ConsoleTesterTest::writeTwoLines);
    }

}
