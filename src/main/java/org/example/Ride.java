package org.example;

public class Ride {
    private String start;
    private String end;
    private double price;
    private String startTime;
    private String endTime;

    public Ride(String start, String end, double price, String startTime, String endTime) {
        this.start = start;
        this.end = end;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Ride from " + start + " to " + end + ", price: " + price + ", start time: " + startTime + ", end time: " + endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
