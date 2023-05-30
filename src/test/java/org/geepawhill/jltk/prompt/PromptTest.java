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
        ByteArrayInputStream alternateIn = new ByteArrayInputStream("xyzzy".getBytes());
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
}
