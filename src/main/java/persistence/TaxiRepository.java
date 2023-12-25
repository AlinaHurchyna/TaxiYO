package persistence;

import entity.Taxi;

import java.util.List;

public interface TaxiRepository {
    void initializeDatabase();
    void loadTaxis();
    void addTaxi(Taxi taxi);
    void updateTaxiStatus(Taxi taxi);
    List<Taxi> getAllTaxis();
}
