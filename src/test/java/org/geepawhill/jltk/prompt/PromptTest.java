package org.geepawhill.jltk.prompt;

import org.geepawhill.jltk.script.*;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class PromptTest {

    @Test
    void systemIoAnyString() {
        Prompt prompt = new Prompt("Enter a string: ");
        new ConsoleTester()
                .computerPrompts("Enter a string: ")
                .humanSays("Hi Mom!")
                .run(prompt::run);
        assertEquals("Hi Mom!", prompt.asString());
    }

    @Test
    void alternateIoAnyString() {
        PrintStream alternateOut = new PrintStream(new ByteArrayOutputStream());
        ByteArrayInputStream alternateIn = new ByteArrayInputStream("xyzzy\n".getBytes());
        Prompt prompt = new Prompt(alternateIn, alternateOut, "Enter a string: ", new StringChecker());
        prompt.run();
        assertEquals("xyzzy", prompt.asString());
    }

    @Test
    void anyStringEmpty() {
        Prompt prompt = new Prompt("Enter a string: ");
        new ConsoleTester()
                .computerPrompts("Enter a string: ")
                .humanSays("")
                .run(prompt::run);
        assertEquals("", prompt.asString());
    }

    @Test
    void nonEmptyString() {
        Prompt prompt = new Prompt("Enter a string: ", new NonEmptyChecker());
        new ConsoleTester()
                .computerPrompts("Enter a string: ")
                .humanSays("")
                .computerPrompts("Enter a string: ")
                .humanSays("Hi mom!")
                .run(prompt::run);
        assertEquals("Hi mom!", prompt.asString());
    }

    @Test
    void anyInteger() {
        Prompt prompt = new Prompt("Enter an integer: ", new IntegerChecker());
        new ConsoleTester()
                .computerPrompts("Enter an integer: ")
                .humanSays("xyzzy")
                .computerPrompts("Enter an integer: ")
                .humanSays("3")
                .run(prompt::run);
        assertEquals(3, prompt.asInteger());
    }

    @Test
    void threeIntegers() {
        Checker[] checkers = new Checker[3];
        for (int checker = 0; checker < 3; checker++) checkers[checker] = new IntegerChecker();
        PrintStream alternateOut = new PrintStream(new ByteArrayOutputStream());
        ByteArrayInputStream alternateIn = new ByteArrayInputStream("1,2,3\n".getBytes());
        Prompt prompt = new Prompt(
                alternateIn,
                alternateOut,
                "Enter three integers: ",
                new SeriesChecker(",", checkers)
        );
        prompt.run();
        assertEquals(1, prompt.asReplies().get(0).asInteger());
        assertEquals(2, prompt.asReplies().get(1).asInteger());
        assertEquals(3, prompt.asReplies().get(2).asInteger());
    }
}
