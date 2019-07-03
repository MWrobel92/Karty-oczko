package algorytm.oczko;

/**
 * Klasa opisująca czynność wykonaną w danym kroku.
 * @author Michal
 */
public class OczkoCzynnosc {
    
    public enum TypCzynnosci {
        /** Wartość "awaryjna", podkreąślająca ewentualny brak jakiejkolwiek czynności. */
        BRAK,
        /** Zapytanie się gracza, czy jest botem. */
        PYTANIE,
        /** Wydanie karty graczowi. */
        ROZDANIE,
        /** Zakończenie ciągnięcia kart przez gracza. */
        SPASOWANIE,
        /** Zakończenie się rozdania. */
        KONIEC_ROZDANIA
    }
    
    /** Typ czynności. */
    public TypCzynnosci typ;
    /** Indeks gracza, którego to dotyczy. */
    public short indeksGracza;
    
    public OczkoCzynnosc (TypCzynnosci typ, short indeksGracza) {
        this.typ = typ;
        this.indeksGracza = indeksGracza;
    }
    
}
