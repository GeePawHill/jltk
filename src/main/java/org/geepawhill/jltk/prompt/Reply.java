package org.geepawhill.jltk.prompt;

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
}
