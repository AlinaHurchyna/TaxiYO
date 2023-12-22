package org.example;

class Klient {
    private String imie;
    private String nazwisko;
    private String numerTelefonu;

    public Klient(String imie, String nazwisko, String numerTelefonu) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.numerTelefonu = numerTelefonu;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getNumerTelefonu() {
        return numerTelefonu;
    }
}