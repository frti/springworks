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


    public State process(List<Waypoint> list) {
        if (list.size() < 2) {
            return new State(0, 0, 0, 0);
        }
        /*List<Waypoint> sortedList = */
        // list.sort(Comparator.comparing((Waypoint a) -> a.timestamp)); // .collect(Collectors.toList());
        List<Waypoint> sortedList = list.stream().sorted(Comparator.comparing((Waypoint a) -> a.timestamp)).collect(Collectors.toList());
        System.out.println("====>>> " + sortedList);
        var firstWaypoint = list.get(0);
        sortedList.remove(0);
        var acc = new State(0, 0, 0, 0);
        var previousWaypoint = firstWaypoint;
        var totalTime = 0;
        for (Waypoint wp : list) {
            System.out.println("Comparing previous wp " + previousWaypoint + " with " + wp);
            var deltaTime = (wp.timestamp.getNanos() - previousWaypoint.timestamp.getNanos()) / 1000000000.0;
            totalTime += deltaTime; // TODO
            var previousDistance = previousWaypoint.speed * deltaTime;
            var previousSpeedingDistance = previousWaypoint.isSpeeding() ? previousDistance : 0.0;
            var previousSpeedingTime = previousWaypoint.isSpeeding() ? deltaTime : 0;
            System.out.println("deltaTime " + deltaTime);
            System.out.printf("previousDistance " + previousDistance);
            System.out.printf("previousSpeedingDistance " + previousSpeedingDistance);
        }
        return acc;
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
