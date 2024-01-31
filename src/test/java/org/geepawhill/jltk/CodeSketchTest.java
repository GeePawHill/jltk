package org.geepawhill.jltk;

import org.junit.jupiter.api.*;


public class CodeSketchTest {
    TestingConsole console = new TestingConsole();

    void promptForNumber(Console console) {
        System.out.println(console.anyInteger("Say something"));
    }

    @Test
    void sketchOne() {
        console
                .computerPrompts("Say something")
                .humanSays("32");
        promptForNumber(console);
    }
}
