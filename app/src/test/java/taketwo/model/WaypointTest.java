package taketwo.model;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WaypointTest {

//    @Test
//    void processBasic() {
//        // 1 leg, first first WP @ 5sec, speeding
//        // next wp @ 10 sec.
//        // total time 10 sec
//        var wp0 = new Waypoint(Timestamp.from(Instant.ofEpochSecond(someDay.getTime() + 10000)), 25.0d, 20.0d);
//        var wp1 = new Waypoint(Timestamp.from(Instant.ofEpochSecond(someDay.getTime() + 5000)), 45.0d, 20.0d);
//        List<Waypoint> wps = List.of(wp0, wp1);
//        assethatrttha wp0.process(wps)
//        System.out.println("result = " + result);
//    }

    @Test
    void superSimple() {
        // 1 leg, first first WP @ 30 sec, speeding
        // next wp @ 60 sec.
        // total time 30 sec
        int someTime = 1665748695; // 2022-10-14, millisecs since epoch
        final Timestamp someDay = Timestamp.from(Instant.ofEpochSecond(someTime));
        System.out.println("someTime is " + someTime);
        final var ts0 = Timestamp.from(Instant.ofEpochSecond(someTime + 30));
        final var ts1 = Timestamp.from(Instant.ofEpochSecond(someTime + 60));
        final var wp0 = new Waypoint(ts0, 25.0d, 20.0d);
        final var wp1 = new Waypoint(ts1, 10.0d, 20.0d);
        System.out.println("wp0 is " + wp0);
        System.out.println("wp1 is " + wp1);
        assertTrue(wp0.isSpeeding());
        assertFalse(wp1.isSpeeding());

        List<Waypoint> wps = List.of(wp0, wp1);
        var result = wp0.process(wps);
        System.out.println("result = " + result);
        final var expected = new State(750, 30, 750, 30);
        assertEquals(expected, result);
    }
}