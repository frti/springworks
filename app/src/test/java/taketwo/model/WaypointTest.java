package taketwo.model;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WaypointTest {

    private final int someTime = 1665748695; // 2022-10-14, millisecs since epoch

    // Helper function to get a Timestamp at a given
    // offset (in seconds) from the someTime reference time.
    private Timestamp getTimestamp(long offset) {
        return Timestamp.from(Instant.ofEpochSecond(someTime + offset));
    }

    @Test
    void basic() {
        // 1 leg, first first WP @ 30 sec, speeding
        // next wp @ 60 sec.
        // total time 30 sec
        final var wp0 = new Waypoint(getTimestamp(30), 25.0d, 20.0d);
        final var wp1 = new Waypoint(getTimestamp(60), 10.0d, 20.0d);
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

    @Test
    void superSonic() {
        // 1 leg, first WP @ 0 sec, not speeding
        // next wp @ 10 sec, speeding
        // next @ 100 sec, not speeding
        // last @ 110 sec, not speeding
        // total time 30 sec
        final var wp0 = new Waypoint(getTimestamp(0), 5d, 20d);
        final var wp1 = new Waypoint(getTimestamp(10), 340d, 20d);
        final var wp2 = new Waypoint(getTimestamp(100), 5d, 20d);
        final var wp3 = new Waypoint(getTimestamp(110), 5d, 20d);

        List<Waypoint> wps = List.of(wp0, wp1, wp2, wp3);
        var result = wp0.process(wps);
        System.out.println("result = " + result);
        assertEquals(new State(30600, 90, 30700, 110), result);
    }
}