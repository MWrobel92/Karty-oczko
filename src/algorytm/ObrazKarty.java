package algorytm;

import algorytm.wyjatki.PodgladanieKartyException;

/**
 * Klasa reprezentująca obraz karty. Zawiera informacje o kolorze i wartości karty
 * albo też informację, że karta jest zakryta. Nie zawiera referencji do samej karty
 * (żeby nie dało się jej w żaden sposób modyfikować lub podglądać).
 * @author Michał Wróbel
 */
public class ObrazKarty {
    
    /** 
     * Zawiera referencję do wartości karty w przypadku karty widocznej albo
     * null w przypadku karty niewidocznej.
     */
    private WartoscKarty karta;
    
    /**
     * Tworzy obraz zakrytej karty.
     */
    public ObrazKarty() {
        karta = null;
    }
    
    /**
     * Tworzy obraz odkrytej karty.
     * @param karta Karta, której obraz ma być utworzony.
     * @param wlasciciel True, jeśli obraz karty jest tworzony z perspektywy jej posiadacza.
     */
    public ObrazKarty(Karta karta, boolean wlasciciel) {
        if (karta.jestCalkiemZakryta() || ((karta.jestZakryta() && !wlasciciel)))
            this.karta = null;
        else
            this.karta = karta.karta;
    }
    
    public boolean jestWidoczna() {
        if (karta == null) 
            return false;
        else
            return true;
    }
    
    public WartoscKarty odczytajWartosc () throws PodgladanieKartyException {
        if (karta == null) 
            throw new PodgladanieKartyException();
        else
            return karta;
    }
    
    public boolean jestWiekszaOd(ObrazKarty drugaKarta) throws PodgladanieKartyException {
        return odczytajWartosc().jestWiekszaOd(drugaKarta.odczytajWartosc());
    }
    
    @Override
    public String toString() {
        if (karta == null) 
            return "XX";
        else
            return karta.toString();
    }
    
}
