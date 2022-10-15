package taketwo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Waypoint {
    public Timestamp timestamp;
    public double speed;
    @JsonProperty("speed_limit")
    public double speedLimit;

    public Waypoint() {
    }

    public Waypoint(Timestamp timestamp, double speed, double speedLimit) {
        this.timestamp = timestamp;
        this.speed = speed;
        this.speedLimit = speedLimit;
    }

    public boolean isSpeeding() {
        return speed > speedLimit;
    }


    public static State process(List<Waypoint> list) {
        if (list.size() < 2) {
            return new State(0, 0, 0, 0);
        }
        // Get a mutable, sorted representation of the list of waypoints.
        List<Waypoint> sortedList = list.stream().sorted(Comparator.comparing((Waypoint a) -> a.timestamp)).collect(Collectors.toList());
        var firstWaypoint = list.get(0);
        sortedList.remove(0);
        var accumulator = new State(0, 0, 0, 0);
        var previousWaypoint = firstWaypoint;
        for (Waypoint wp : list) {
            var previousLegTime = (wp.timestamp.getTime() - previousWaypoint.timestamp.getTime()) / 1000d;
            var previousLegDistance = previousWaypoint.speed * previousLegTime;
            var previousLegSpeedingDistance = previousWaypoint.isSpeeding() ? previousLegDistance : 0.0;
            var previousLegSpeedingTime = previousWaypoint.isSpeeding() ? previousLegTime : 0;
            var previousLeg = new State(previousLegSpeedingDistance,
                    previousLegSpeedingTime,
                    previousLegDistance,
                    previousLegTime
            );
            accumulator = accumulator.add(previousLeg);
            previousWaypoint = wp;
        }
        return accumulator;
    }

    @Override
    public String toString() {
        return "Waypoint{" +
                "timestamp=" + timestamp +
                ", speed=" + speed +
                ", speedLimit=" + speedLimit +
                ", speeding=" + isSpeeding() +
                '}';
    }
}
