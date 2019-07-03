package algorytm;

/**
 * Klasa raprezentująca kartę, która może być rozdana graczowi.
 * Oprócz samej karty klasa zawiera informacje na temat jej widoczności.
 * @author Michał Wróbel
 */
public class Karta {
    
    /**
     * Typ wyliczeniowy określający, dla kogo karta jest odkryta.
     */
    public enum Widocznosc {
        /** Widoczna dla wszystkich graczy */
        ODKRYTA,
        /** Widoczna tylko dla gracza trzymającego kartę */
        ZAKRYTA,
        /** Niewidoczna dla nikogo */
        CALKIEM_ZAKRYTA
    }
    
    public final WartoscKarty karta;
    public Widocznosc widocznosc;
    
    /**
     * Domyślnie ustawia kartę jako zakrytą.
     * @param karta 
     */
    public Karta (WartoscKarty karta) {
        this.karta = karta;
        widocznosc = Widocznosc.ZAKRYTA;
    }
    
    public Karta (WartoscKarty karta, Widocznosc widocznosc) {
        this.karta = karta;
        this.widocznosc = widocznosc;
    }
    
    /**
     * Ustawia widoczność karty w pozycji ODKRYTA.
     */
    public void odkryj() {
        widocznosc = Widocznosc.ODKRYTA;
    }
    
    /**
     * Ustawia widoczność karty w pozycji ZAKRYTA.
     */
    public void zakryj() {
        widocznosc = Widocznosc.ZAKRYTA;
    }
    
    /**
     * Ustawia widoczność karty w pozycji CALKIEM_ZAKRYTA.
     */
    public void zakryjCalkiem() {
        widocznosc = Widocznosc.CALKIEM_ZAKRYTA;
    }
    
    public boolean jestOdkryta() {
        if (widocznosc == Widocznosc.ODKRYTA) 
            return true;
        else
            return false;
        
    }
    
    public boolean jestZakryta() {
        if (widocznosc == Widocznosc.ZAKRYTA) 
            return true;
        else
            return false;
        
    }
    
    public boolean jestCalkiemZakryta() {
        if (widocznosc == Widocznosc.CALKIEM_ZAKRYTA) 
            return true;
        else
            return false;
        
    }
    
    @Override
    public String toString() {
        return karta.toString();
    }
}
