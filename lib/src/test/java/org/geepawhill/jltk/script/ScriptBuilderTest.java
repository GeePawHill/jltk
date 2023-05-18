package org.geepawhill.jltk.script;

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
        new ScriptBuilder()
                .sayln("first")
                .sayln("second")
                .validate(ScriptBuilderTest::readTwoStrings);
    }

    @Test
    void successfulExpectlnOnly() {
        new ScriptBuilder()
                .expectln("First line.")
                .expectln("Second line.")
                .validate(ScriptBuilderTest::writeTwoStrings);
    }

    @Test
    void successfulExpectOnly() {
        new ScriptBuilder()
                .expect("Just this: ")
                .validate(ScriptBuilderTest::onePrompt);
    }

    @Test
    void successfulTwoExpects() {
        new ScriptBuilder()
                .expect("Just this: ")
                .expect(" Then that: ")
                .validate(ScriptBuilderTest::twoPrompts);
    }


    @Test
    void mixedWithMismatch() {
        new ScriptBuilder()
                .expectln("First line.")
                .sayln("response")
                .expectln("Second line.")
                .sayln("second")
                .expectln("Done")
                .validate(ScriptBuilderTest::writeReadWriteReadWrite);
    }
    
    public static void simpleQueryEof() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("guess");
        scanner.nextLine();
        System.out.println("You guessed it!");
    }

    @Test
    void weirdLineEnding() {
        new ScriptBuilder()
                .expect("guess")
                .sayln("34")
                .expectln("You guessed it!")
                .validate(ScriptBuilderTest::simpleQueryEof);
    }

    @Test
    void underflowExpectOnly() {
        Assertions.assertThrows(ScriptUnderflowException.class,
                () -> {
                    new ScriptBuilder()
                            .expectln("First line.")
                            .validate(ScriptBuilderTest::writeTwoStrings);
                }
        );
    }

    @Test
    void underflowSayOnly() {
        Assertions.assertThrows(ScriptUnderflowException.class,
                () -> {
                    new ScriptBuilder()
                            .sayln("first")
                            .validate(ScriptBuilderTest::readTwoStrings);
                }
        );
    }

    @Test
    void wrongExpectValue() {
        Assertions.assertThrows(ScriptException.class,
                () -> {
                    new ScriptBuilder()
                            .expectln("Nope.")
                            .validate(ScriptBuilderTest::helloWorld);
                }
        );
    }

    @Disabled("Double-check JUnit output.")
    @Test
    void unsafeWrongExpectValue() {
        new ScriptBuilder()
                .expectln("Nope.")
                .validate(ScriptBuilderTest::helloWorld);
    }

    @Disabled("Double-check JUnit output.")
    @Test
    void unsafeUnderflowOnSay() {
        new ScriptBuilder()
                .sayln("Nope.")
                .validate(ScriptBuilderTest::readTwoStrings);
    }

    @Disabled("Double-check JUnit output.")
    @Test
    void unsafeUnderflowOnExpect() {
        new ScriptBuilder()
                .expectln("First line.")
                .validate(ScriptBuilderTest::writeTwoStrings);
    }

}
