package za.co.wethinkcode.flow;

import org.yaml.snakeyaml.*;

import java.util.*;

public class YamlMap extends LinkedHashMap<String, Object> {
    public String asString() {
        DumperOptions options = new DumperOptions();
        options.setExplicitStart(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        return yaml.dump(this);
    }

    public void append(MapAppender... appenders) {
        for (int appender = 0; appender < appenders.length; appender++) {
            appenders[appender].putTo(this);
        }
    }
}
