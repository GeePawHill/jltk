package za.co.wethinkcode.prompt;

import java.io.*;
import java.math.*;
import java.util.*;

/**
 * Provides easy and safe collection and validation of user input.
 * <p>
 * The Prompt class is used to create prompts on a PrintStream and read whole lines from
 * an InputStream, returning those lines as Reply objects which can be used to extract
 * fundamental java types safely. Unlike java.util.Scanner, it does not throw exceptions on
 * invalid user input, it just repeats the prompt and tries again.</p>
 *
 * <p>Although the Prompt class supports arbitrary i/o streams and can take any class
 * that implements Checker, it supports the most common scenarios in two ways:
 * </p>
 * <ul>
 *     <li>Multiple constructors assume System.in and System.out unless constructed otherwise.</li>
 *     <li>The most common cases are supported by static convenience functions.</li>
 * </ul>
 *
 * <p>The snippet below will prompt the user with "Please enter the row number: " and wait for
 * the user to type characters and press enter. If the user enters a valid integer, it will
 * return, and that integer will be the 0th reply value. If the user enters a non-integer, or a
 * blank line, it will repeat the prompt.</p>
 *
 * <pre>
 *      Prompt rowPrompt = new Prompt("Please enter the row number: ",new IntegerChecker());
 *      rowPrompt.run();
 *      int rowNumer = rowPrompt.reply(0).asInteger();
 * </pre>
 *
 * <p>That same effect can be achieved by this one-liner convenience function:</p>
 *
 * <pre>
 *      int rowNumber = Prompt.anyInteger("Please enter the row number: ");
 * </pre>
 *
 * <p>
 *     A prompt may be created once and run many times. Each run is a separate process and prints new
 *     text and reads input again.
 * </p>
 *
 * @see Checker for several useful pre-defined checkers.
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
        this.checker = Checker.safeChecker(checkers);
    }

    public Prompt(String text, Checker... checkers) {
        this(null, null, text, checkers);
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

    public double asDouble() {
        return asReply().asDouble();
    }

    public BigDecimal asDecimal() {
        return asReply().asDecimal();
    }

    public void run() {
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

    public static int anyInteger(String text) {
        Prompt prompt = new Prompt(text, new IntegerChecker());
        prompt.run();
        return prompt.asInteger();
    }
}
