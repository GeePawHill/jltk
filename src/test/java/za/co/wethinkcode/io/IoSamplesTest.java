package za.co.wethinkcode.io;


import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RetroBankingMenu {

    int run() {
        /*
        This is an example of the standard usage for beginners of System.in.
        Note the two big difficulties.
        1) It's hard to test.
        2) It actually throws an exception if the user doesn't enter an integer.
         */
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your choice (1-5): ");
        return scanner.nextInt();
    }
}

class IoBankingMenu {

    private final Io io;

    IoBankingMenu(Io io) {
        /*
        We can pass the Io object to the constructor and stash it in a field.
         */
        this.io = io;
    }

    int run() {
        /*
        Now our run not only doesn't use the System.in and System.out, but
        has the extra functionality for writing safe user prompts, and if you use
        the TestingIo instead of the StandardIo, it will let us script our test in
        a straightforward manner.
         */
        return io.anyInteger("Enter your choice (1-5): ");
    }
}

public class IoSamplesTest {


    @Test
    void oldStyleWithIntegerPrompt() {
        /*
        This, or some minor variation of it, is what one has to do
        to "hack" System.in and System.out such that we can test the old-style
        menu.

        Note, too, that any newlines embedded in the expected text will fail
        when you run cross-platform between linux and windows, because they use
        different newline conventions.

        Finally, note that even *this* won't work if an old-style approach grabs
        System.in and System.out (or makes a Scanner) before they've been replaced.
        In that case, one has to do the replacements and *then* call the constructor
        of the old-style class.
        */

        RetroBankingMenu oldStyle = new RetroBankingMenu();

        // stash old value of System.in and replace it with a new input stream,
        // supplying all the inputs  to the run, in this case just one line.
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("3\n".getBytes()));

        // stash old value of System.out and replace it with a new PrintStream,
        // one that captures the output in a byte array.
        PrintStream stdout = System.out;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);

        // Call the function
        int result = oldStyle.run();

        // Put System.in and System.out back, because we're done.
        // Note that if oldStyle.run() threw, this code won't happoen.
        System.setIn(stdin);
        System.setOut(stdout);

        // Run your assertions.
        assertEquals(3, result);
        String actualText = byteArrayOutputStream.toString();
        String expectedText = "Enter your choice (1-5): ";
        assertEquals(expectedText, actualText);
    }

    @Test
    void newStyleWithInteger() {
        /*
        But if we pass our unit under test an Io object instead,
        The test gets easy to read, easy to write, and easy to run.

        The prompt-building API for the Io class has a lot of variants.

        The scripting API for the TestingIo class has a lot of variants.
         */
        TestingIo io = new TestingIo();
        IoBankingMenu newStyle = new IoBankingMenu(io);

        io.computerPrompts("Enter your choice (1-5): ")
                .humanSays("3");
        assertEquals(3, newStyle.run());
    }

    @Test
    void newStyleWithIntegerRetry() {
        /*
        Here's another example demonstrating that the call to
        anyInteger() in new style run won't let a non-integer hreak things.
         */
        TestingIo io = new TestingIo();
        IoBankingMenu newStyle = new IoBankingMenu(io);

        io.computerPrompts("Enter your choice (1-5): ")
                .humanSays("asdf")
                .computerPrompts("Enter your choice (1-5): ")
                .humanSays("3")
        ;
        assertEquals(3, newStyle.run());
    }
}
