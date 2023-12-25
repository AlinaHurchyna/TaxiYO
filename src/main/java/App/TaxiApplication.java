package App;

import entity.Client;
import entity.Ride;
import entity.Taxi;
import persistence.JdbcTaxiRepository;
import persistence.TaxiRepository;

import java.util.List;
import java.util.Scanner;

public class TaxiApplication {
    private final TaxiRepository taxiRepository;

    public TaxiApplication(TaxiRepository taxiRepository) {
        this.taxiRepository = taxiRepository;
    }

    public void initialize() {
        taxiRepository.initializeDatabase();
        taxiRepository.loadTaxis();
    }

    public void orderTaxi(Client client) {
        System.out.println("Enter your location: ");

        Taxi availableTaxi = findAvailableTaxi();
        if (availableTaxi != null) {
            availableTaxi.setAvailable(false);
            taxiRepository.updateTaxiStatus(availableTaxi);
            System.out.println("Taxi ordered! Your taxi's registration number: " + availableTaxi.getRegistrationNumber());
        } else {
            System.out.println("Sorry, no available taxis at the moment. Please try again later.");
        }
    }

    private Taxi findAvailableTaxi() {
        List<Taxi> availableTaxis = taxiRepository.getAvailableTaxis();
        return availableTaxis.isEmpty() ? null : availableTaxis.get(0);
    }

    public void endRideAndAddToHistory(Taxi taxi) {
        taxi.setAvailable(true);
        taxiRepository.updateTaxiStatus(taxi);
        // Dodaj logikę zakończenia kursu i dodania do historii
    }

    public List<Ride> getRideHistory() {
        // Pobierz historię z repozytorium
        return taxiRepository.getRideHistory();
    }

    public static void main(String[] args) {
        TaxiRepository taxiRepository = new JdbcTaxiRepository();
        TaxiApplication taxiApplication = new TaxiApplication(taxiRepository);
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

    private Taxi findTaxiByRegistrationNumber(String registrationNumber) {
        return taxiRepository.findTaxiByRegistrationNumber(registrationNumber);
    }
}
