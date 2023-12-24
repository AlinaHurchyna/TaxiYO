package org.example;

class Taxi {
    private String registrationNumber;
    private boolean available;

    public Taxi(String registrationNumber) {
        this.registrationNumber = registrationNumber;
        this.available = true;
    }

    public Taxi(Taxi taxi ) { this.registrationNumber = taxi.getRegistrationNumber();
        this.available = true;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Taxi{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", available=" + available +
                '}';
    }
}

