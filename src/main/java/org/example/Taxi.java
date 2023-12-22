package org.example;

class Taxi {
    private String numerRejestracyjny;
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

    public void rozpocznijKurs(String poczatek, String koniec) {
        if (dostepne) {
            this.aktualnyKurs = new Kurs(poczatek, koniec, 0, getCurrentTime(), null);
            this.dostepne = false;
        } else {
            System.out.println("Taxi zajęte. Nie można rozpocząć nowego kursu.");
        }
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
}