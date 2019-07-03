package interfejs.jezyki;

/**
 *
 * @author Michał Wróbel
 */
public class TworcaJezykow {
    
    public static Jezyk utworzJezykPolski() {
        
        Jezyk jezyk = new Jezyk();
        
        jezyk.nazwaJezyka = "Polski";
        jezyk.oczko = "Oczko";
        jezyk.nazwaProgramu = "Gra w oczko";
        jezyk.liczbaZwyciestw = "Liczba zwycięstw: ";
        jezyk.fura = "FURA";
        jezyk.bankier = "Bankier";
        jezyk.gracz = "Gracz";
        jezyk.tak = "Tak";
        jezyk.nie = "Nie";
        jezyk.kontynuuj = "Kontynuuj";
        jezyk.graj = "Graj";
        jezyk.pytanieOKarte = "Czy chcesz ciągnąć kolejną kartę?";
        jezyk.otrzymalKarte = " otrzymał kartę.";
        jezyk.koniecRozdania = "Koniec rozdania - zwyciężył ";
        jezyk.remis = "Koniec rozdania - jest remis.";
        jezyk.menuOpcje = "Opcje";
        jezyk.menuKoszulka = "Zmień koszulkę";
        jezyk.menuPusteRuchy = "Przewijaj puste ruchy";
        jezyk.menuGra = "Gra";
        jezyk.menuPowrot = "Zakończ i wróć do menu";
        jezyk.gracz1bot1 = "1 gracz, 1 bot";
        jezyk.gracz1bot2 = "1 gracz, 2 boty";
        jezyk.gracz2bot1 = "2 graczy, 1 bot";
        jezyk.liczbaGraczy = "Liczba graczy:";
        jezyk.nazwaGracza1 = "Nazwa pierwszego gracza:";
        jezyk.nazwaGracza2 = "Nazwa drugiego gracza (opcjonalna):";
        jezyk.zmianaSterowania = "Zmiana gracza - sterowanie przejmuje ";
        return jezyk;
        
    }
}
