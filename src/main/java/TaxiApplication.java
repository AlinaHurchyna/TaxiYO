import entity.Client;
import entity.Ride;
import entity.Taxi;
import persistence.TaxiRepository;

import java.util.List;

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
            availableTaxi.available();
            taxiRepository.updateTaxiStatus(availableTaxi);
            System.out.println("Taxi ordered! Your taxi's registration number: " + availableTaxi.registrationNumber());
        } else {
            System.out.println("Sorry, no available taxis at the moment. Please try again later.");
        }
    }

    private Taxi findAvailableTaxi() {
        List<Taxi> availableTaxis = taxiRepository.getAvailableTaxis();
        return availableTaxis.isEmpty() ? null : availableTaxis.get(0);
    }

    public void endRideAndAddToHistory(Taxi taxi) {
        boolean available = taxi.available();
        taxiRepository.updateTaxiStatus(taxi);

    }

    public List<Ride> getRideHistory() {

        return taxiRepository.getRideHistory();
    }

    public Taxi findTaxiByRegistrationNumber(String registrationNumber) {
        return taxiRepository.findTaxiByRegistrationNumber(registrationNumber);
    }

    public void orderTaxi(com.mysql.cj.xdevapi.Client client) {

    }
}
