package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaxiSystem {

    private static Connection connection;

    public static void main(String[] args) {
        try {
            // Inicjalizacja bazy danych
            connection = DriverManager.getConnection("jdbc:sqlite:taxi.db");

            // Tworzenie tabeli klientów
            createClientsTable();

            // Tworzenie tabeli kursów
            createTripsTable();

            // Przykłady użycia
            addClient("John Doe", "123-456-789");
            addClient("Jane Doe", "987-654-321");

            addTrip(1, 10.5, calculateCost(10.5));
            addTrip(2, 8.0, calculateCost(8.0));

            displayClients();
            displayTrips();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Zamykanie połączenia z bazą danych
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createClientsTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS clients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "phone TEXT NOT NULL)";
        executeUpdate(createTableSQL);
    }

    private static void createTripsTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS trips (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "client_id INTEGER," +
                "distance REAL NOT NULL," +
                "cost REAL NOT NULL," +
                "FOREIGN KEY (client_id) REFERENCES clients(id))";
        executeUpdate(createTableSQL);
    }

    private static void addClient(String name, String phone) throws SQLException {
        String insertSQL = "INSERT INTO clients (name, phone) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            executeUpdate(preparedStatement);
        }
    }

    private static void addTrip(int clientId, double distance, double cost) throws SQLException {
        String insertSQL = "INSERT INTO trips (client_id, distance, cost) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, clientId);
            preparedStatement.setDouble(2, distance);
            preparedStatement.setDouble(3, cost);
            executeUpdate(preparedStatement);
        }
    }

    private static double calculateCost(double distance) {
        // Dodaj dowolną logikę obliczania kosztu podróży
        return distance * 0.5;
    }

    private static void displayClients() throws SQLException {
        String selectSQL = "SELECT * FROM clients";
        try (ResultSet resultSet = executeQuery(selectSQL)) {
            System.out.println("Klienci:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                System.out.println("ID: " + id + ", Imię: " + name + ", Telefon: " + phone);
            }
        }
    }

    private static void displayTrips() throws SQLException {
        String selectSQL = "SELECT * FROM trips";
        try (ResultSet resultSet = executeQuery(selectSQL)) {
            System.out.println("\nKursy:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int clientId = resultSet.getInt("client_id");
                double distance = resultSet.getDouble("distance");
                double cost = resultSet.getDouble("cost");
                System.out.println("ID: " + id + ", ID klienta: " + clientId + ", Dystans: " + distance + ", Koszt: " + cost);
            }
        }
    }

    private static void executeUpdate(String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
    }

    private static void executeUpdate(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.executeUpdate();
    }

    private static ResultSet executeQuery(String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return preparedStatement.executeQuery();
        }
    }
}
