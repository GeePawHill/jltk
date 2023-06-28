package org.geepawhill.jltk.script;

import org.junit.jupiter.api.*;

import java.util.*;

class ConsoleSamples {
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

    public static void readTwoStrings() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.nextLine();
    }

    public static void simpleQueryEof() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("guess");
        scanner.nextLine();
        System.out.println("You guessed it!");
    }
}


public class ConsoleTesterTest {
    @Test
    void successfulHumanSaysOnly() {
        new ConsoleTester()
                .humanSays("first")
                .humanSays("second")
                .run(ConsoleSamples::readTwoStrings);
    }

    @Test
    void successfulComputerSaysOnly() {
        new ConsoleTester()
                .computerSays("First line.")
                .computerSays("Second line.")
                .run(ConsoleSamples::writeTwoLines);
    }

    @Test
    void successfulOnePrompt() {
        new ConsoleTester()
                .computerPrompts("Just this: ")
                .run(ConsoleSamples::onePrompt);
    }

    @Test
    void successfulTwoPrompts() {
        new ConsoleTester()
                .computerPrompts("Just this: ")
                .computerPrompts(" Then that: ")
                .run(ConsoleSamples::twoPrompts);
    }

    @Test
    void successfulMixedDialog() {
        new ConsoleTester()
                .computerSays("First line.")
                .humanSays("response")
                .computerSays("Second line.")
                .humanSays("second")
                .computerSays("Done")
                .run(ConsoleSamples::writeReadWriteReadWrite);
    }

    @Test
    void successfulComputerChatters() {
        new ConsoleTester()
                .computerChatters(2)
                .run(ConsoleSamples::writeTwoLines);
    }

    @Test
    void successfulComputerChattersUntil() {
        new ConsoleTester()
                .computerChattersUntil("Second Line", 2)
                .run(ConsoleSamples::writeTwoLines);
    }

    @Test
    void failedComputerChattersTooLong() {
        Assertions.assertThrows(ScriptException.class,
                () -> {
                    new ConsoleTester()
                            .computerChattersUntil("Second Line", 1)
                            .run(ConsoleSamples::writeTwoLines);
                }
        );
    }

    @Test
    void successfulEofInsteadOfLinefeed() {
        new ConsoleTester()
                .computerPrompts("guess")
                .humanSays("34")
                .computerSays("You guessed it!")
                .run(ConsoleSamples::simpleQueryEof);
    }

    @Test
    void underflowOnComputer() {
        Assertions.assertThrows(ScriptUnderflow.class,
                () -> {
                    new ConsoleTester()
                            .computerSays("First line.")
                            .run(ConsoleSamples::writeTwoLines);
                }
        );
    }

    @Test
    void underflowSayOnly() {
        Assertions.assertThrows(ScriptUnderflow.class,
                () -> {
                    new ConsoleTester()
                            .humanSays("first")
                            .run(ConsoleSamples::readTwoStrings);
                }
        );
    }


    @Test
    void wrongExpectValue() {
        Assertions.assertThrows(ScriptException.class,
                () -> {
                    new ConsoleTester()
                            .computerSays("Nope.")
                            .run(ConsoleSamples::helloWorld);
                }
        );
    }
}
