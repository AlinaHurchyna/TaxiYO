package org.example;

import java.util.List;
import java.util.Scanner;

import java.util.Scanner;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        TaxiApplication application = new TaxiApplication();

        application.addTaxi("XYZ123");
        application.addTaxi("ABC456");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Order Taxi");
            System.out.println("2. End Taxi Ride");
            System.out.println("3. Display Ride History");
            System.out.println("4. Exit");
            System.out.println("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.println("Enter customer's first name: ");
                    String firstName = scanner.nextLine();
                    System.out.println("Enter customer's last name: ");
                    String lastName = scanner.nextLine();
                    System.out.println("Enter customer's phone number: ");
                    String phoneNumber = scanner.nextLine();

                    Client client = application.addClient(firstName, lastName, phoneNumber);
                    application.orderTaxi(client);
                    break;
                case 2:
                    System.out.println("Enter taxi registration number: ");
                    String registrationNumber = scanner.nextLine();
                    Taxi taxi = findTaxi(application, registrationNumber);
                    if (taxi != null) {
                        application.endRideAndAddToHistory(taxi);
                    } else {
                        System.out.println("No taxi with the specified registration number.");
                    }
                    break;
                case 3:
                    displayRideHistory(application);
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static Taxi findTaxi(TaxiApplication application, String registrationNumber) {
        for (Taxi taxi : application.getTaxis()) {
            if (taxi.getRegistrationNumber().equals(registrationNumber)) {
                return taxi;
            }
        }
        return null;
    }

    private static void displayRideHistory(TaxiApplication application) {
        List<Ride> rideHistory = application.getRideHistory();
        if (rideHistory.isEmpty()) {
            System.out.println("No ride history.");
        } else {
            System.out.println("Ride history:");
            for (Ride ride : rideHistory) {
                System.out.println(ride);
            }
        }
    }
}
