package entity;

public class Ride {
    private String startLocation;
    private String endLocation;
    private double fare;

    public Ride(String startLocation, String endLocation, double fare) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.fare = fare;
    }

    @Override
    public String toString() {
        return "Ride from " + startLocation + " to " + endLocation + ", Fare: $" + fare;
    }
}
