package za.co.wethinkcode.io;

import za.co.wethinkcode.prompt.*;
import za.co.wethinkcode.script.*;

import java.io.*;

public class TestingIo implements Io {

    private Script script = new Script();
    private final StandardIo console;


    public TestingIo() {
        console = new StandardIo(
                new ScriptInputStream(script),
                new PrintStream(new ScriptOutputStream(script))
        );
    }

    @Override
    public void println(String s) {
        console.println(s);
    }

    @Override
    public void print(String s) {
        console.print(s);
    }

    @Override
    public Prompt prompt(String text, Checker... checkers) {
        return console.prompt(text, checkers);
    }

    @Override
    public int anyInteger(String text) {
        return console.anyInteger(text);
    }

    public TestingIo humanSays(String line) {
        script.add(new HumanSays(line));
        return this;
    }

    public TestingIo computerPrompts(String line) {
        script.add(new ComputerPrompts(line));
        return this;
    }
}
