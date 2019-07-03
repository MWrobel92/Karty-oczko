package algorytm.oczko;

import algorytm.ObrazGracza;

/**
 *
 * @author Michał Wróbel
 */
public class OczkoObrazGracza extends ObrazGracza {
    
    public int liczbaZwyciestw;
    public boolean maFure; 
    public boolean jestBotem;
    
        /**
     * Konstruktor.
     * @param gracz Gracz, który ma zostać zobrazowany.
     * @param wlasciciel Informacja o tym, czy obraz jest przygotowywany dla tego
     * gracza, czy też dla innego.
     */
    public OczkoObrazGracza(OczkoGracz gracz, boolean wlasciciel) {
        
        super(gracz, wlasciciel);
        liczbaZwyciestw = gracz.liczbaZwyciestw;
        maFure = gracz.maFure();
        
        if (gracz.jednostkaDecyzyjna == null) {
            jestBotem = false;
        }
        else {
            jestBotem = true;
        }
    }
    
}
