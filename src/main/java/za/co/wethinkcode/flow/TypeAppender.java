package za.co.wethinkcode.flow;

public class TypeAppender implements MapAppender {
    public final String type;

    public TypeAppender(String type) {
        this.type = type;
    }

    @Override
    public void putTo(YamlMap map) {
        map.put("type", type);
    }
}
