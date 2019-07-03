package algorytm;

/**
 * Klasa reprezentująca wartość i kolor karty.
 * @author Michał Wróbel
 */
public class WartoscKarty {
    
    public enum Kolor {
        TREFL, PIK, KIER, KARO
    }
    
    public enum Wartosc {
        N2, N3, N4, N5, N6, N7, N8, N9, N10, J, Q, K, A
    }
    
    public final Wartosc wartosc;
    public final Kolor kolor;    
    
    public WartoscKarty(Wartosc wartosc, Kolor kolor) {
        this.wartosc = wartosc;
        this.kolor = kolor;
    }
    
    public boolean jestWiekszaOd(WartoscKarty drugaKarta) {
        if (wartosc.compareTo(drugaKarta.wartosc) > 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public int punktyOczko() {
        
        switch (wartosc) {
            case N2:
            case J:
                return 2;
            case N3:
            case Q:
                return 3;
            case N4:
            case K:
                return 4;
            case N5:
                return 5;
            case N6:
                return 6;
            case N7:
                return 7;
            case N8:
                return 8;
            case N9:
                return 9;
            case N10:
                return 10;
            default: // Został tylko As
                return 11;
        }
        
    }
    
    @Override
    public String toString () {
        StringBuilder wynik = new StringBuilder(wartosc.toString());
        if (wynik.charAt(0) == 'N') {
            wynik.deleteCharAt(0);
        }
        switch (kolor) {
            case TREFL:
                wynik.append('T');
                break;
            case PIK:
                wynik.append('P');
                break;
            case KIER:
                wynik.append('K');
                break;
            case KARO:
                wynik.append('C');
                break;
        }
        return wynik.toString();
    }
   
}
