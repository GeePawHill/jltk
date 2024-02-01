package za.co.wethinkcode.io;

import za.co.wethinkcode.prompt.*;

import java.io.*;
import java.math.*;
import java.util.*;

public class StandardIo implements Io {

    private final InputStream in;
    private final PrintStream out;

    public StandardIo(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public StandardIo() {
        this(System.in, System.out);
    }

    @Override
    public void println(String s) {
        out.println(s);
    }

    @Override
    public void print(String s) {
        out.print(s);
    }

    @Override
    public Prompt prompt(String text, Checker... checkers) {
        return new Prompt(in, out, text, checkers);
    }

    @Override
    public int anyInteger(String text) {
        Prompt prompt = prompt(text, new IntegerChecker());
        prompt.run();
        return prompt.asInteger();
    }

    @Override
    public String anyString(String text) {
        Prompt prompt = prompt(text, new StringChecker());
        prompt.run();
        return prompt.asString();
    }

    @Override
    public double anyDouble(String text) {
        Prompt prompt = prompt(text, new DoubleChecker());
        prompt.run();
        return prompt.asDouble();
    }

    @Override
    public BigDecimal anyDecimal(String text) {
        Prompt prompt = prompt(text, new DecimalChecker());
        prompt.run();
        return prompt.asDecimal();
    }

    @Override
    public String nonEmpty(String text) {
        Prompt prompt = prompt(text, new NonEmptyChecker());
        prompt.run();
        return prompt.asString();
    }

    @Override
    public List<Reply> manyIntegers(String text, int howMany) {
        Checker[] checkers = new Checker[howMany];
        for (int checker = 0; checker < howMany; checker++) checkers[checker] = new IntegerChecker();
        Prompt prompt = prompt(text, new SeriesChecker("[\b,]", checkers));
        prompt.run();
        return prompt.asReplies();
    }

}
