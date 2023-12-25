package entity;

public record Taxi(String registrationNumber, boolean available) {


    @Override
    public String toString() {
        return "Taxi{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", available=" + available +
                '}';
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setAvailable(boolean ignoredAvailable) {
    }

    public boolean isAvailable() {
        return false;
    }
}






