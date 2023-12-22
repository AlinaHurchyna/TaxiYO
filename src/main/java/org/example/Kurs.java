package org.example;

class Kurs {
    private String poczatek;
    private String koniec;
    private double cena;
    private String czasRozpoczecia;
    private String czasZakonczenia;

    public Kurs(String poczatek, String koniec, double cena, String czasRozpoczecia, String czasZakonczenia) {
        this.poczatek = poczatek;
        this.koniec = koniec;
        this.cena = cena;
        this.czasRozpoczecia = czasRozpoczecia;
        this.czasZakonczenia = czasZakonczenia;
    }

    public String toString() {
        return "Kurs od " + poczatek + " do " + koniec + ", cena: " + cena + ", czas rozpoczęcia: " + czasRozpoczecia + ", czas zakończenia: " + czasZakonczenia;
    }

    public void setCzasZakonczenia(String czasZakonczenia) {
        this.czasZakonczenia = czasZakonczenia;
    }

    public String getCzasZakonczenia() {
        return czasZakonczenia;
    }
}