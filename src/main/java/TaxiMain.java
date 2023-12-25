import com.mysql.cj.xdevapi.Client;
import com.mysql.cj.xdevapi.Session;
import entity.Ride;
import entity.Taxi;
import App.TaxiApplication;

import java.util.List;
import java.util.Scanner;

public class TaxiMain {
    public static void main(String[] args) {
        TaxiApplication taxiApplication = new TaxiApplication();
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

                    Client client = new Client() {
                        @Override
                        public Session getSession() {
                            return null;
                        }

                        @Override
                        public void close() {

                        }
                    };
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
    }

    private static void displayRideHistory(TaxiApplication taxiApplication) {
        List<Ride> rideHistory = taxiApplication.getRideHistory();
        if (((List<?>) rideHistory).isEmpty()) {
            System.out.println("No ride history.");
        } else {
            System.out.println("Ride History:");
            for (Ride ride : rideHistory) {
                System.out.println(ride);
            }
        }
    }
}
