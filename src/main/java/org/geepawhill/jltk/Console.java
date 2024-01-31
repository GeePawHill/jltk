package org.geepawhill.jltk;

import org.geepawhill.jltk.prompt.*;

public interface Console {
    void println(String s);

    void print(String s);

    Prompt prompt(String text, Checker... checkers);

    int anyInteger(String text);
}
