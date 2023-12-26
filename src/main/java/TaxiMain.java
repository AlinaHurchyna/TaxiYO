import entity.Client;
import entity.Ride;
import entity.Taxi;
import persistence.JdbcTaxiRepository;
import persistence.TaxiRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class TaxiMain {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "")) {

            initializeDatabase(connection);

            TaxiRepository taxiRepository = new JdbcTaxiRepository(connection);
            TaxiApplication taxiApplication = new TaxiApplication(connection, taxiRepository);
            taxiApplication.initialize();

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("1. Order Taxi");
                System.out.println("2. End Taxi Ride");
                System.out.println("3. Display Ride History");
                System.out.println("4. Exit");
                System.out.println("Choose option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        System.out.println("Enter client's name: ");
                        String name = scanner.nextLine();
                        System.out.println("Enter client's surname: ");
                        String surname = scanner.nextLine();
                        System.out.println("Enter client's phone number: ");
                        String phoneNumber = scanner.nextLine();

                        Client client = new Client(name, surname, phoneNumber);
                        taxiApplication.orderTaxi(client);
                        break;
                    case 2:
                        System.out.println("Enter taxi registration number: ");
                        String registrationNumber = scanner.nextLine();
                        Taxi taxi = taxiApplication.findTaxiByRegistrationNumber(registrationNumber);
                        if (taxi != null) {
                            taxiApplication.endRideAndAddToHistory(taxi);
                        } else {
                            System.out.println("No taxi with the provided registration number.");
                        }
                        break;
                    case 3:
                        displayRideHistory(taxiApplication);
                        break;
                    case 4:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayRideHistory(TaxiApplication taxiApplication) {
        List<Ride> rideHistory = taxiApplication.getRideHistory();
        if (rideHistory.isEmpty()) {
            System.out.println("No ride history.");
        } else {
            System.out.println("Ride History:");
            for (Ride ride : rideHistory) {
                System.out.println(ride);
            }
        }
    }

    private static void initializeDatabase(Connection connection) {
        try (Statement statement = connection.createStatement()) {

            boolean tableExists = statement.execute("SHOW TABLES LIKE 'Taxi'");
            if (!tableExists) {

                String createTableQuery = "CREATE TABLE Taxi (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "registration_number VARCHAR(255) NOT NULL," +
                        "available BOOLEAN NOT NULL)";
                statement.executeUpdate(createTableQuery);


                String insertDataQuery = "INSERT INTO Taxi (registration_number, available) VALUES " +
                        "('XYZ123', TRUE), ('ABC456', TRUE)";
                statement.executeUpdate(insertDataQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
