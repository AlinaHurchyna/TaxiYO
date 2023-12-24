package org.example;

import java.util.Objects;

class Taxi {
    private final String numerRejestracyjny;
    private boolean dostepne;
    private Kurs aktualnyKurs;

    public Taxi(String numerRejestracyjny) {
        this.numerRejestracyjny = numerRejestracyjny;
        this.dostepne = true;
    }

    public String getNumerRejestracyjny() {
        return numerRejestracyjny;
    }

    public boolean isDostepne() {
        return dostepne;
    }

    public Kurs getAktualnyKurs() {
        return aktualnyKurs;
    }

    public void zakonczKurs() {
        if (!dostepne && aktualnyKurs != null) {
            aktualnyKurs.setCzasZakonczenia(getCurrentTime());
            this.dostepne = true;
            System.out.println("Kurs zakończony. Cena: " + obliczCeneKursu());
            this.aktualnyKurs = null;
        } else {
            System.out.println("Taxi nie jest zajęte. Nie można zakończyć kursu.");
        }
    }

    private double obliczCeneKursu() {
        // Logika obliczania ceny na podstawie odległości
        return 30.0; // Przykładowa cena
    }

    private String getCurrentTime() {
        // Pobierz aktualny czas
        return "12:00"; // Przykładowy czas
    }

    public void setDostepne(boolean dostepne) {
        this.dostepne = dostepne;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taxi taxi = (Taxi) o;
        return dostepne == taxi.dostepne &&
                Objects.equals(numerRejestracyjny, taxi.numerRejestracyjny) &&
                Objects.equals(aktualnyKurs, taxi.aktualnyKurs);
    }
    public int hashCode() {
        return Objects.hash(numerRejestracyjny, dostepne, aktualnyKurs);
    }
    public String toString() {
        return "Taxi{" +
                "numerRejestracyjny='" + numerRejestracyjny + '\'' +
                ", dostepne=" + dostepne +
                ", aktualnyKurs=" + aktualnyKurs +
                '}';
    }
}