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
 * <pre>
 * Prompt rowPrompt = new Prompt("Please enter the row number: ");
 * rowPrompt.anyInteger();
 * int rowNumer = rowPrompt.reply(0).asInteger();
 * </pre>
 */
public class Prompt {
    private final String text;
    private final ArrayList<Reply> replies = new ArrayList<>();
    private final InputStream in;
    private final PrintStream out;
    private final Checker checker;

    public Prompt(InputStream in, PrintStream out, String text, Checker... checkers) {
        this.in = in;
        this.out = out;
        this.text = text;
        this.checker = safeChecker(checkers);
    }

    static private Checker safeChecker(Checker[] checkers) {
        if (checkers == null || checkers.length == 0) return new StringChecker();
        return checkers[0];
    }

    public Prompt(String text) {
        this.text = text;
        this.in = null;
        this.out = null;
        this.checker = null;
    }

    public Reply reply(int index) {
        return replies.get(index);
    }

    public Reply asReply() {
        return reply(0);
    }

    public List<Reply> asReplies() {
        return replies;
    }

    public String asString() {
        return asReply().asString();
    }

    public int asInteger() {
        return asReply().asInteger();
    }

    public void anyString() {
        anyString(System.in, System.out);
    }

    public void anyString(InputStream in, PrintStream out) {
        while (true) {
            out.print(text);
            String response = new Scanner(in).nextLine();
            if (((Checker) new StringChecker()).isSatisfied(response, replies)) break;
        }
    }

    public void run() {
        run(checker);
    }

    public void run(Checker checker) {
        while (true) {
            chooseOut().print(text);
            String response = new Scanner(chooseIn()).nextLine();
            if (checker.isSatisfied(response, replies)) break;
        }
    }

    private InputStream chooseIn() {
        if (in == null) return System.in;
        return in;
    }

    private PrintStream chooseOut() {
        if (out == null) return System.out;
        return out;
    }

    public void nonEmptyString() {
        nonEmptyString(System.in, System.out);
    }

    public void nonEmptyString(InputStream in, PrintStream out) {
        while (true) {
            out.print(text);
            String response = new Scanner(in).nextLine();
            if (((Checker) new NonEmptyChecker()).isSatisfied(response, replies)) break;
        }
    }

    public void anyInteger() {
        anyInteger(System.in, System.out);
    }

    public void anyInteger(InputStream in, PrintStream out) {
        while (true) {
            out.print(text);
            String response = new Scanner(in).nextLine();
            if (((Checker) new IntegerChecker()).isSatisfied(response, replies)) break;
        }
    }
}
