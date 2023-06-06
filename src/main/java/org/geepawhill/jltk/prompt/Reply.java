package org.geepawhill.jltk.prompt;

import java.math.*;

public class Reply {

    private final String text;

    public Reply(String text) {
        this.text = text;
    }

    int asInteger() {
        return Integer.parseInt(text);
    }

    String asString() {
        return text;
    }

    double asDouble() {
        return Double.parseDouble(text);
    }

    BigDecimal asDecimal() {
        return new BigDecimal(text);
    }
}
