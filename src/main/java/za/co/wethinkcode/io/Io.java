package za.co.wethinkcode.io;

import za.co.wethinkcode.prompt.*;

public interface Io {
    void println(String s);

    void print(String s);

    Prompt prompt(String text, Checker... checkers);

    int anyInteger(String text);
}
