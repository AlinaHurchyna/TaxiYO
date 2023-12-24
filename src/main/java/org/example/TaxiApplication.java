package org.example;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class TaxiApplication {
    private List<Taxi> taxis;
    private List<Client> clients;
    private List<Ride> rideHistory;

    private static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            // Create the Taxi table in the database
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Taxi (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "registration_number VARCHAR(255) NOT NULL," +
                    "available BOOLEAN NOT NULL)";
            statement.executeUpdate(createTableQuery);

            // Add sample data to the table
            String insertDataQuery = "INSERT INTO Taxi (registration_number, available) VALUES " +
                    "('XYZ123', TRUE), ('ABC456', TRUE)";
            statement.executeUpdate(insertDataQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        this.clients = new ArrayList<>();
        this.rideHistory = new ArrayList<>();
    }

    public List<Taxi> getTaxis() {
        return taxis;
    }

    public List<Ride> getRideHistory() {
        return rideHistory;
    }

    public void addTaxi(String registrationNumber) {
        Taxi taxi = new Taxi(registrationNumber);
        taxis.add(taxi);
    }

    public Client addClient(String firstName, String lastName, String phoneNumber) {
        Client client = new Client(firstName, lastName, phoneNumber);
        clients.add(client);
        return client;
    }

    public void orderTaxi(Client client) {
        Taxi availableTaxi = findAvailableTaxi();
        if (availableTaxi != null) {
            try {
                availableTaxi.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Taxi " + availableTaxi.getRegistrationNumber() + " ordered for client " +
                    client.getFirstName() + " " + client.getLastName() + ". Contact: " + client.getPhoneNumber());
        } else {
            System.out.println("No available taxis. Please try again later.");
        }
    }

    public void endRideAndAddToHistory(Taxi taxi) {
        taxi.endRide();
        if (taxi.getCurrentRide() != null) {
            rideHistory.add(taxi.getCurrentRide());
        }
    }

    private Taxi findAvailableTaxi() {
        for (Taxi taxi : taxis) {
            if (taxi.isAvailable()) {
                return taxi;
            }
        }
        return null;
    }
}
