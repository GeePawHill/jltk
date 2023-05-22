package org.geepawhill.jltk.prompt;

import org.geepawhill.jltk.script.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class Reply {

    private final String text;

    public Reply(String text) {
        this.text = text;
    }

    int asInteger() {
        return 0;
    }

    String asString() {
        return text;
    }

    boolean isQuit() {
        return false;
    }
}

class Prompt {
    private final String text;
    private final ArrayList<Reply> replies = new ArrayList<>();

    public Reply reply(int index) {
        return replies.get(index);
    }

    public Prompt(String text) {
        this.text = text;
    }

    public void anyString() {
        System.out.print(text);
        replies.add(new Reply(new Scanner(System.in).nextLine()));
    }

    public void nonEmptyString() {
        while (true) {
            System.out.print(text);
            String text = new Scanner(System.in).nextLine();
            if (!text.isBlank()) {
                replies.add(new Reply(text));
                break;
            }
        }
    }
}

public class PromptTest {

    @Test
    void anyString() {
        Prompt prompt = new Prompt("Enter a string: ");
        Optional<List<Reply>> reply;
        new ScriptBuilder()
                .expect("Enter a string: ")
                .sayln("Hi Mom!")
                .validate(prompt::anyString);
        assertEquals("Hi Mom!", prompt.reply(0).asString());
    }

    @Test
    void anyStringEmpty() {
        Prompt prompt = new Prompt("Enter a string: ");
        Optional<List<Reply>> reply;
        new ScriptBuilder()
                .expect("Enter a string: ")
                .sayln("")
                .validate(prompt::anyString);
        assertEquals("", prompt.reply(0).asString());
    }

    @Test
    void nonEmptyString() {
        Prompt prompt = new Prompt("Enter a string: ");
        Optional<List<Reply>> reply;
        new ScriptBuilder()
                .expect("Enter a string: ")
                .sayln("")
                .expect("Enter a string: ")
                .sayln("Hi mom!")
                .validate(prompt::nonEmptyString);
        assertEquals("Hi mom!", prompt.reply(0).asString());
    }

}
