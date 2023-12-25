package persistence;

import entity.Ride;
import entity.Taxi;

import java.util.List;

public interface TaxiRepository {
    void initializeDatabase();
    void loadTaxis();
    void addTaxi(Taxi taxi);
    void updateTaxiStatus(Taxi taxi);
    List<Taxi> getAllTaxis();

    Taxi findTaxiByRegistrationNumber(String registrationNumber);

    List<Taxi> getAvailableTaxis();

    List<Ride> getRideHistory();
}
