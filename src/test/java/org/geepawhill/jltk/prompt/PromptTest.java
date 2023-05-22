package org.geepawhill.jltk.prompt;

import org.geepawhill.jltk.script.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@FunctionalInterface
interface Checker {
    boolean isSatisfied(String candidate, ArrayList<Reply> replies);
}

class Reply {

    private final String text;

    public Reply(String text) {
        this.text = text;
    }

    int asInteger() {
        return Integer.parseInt(text);
    }

    String asString() {
        return text;
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
        anyString(System.in, System.out);
    }

    public void anyString(InputStream in, PrintStream out) {
        run(in, out, this::anyChecker);
    }

    private boolean anyChecker(String text, ArrayList<Reply> replies) {
        replies.add(new Reply(text));
        return true;
    }

    private void run(InputStream in, PrintStream out, Checker checker) {
        while (true) {
            out.print(text);
            String response = new Scanner(in).nextLine();
            if (checker.isSatisfied(response, replies)) break;
        }
    }

    public void nonEmptyString() {
        nonEmptyString(System.in, System.out);
    }

    public void nonEmptyString(InputStream in, PrintStream out) {
        run(in, out, this::nonEmptyChecker);
    }

    private boolean nonEmptyChecker(String text, ArrayList<Reply> replies) {
        if (text.isBlank()) return false;
        replies.add(new Reply(text));
        return true;
    }

    public void anyInteger() {
        anyInteger(System.in, System.out);
    }

    public void anyInteger(InputStream in, PrintStream out) {
        run(in, out, this::integerChecker);
    }

    private boolean integerChecker(String text, ArrayList<Reply> replies) {
        try {
            Integer.parseInt(text);
        } catch (Exception unused) {
            return false;
        }
        replies.add(new Reply(text));
        return true;
    }
}

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
