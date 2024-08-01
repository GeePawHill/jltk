package za.co.wethinkcode.io;

import za.co.wethinkcode.prompt.*;

import java.io.*;
import java.math.*;
import java.util.*;

public class StandardIo implements Io {

    private final InputStream in;
    private final PrintStream out;
    private final PrintStream err;

    public StandardIo(InputStream in, PrintStream out, PrintStream err){
        this.in = in;
        this.out = out;
        this.err = err;        
    }

    public StandardIo(InputStream in, PrintStream out) {
        this(in, out, System.err);
    }

    public StandardIo() {
        this(System.in, System.out);
    }

    @Override
    public Io println(String s) {
        out.println(s);
        return this;
    }

    @Override
    public Io println() {
        out.println();
        return this;
    }

    @Override
    public Io print(String s) {
        out.print(s);
        return this;
    }

    @Override
    public Io errorln(String s) {
        err.println(s);
        return this;
    }

    @Override
    public Io errorln() {
        err.println();
        return this;
    }

    @Override
    public Io error(String s) {
        err.print(s);
        return this;
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
    public float anyFloat(String text) {
        Prompt prompt = prompt(text, new FloatChecker());
        prompt.run();
        return prompt.asFloat();
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
