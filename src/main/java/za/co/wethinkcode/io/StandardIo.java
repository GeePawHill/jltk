package za.co.wethinkcode.io;

import za.co.wethinkcode.prompt.*;

import java.io.*;

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
}
