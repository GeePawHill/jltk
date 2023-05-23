package org.geepawhill.jltk.prompt;

import java.io.*;
import java.util.*;

/**
 * Provides easy and safe collection and validation of user input.
 * <p>
 * The Prompt class is used to create prompts on System.out and read whole lines from
 * System.in, returning those lines as Reply objects which can be used to extract
 * fundamental java types safely. Unlike java.util.Scanner, it does not throw exceptions on
 * invalid user input, it just repeats the prompt and tries again.
 * <p>
 * The snippet below will prompt the user with "Please enter the row number: " and wait for
 * the user to type characters and press enter. If the user enters a valid integer, it will
 * return, and that integer will be the 0th reply value. If the user enters a non-integer, or a
 * blank line, it will repeat the prompt.
 *
 * <code>
 * Prompt rowPrompt = new Prompt("Please enter the row number: ");
 * rowPrompt.anyInteger();
 * int rowNumer = rowPrompt.reply(0).asInteger();
 * </code>
 */
public class Prompt {
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

    public void run(Checker checker) {
        run(System.in, System.out, checker);
    }

    public void run(InputStream in, PrintStream out, Checker checker) {
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
