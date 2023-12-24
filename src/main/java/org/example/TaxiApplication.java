package org.example;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;

public class TaxiApplication {
    private static List<Taxi> taxis;
    private List<Client> clients;
    private List<Ride> rideHistory;

    private static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    public void initialize() {

        taxis.add(new Taxi("ABC123"));
        taxis.add(new Taxi("XYZ789"));
    }

    public void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            // Create the Taxi table in the database
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Taxi (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "registration_number VARCHAR(255) NOT NULL," +
                    "available BOOLEAN NOT NULL)";
            statement.executeUpdate(createTableQuery);


            String insertDataQuery = "INSERT INTO Taxi (registration_number, available) VALUES " +
                    "('XYZ123', TRUE), ('ABC456', TRUE)";
            statement.executeUpdate(insertDataQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void orderTaxi(Client client) {
        System.out.println("Enter your location: ");

        Taxi availableTaxi = findAvailableTaxi();
        if (availableTaxi != null) {
            availableTaxi.setAvailable(false);
            System.out.println("Taxi ordered! Your taxi's registration number: " + availableTaxi.getRegistrationNumber());
        } else {
            System.out.println("Sorry, no available taxis at the moment. Please try again later.");
        }
    }

    private Taxi findAvailableTaxi() {
        return null;
    }

    public void loadTaxisFromDatabase() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Taxi");
            while (resultSet.next()) {
                String registrationNumber = resultSet.getString("registration_number");
                boolean available = resultSet.getBoolean("available");
                Taxi taxi = new Taxi(registrationNumber);
                taxi.setAvailable(available);
                taxis.add(taxi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTaxiToDatabase(Taxi taxi) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Taxi (registration_number, available) VALUES (?, ?)")) {
            preparedStatement.setString(1, taxi.getRegistrationNumber());
            preparedStatement.setBoolean(2, taxi.isAvailable());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTaxiStatusInDatabase(Taxi taxi) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE Taxi SET available = ? WHERE registration_number = ?")) {
            preparedStatement.setBoolean(1, taxi.isAvailable());
            preparedStatement.setString(2, taxi.getRegistrationNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TaxiApplication() {
        this.taxis = new ArrayList<>();
    }

    public TaxiApplication(TaxiRepository taxiRepository) {
    }

    public static void addTaxi(String registrationNumber) {
        Taxi taxi = new Taxi(registrationNumber);
        taxis.add(taxi);
        System.out.println("Taxi added: " + taxi);
    }

    public List<Taxi> getTaxis() {
        return taxis;
    }

    public static void main(String[] args) {
        TaxiApplication taxiApplication = new TaxiApplication();
        taxiApplication.addTaxi("XYZ123");
        taxiApplication.addTaxi("ABC456");

        // Print the list of taxis
        System.out.println("List of Taxis:");
        for (Taxi taxi : taxiApplication.getTaxis()) {
            System.out.println(taxi);
        }
    }


    public Taxi findTaxiByRegistrationNumber(String registrationNumber) {
        Optional<Object> taksowki;
        Optional<Taxi> foundTaxi = taxis.stream()
                .filter(taxi -> Objects.equals(taxi.toString(), registrationNumber))
                .findFirst();

        return foundTaxi.orElse(null);
    }

    public void endRideAndAddToHistory(Taxi taxi) {

    }

    public List<Ride> getRideHistory() {
        return rideHistory;
    }

    public void setRideHistory(List<Ride> rideHistory) {
        this.rideHistory = rideHistory;
    }
}