package algorytm.oczko;

import algorytm.Gracz;
import algorytm.Karta;
import java.util.LinkedList;

/**
 * Gracz w oczko.
 * @author Michał Wróbel
 */
public class OczkoGracz extends Gracz {
    
    /** Zawiera referencję do jednostki decyzyjnej w przypadku bota
     * lub null w przypadku gracza rzeczywistego. */
    public OczkoJednostkaDecyzyjna jednostkaDecyzyjna;    
    public int liczbaZwyciestw;
    
    public boolean maFure() {
        
        boolean wynik = false;
        
        if ((policzPunkty() > 21)&&(karty.size() > 2))
            wynik = true;
        
        return wynik;
    }
    
    public int policzPunkty() {
        
        int suma = 0;
        
        for(Karta k: karty) {
            suma += k.karta.punktyOczko();
        }
        
        return suma;
        
    }
        
    /**
     * Konstruktor.
     * @param bot Informacja, czy gracz ma być botem.
     */
    
    public OczkoGracz (boolean bot) {
        
        if (bot)
            jednostkaDecyzyjna = new OczkoJednostkaDecyzyjna();
        else 
            jednostkaDecyzyjna = null;
        
        liczbaZwyciestw = 0;
    }

    void odkryjKarty() {
        
        for (Karta k : karty) {
            k.odkryj();
        }
    
    }

    LinkedList<Karta> zabierzKarty() {
        
        LinkedList<Karta> zabrane = karty;
        karty = new LinkedList<>();
        return zabrane;
    }
    
}
