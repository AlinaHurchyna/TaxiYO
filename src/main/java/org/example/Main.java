package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        TaxiAplikacja aplikacja = new TaxiAplikacja();

        aplikacja.dodajTaxi("XYZ123");
        aplikacja.dodajTaxi("ABC456");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Zamów Taxi");
            System.out.println("2. Zakończ kurs taksówki");
            System.out.println("3. Wyświetl historię kursów");
            System.out.println("4. Wyjście");
            System.out.println("Wybierz opcję: ");

            int wybor = scanner.nextInt();
            scanner.nextLine(); // Konsumuj znak nowej linii

            switch (wybor) {
                case 1:
                    System.out.println("Podaj imię klienta: ");
                    String imie = scanner.nextLine();
                    System.out.println("Podaj nazwisko klienta: ");
                    String nazwisko = scanner.nextLine();
                    System.out.println("Podaj numer telefonu klienta: ");
                    String numerTelefonu = scanner.nextLine();

                    Klient klient = aplikacja.dodajKlienta(imie, nazwisko, numerTelefonu);
                    aplikacja.zamowTaxi(klient);
                    break;
                case 2:
                    System.out.println("Podaj numer rejestracyjny taksówki: ");
                    String numerRejestracyjny = scanner.nextLine();
                    Taxi taxi = znajdzTaxi(aplikacja, numerRejestracyjny);
                    if (taxi != null) {
                        aplikacja.zakonczKursITDodajDoHistorii(taxi);
                    } else {
                        System.out.println("Brak taksówki o podanym numerze rejestracyjnym.");
                    }
                    break;
                case 3:
                    wyswietlHistorieKursow(aplikacja);
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Niepoprawny wybór.");
            }
        }
    }

    private static Taxi znajdzTaxi(TaxiAplikacja aplikacja, String numerRejestracyjny) {
        for (Taxi taxi : aplikacja.getTaksowki()) {
            if (taxi.getNumerRejestracyjny().equals(numerRejestracyjny)) {
                return taxi;
            }
        }
        return null;
    }

    private static void wyswietlHistorieKursow(TaxiAplikacja aplikacja) {
        List<Kurs> historiaKursow = aplikacja.getHistoriaKursow();
        if (historiaKursow.isEmpty()) {
            System.out.println("Brak historii kursów.");
        } else {
            System.out.println("Historia kursów:");
            for (Kurs kurs : historiaKursow) {
                System.out.println(kurs);
            }
        }
    }
}