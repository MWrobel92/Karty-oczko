package algorytm;

import java.util.LinkedList;

/**
 * Klasa abstrakcyjna reprezentująca gracza w dowolnej grze karcianej.
 * @author Michał Wróbel
 */
public abstract class Gracz {
    
    protected LinkedList<Karta> karty;
    protected String nazwa;
    
    public void dodajKarte(Karta karta) {
        karta.widocznosc = Karta.Widocznosc.ZAKRYTA;
        karty.addLast(karta);
    }
    
    public Gracz() {
        karty = new LinkedList<>();
        nazwa = "Nienazwany gracz";
    }
    
    public void ustawNazwe(String nowaNazwa) {
        nazwa = nowaNazwa;
    }
    
    public String pobierzNazwe() {
        return nazwa;
    }
   
}
