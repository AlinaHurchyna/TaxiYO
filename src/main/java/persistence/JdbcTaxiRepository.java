package persistence;

import entity.Ride;
import entity.Taxi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTaxiRepository implements TaxiRepository {
    private final Connection connection;

    private  final String SELECT_ALL_TAXIS_QUERY = "SELECT * FROM Taxi";
    private  final String INSERT_TAXI_QUERY = "INSERT INTO Taxi (registration_number, available) VALUES (?, ?)";
    private final String UPDATE_TAXI_STATUS_QUERY = "UPDATE Taxi SET available = ? WHERE registration_number = ?";

    public JdbcTaxiRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void initializeDatabase() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

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
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TAXI_QUERY)) {
            preparedStatement.setString(1, taxi.registrationNumber());
            preparedStatement.setBoolean(2, taxi.available());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTaxiStatus(Taxi taxi) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TAXI_STATUS_QUERY)) {
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
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_TAXIS_QUERY);
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
