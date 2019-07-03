package algorytm;
import java.util.ArrayList;

/**
 * Okrojone dane dotyczące gracza, przygotowane do przesłania do interfejsu
 * bądź jednoski decyzyjnej.
 * @author Michał Wróbel
 */
public class ObrazGracza {
    
    public ArrayList<ObrazKarty> karty;
    public String nazwa;
    
    /**
     * Konstruktor.
     * @param gracz Gracz, który ma zostać zobrazowany.
     * @param wlasciciel Informacja o tym, czy obraz jest przygotowywany dla tego
     * gracza, czy też dla innego.
     */
    public ObrazGracza(Gracz gracz, boolean wlasciciel) {
        
        karty = new ArrayList<>();
        
        for (Karta k : gracz.karty) {
            karty.add(new ObrazKarty(k, wlasciciel));
        } 
        
        nazwa = gracz.pobierzNazwe();
    }
    
}
