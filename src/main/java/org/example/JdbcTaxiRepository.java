package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTaxiRepository implements TaxiRepository {
    private static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    @Override
    public void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            // Create Taxi table in the database
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

    @Override
    public void loadTaxis() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Taxi");
            while (resultSet.next()) {
                String registrationNumber = resultSet.getString("registration_number");
                boolean available = resultSet.getBoolean("available");
                Taxi taxi = new Taxi(registrationNumber);
                taxi.setAvailable(available);
                TaxiApplication.addTaxi(taxi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTaxi(Taxi taxi) {
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

    @Override
    public void updateTaxiStatus(Taxi taxi) {
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

    @Override
    public List<Taxi> getAllTaxis() {
        List<Taxi> taxis = new ArrayList<>();
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
        return taxis;
    }
}
