package org.geepawhill.jltk;

import org.geepawhill.jltk.prompt.*;
import org.geepawhill.jltk.script.*;

import java.io.*;

public class TestingConsole implements Console {

    private Script script = new Script();
    private final StandardConsole console;


    public TestingConsole() {
        console = new StandardConsole(
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

    public TestingConsole humanSays(String line) {
        script.add(new HumanSays(line));
        return this;
    }

    public TestingConsole computerPrompts(String line) {
        script.add(new ComputerPrompts(line));
        return this;
    }
}
