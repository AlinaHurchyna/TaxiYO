package org.example;

import java.util.Objects;

public class Taxi {
    private final String registrationNumber;
    private boolean available;
    private Ride currentRide;

    public Taxi(String registrationNumber) {
        this.registrationNumber = registrationNumber;
        this.available = true;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public Ride getCurrentRide() {
        return currentRide;
    }

    public void endRide() {
        if (!available && currentRide != null) {
            currentRide.setEndTime(getCurrentTime());
            this.available = true;
            System.out.println("Ride completed. Fare: " + calculateRidePrice());
            this.currentRide = null;
        } else {
            System.out.println("Taxi is not occupied. Cannot end the ride.");
        }
    }

    private double calculateRidePrice() {
        // Logic for calculating the fare based on distance
        return 30.0; // Sample fare
    }

    private String getCurrentTime() {
        // Get the current time
        return "12:00"; // Sample time
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taxi taxi = (Taxi) o;
        return available == taxi.available &&
                Objects.equals(registrationNumber, taxi.registrationNumber) &&
                Objects.equals(currentRide, taxi.currentRide);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber, available, currentRide);
    }

    @Override
    public String toString() {
        return "Taxi{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", available=" + available +
                ", currentRide=" + currentRide +
                '}';
    }
}
