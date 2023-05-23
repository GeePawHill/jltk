package org.geepawhill.jltk.prompt;

import java.io.*;
import java.util.*;

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
