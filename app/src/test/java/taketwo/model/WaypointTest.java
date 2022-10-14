package taketwo.model;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.*;

class WaypointTest {

    final public Timestamp someDay = Timestamp.from(Instant.ofEpochSecond(1665734817));

    @Test
    void processBasic() {
        var wp0 = new Waypoint(Timestamp.from(Instant.ofEpochSecond(someDay.getTime() + 10)), 25.0d, 20.0d);
        var wp1 = new Waypoint(Timestamp.from(Instant.ofEpochSecond(someDay.getTime() + 5)), 15.0d, 20.0d);
        List<Waypoint> wps = List.of(wp0, wp1);
        var result = wp0.process(wps);
        System.out.println("result = " + result);
    }
}