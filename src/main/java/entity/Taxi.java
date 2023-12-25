package entity;

public record Taxi(String registrationNumber, boolean available) {

    public Taxi withAvailability(boolean newAvailability) {
        return new Taxi(registrationNumber, newAvailability);
    }

    @Override
    public String toString() {
        return "Taxi{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", available=" + available +
                '}';
    }
}






