package taketwo.model;

public record State(
        double distanceSpeeding,
        double timeSpeeding,
        double totalDistance,
        double totalTime) {

    public State add(final State other) {
        return new State(this.distanceSpeeding + other.distanceSpeeding(),
                this.timeSpeeding + other.timeSpeeding,
                totalDistance + other.totalDistance,
                totalTime + other.totalTime);
    }

    @Override
    public String toString() {
        return "State{" +
                "distanceSpeeding=" + distanceSpeeding +
                ", timeSpeeding=" + timeSpeeding +
                ", totalDistance=" + totalDistance +
                ", totalTime=" + totalTime +
                '}';
    }
}
