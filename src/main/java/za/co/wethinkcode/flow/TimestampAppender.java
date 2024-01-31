package za.co.wethinkcode.flow;

import java.time.*;
import java.time.format.*;

public class TimestampAppender implements MapAppender {
    final String timestamp;

    TimestampAppender(LocalDateTime now) {
        timestamp = timestampFrom(now);
    }

    TimestampAppender() {
        this(LocalDateTime.now());
    }

    @Override
    public void putTo(YamlMap map) {
        map.put("timestamp", timestamp);
    }

    /**
     * Convert a LocalDateTime into a timestamp w/o fractional seconds, i.e. YYYY-MM-DD-HH-MM-SS
     *
     * @param time
     * @return
     */
    static String timestampFrom(LocalDateTime time) {
        return time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).split("\\.")[0];
    }
}
