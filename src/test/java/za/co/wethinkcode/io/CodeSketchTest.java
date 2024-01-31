package za.co.wethinkcode.io;

import org.junit.jupiter.api.*;

public class CodeSketchTest {

    TestingIo console = new TestingIo();

    void promptForNumber(Io io) {
        System.out.println(io.anyInteger("Say something"));
    }

    @Test
    void sketchOne() {
        console
                .computerPrompts("Say something")
                .humanSays("32");
        promptForNumber(console);
    }
}
