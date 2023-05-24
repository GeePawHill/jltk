package org.geepawhill.jltk.prompt;

import org.geepawhill.jltk.script.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PromptTest {

    @Test
    void anyString() {
        Prompt prompt = new Prompt("Enter a string: ");
        new ConsoleTester()
                .computerPrompts("Enter a string: ")
                .humanSays("Hi Mom!")
                .run(prompt::anyString);
        assertEquals("Hi Mom!", prompt.reply(0).asString());
    }

    @Test
    void anyStringEmpty() {
        Prompt prompt = new Prompt("Enter a string: ");
        new ConsoleTester()
                .computerPrompts("Enter a string: ")
                .humanSays("")
                .run(prompt::anyString);
        assertEquals("", prompt.reply(0).asString());
    }

    @Test
    void nonEmptyString() {
        Prompt prompt = new Prompt("Enter a string: ");
        new ConsoleTester()
                .computerPrompts("Enter a string: ")
                .humanSays("")
                .computerPrompts("Enter a string: ")
                .humanSays("Hi mom!")
                .run(prompt::nonEmptyString);
        assertEquals("Hi mom!", prompt.reply(0).asString());
    }

    @Test
    void anyInteger() {
        Prompt prompt = new Prompt("Enter an integer: ");
        new ConsoleTester()
                .computerPrompts("Enter an integer: ")
                .humanSays("xyzzy")
                .computerPrompts("Enter an integer: ")
                .humanSays("3")
                .run(prompt::anyInteger);
        assertEquals(3, prompt.reply(0).asInteger());
    }
}
