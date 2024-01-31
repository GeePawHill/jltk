package za.co.wethinkcode.flow;

import org.junit.jupiter.api.*;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

public class TimestampAppenderTest {

    @Test
    void putsToMap() {
        LocalDateTime time = LocalDateTime.of(2020, Month.JANUARY, 2, 12, 13, 14, 15);
        TimestampAppender appender = new TimestampAppender(time);
        YamlMap map = new YamlMap();
        map.append(appender);
        assertEquals("2020-01-02T12:13:14", map.get("timestamp"));
    }
}
