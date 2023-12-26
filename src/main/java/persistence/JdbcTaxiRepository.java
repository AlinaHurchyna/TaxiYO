package persistence;

import entity.Ride;
import entity.Taxi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTaxiRepository implements TaxiRepository {


    private final Connection connection;


    public JdbcTaxiRepository(Connection databaseConnection) throws SQLException {
        this.connection = databaseConnection;
    }


    @Override
    public void initializeDatabase() {
        try (Statement statement = connection.createStatement()) {
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

    @Override
    public void loadTaxis() {

    }

    @Override
    public void addTaxi(Taxi taxi) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Taxi (registration_number, available) VALUES (?, ?)")) {
            preparedStatement.setString(1, taxi.registrationNumber());
            preparedStatement.setBoolean(2, taxi.available());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTaxiStatus(Taxi taxi) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE Taxi SET available = ? WHERE registration_number = ?")) {
            preparedStatement.setBoolean(1, taxi.available());
            preparedStatement.setString(2, taxi.registrationNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Taxi> getAllTaxis() {
        List<Taxi> taxis = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT registration_number, available FROM Taxi")) {
            while (resultSet.next()) {
                String registrationNumber = resultSet.getString("registration_number");
                boolean available = resultSet.getBoolean("available");
                taxis.add(new Taxi(registrationNumber, available));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taxis;
    }

    @Override
    public Taxi findTaxiByRegistrationNumber(String registrationNumber) {
        return null;
    }

    @Override
    public List<Taxi> getAvailableTaxis() {
        return null;
    }

    @Override
    public List<Ride> getRideHistory() {
        return null;
    }


}
