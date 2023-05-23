package org.geepawhill.jltk.prompt;

import org.geepawhill.jltk.script.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PromptTest {

    @Test
    void anyString() {
        Prompt prompt = new Prompt("Enter a string: ");
        new ScriptBuilder()
                .expect("Enter a string: ")
                .sayln("Hi Mom!")
                .validate(prompt::anyString);
        assertEquals("Hi Mom!", prompt.reply(0).asString());
    }

    @Test
    void anyStringEmpty() {
        Prompt prompt = new Prompt("Enter a string: ");
        new ScriptBuilder()
                .expect("Enter a string: ")
                .sayln("")
                .validate(prompt::anyString);
        assertEquals("", prompt.reply(0).asString());
    }

    @Test
    void nonEmptyString() {
        Prompt prompt = new Prompt("Enter a string: ");
        new ScriptBuilder()
                .expect("Enter a string: ")
                .sayln("")
                .expect("Enter a string: ")
                .sayln("Hi mom!")
                .validate(prompt::nonEmptyString);
        assertEquals("Hi mom!", prompt.reply(0).asString());
    }

    @Test
    void anyInteger() {
        Prompt prompt = new Prompt("Enter an integer: ");
        new ScriptBuilder()
                .expect("Enter an integer: ")
                .sayln("xyzzy")
                .expect("Enter an integer: ")
                .sayln("3")
                .validate(prompt::anyInteger);
        assertEquals(3, prompt.reply(0).asInteger());
    }
}
