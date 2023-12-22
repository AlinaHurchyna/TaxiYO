package org.example;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
class TaxiAplikacja {
    private List<Taxi> taksowki;
    private List<Klient> klienci;
    private List<Kurs> historiaKursow;

        private static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
        private static final String USER = "sa";
        private static final String PASSWORD = "";

        public void inicjalizujBazeDanych() {
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 Statement statement = connection.createStatement()) {
                // Utwórz tabelę Taxi w bazie danych
                String createTableQuery = "CREATE TABLE IF NOT EXISTS Taxi (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "numer_rejestracyjny VARCHAR(255) NOT NULL," +
                        "dostepne BOOLEAN NOT NULL)";
                statement.executeUpdate(createTableQuery);

                // Dodaj przykładowe dane do tabeli
                String insertDataQuery = "INSERT INTO Taxi (numer_rejestracyjny, dostepne) VALUES " +
                        "('XYZ123', TRUE), ('ABC456', TRUE)";
                statement.executeUpdate(insertDataQuery);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void wczytajTaksowkiZBazy() {
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Taxi");
                while (resultSet.next()) {
                    String numerRejestracyjny = resultSet.getString("numer_rejestracyjny");
                    boolean dostepne = resultSet.getBoolean("dostepne");
                    Taxi taxi = new Taxi(numerRejestracyjny);
                    taxi.setDostepne(dostepne);
                    taksowki.add(taxi);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void dodajTaxiDoBazy(Taxi taxi) {
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO Taxi (numer_rejestracyjny, dostepne) VALUES (?, ?)")) {
                preparedStatement.setString(1, taxi.getNumerRejestracyjny());
                preparedStatement.setBoolean(2, taxi.isDostepne());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void aktualizujStatusTaksowkiWBazie(Taxi taxi) {
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "UPDATE Taxi SET dostepne = ? WHERE numer_rejestracyjny = ?")) {
                preparedStatement.setBoolean(1, taxi.isDostepne());
                preparedStatement.setString(2, taxi.getNumerRejestracyjny());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    public TaxiAplikacja() {
        this.taksowki = new ArrayList<>();
        this.klienci = new ArrayList<>();
        this.historiaKursow = new ArrayList<>();
    }

    public List<Taxi> getTaksowki() {
        return taksowki;
    }

    public List<Kurs> getHistoriaKursow() {
        return historiaKursow;
    }

    public void dodajTaxi(String numerRejestracyjny) {
        Taxi taxi = new Taxi(numerRejestracyjny);
        taksowki.add(taxi);
    }

    public Klient dodajKlienta(String imie, String nazwisko, String numerTelefonu) {
        Klient klient = new Klient(imie, nazwisko, numerTelefonu);
        klienci.add(klient);
        return klient;
    }

    public void zamowTaxi(Klient klient) {
        Taxi dostepneTaxi = znajdzDostepneTaxi();
        if (dostepneTaxi != null) {
            try {
                dostepneTaxi.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Taxi " + dostepneTaxi.getNumerRejestracyjny() + " zamówione dla klienta " +
                    klient.getImie() + " " + klient.getNazwisko() + ". Kontakt: " + klient.getNumerTelefonu());
        } else {
            System.out.println("Brak dostępnych taksówek. Proszę spróbować później.");
        }
    }

    public void zakonczKursITDodajDoHistorii(Taxi taxi) {
        taxi.zakonczKurs();
        if (taxi.getAktualnyKurs() != null) {
            historiaKursow.add(taxi.getAktualnyKurs());
        }
    }

    private Taxi znajdzDostepneTaxi() {
        for (Taxi taxi : taksowki) {
            if (taxi.isDostepne()) {
                return taxi;
            }
        }
        return null;
    }
}

