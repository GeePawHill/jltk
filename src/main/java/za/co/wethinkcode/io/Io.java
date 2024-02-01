package za.co.wethinkcode.io;

import za.co.wethinkcode.prompt.*;

import java.math.*;
import java.util.*;

public interface Io {
    void println(String s);

    void print(String s);

    Prompt prompt(String text, Checker... checkers);

    int anyInteger(String text);

    String anyString(String text);

    double anyDouble(String text);

    BigDecimal anyDecimal(String text);

    String nonEmpty(String text);

    List<Reply> manyIntegers(String text, int howMany);
}
