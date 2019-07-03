package algorytm;

import java.util.LinkedList;
import java.util.Random;

/**
 * Reprezentuje talię kart.
 * Karty w talii domyślnie są całkowicie zakryte.
 * @author Michał Wróbel
 */
public class Talia {
    
    private LinkedList<Karta> stos;
    
    public Talia (WartoscKarty.Wartosc odKarty) {
        
        stos = new LinkedList<>();
        
        for(WartoscKarty.Wartosc wartosc : WartoscKarty.Wartosc.values()) {
            
            if (wartosc.compareTo(odKarty) >= 0) {
                for (WartoscKarty.Kolor kolor : WartoscKarty.Kolor.values()) {
                    stos.add(new Karta(new WartoscKarty(wartosc, kolor), Karta.Widocznosc.CALKIEM_ZAKRYTA));
                }
            }
            
        }        
    }
    
    public ObrazKarty obrazWierzchu() {
        ObrazKarty obraz = null;
        if (stos.size() > 0)
            obraz = new ObrazKarty(stos.getLast(), false);
        return obraz;
    }
    
    public int ilosc () {
        return stos.size();
    }
    
    public void potasuj () {
        
        LinkedList<Karta> nowyStos = new LinkedList<>();
        Random r = new Random();
        
        for (Karta k : stos) {
            nowyStos.add(r.nextInt(nowyStos.size()+1), k);
        }
        
        stos = nowyStos;
    }

    /**
     * @return Kartę z wierzchu lub null, jeśli stos jest pusty. 
     */
    public Karta pobierzKarte() {
        return stos.pollLast();
    }
    
    /**
     * Odkłada kartę na wierzch stosu (tak, że zostanie zdjęta jako pierwsza).
     * @param karta 
     */
    public void odlorzKarteNaWierzch(Karta karta) {
        karta.zakryjCalkiem();
        stos.addLast(karta);
    }
    
    /**
     * Odkłada kartę na spód stosu (tak, że zostanie zdjęta jako ostatnia).
     * @param karta 
     */
    public void odlorzKarteNaSpod(Karta karta) {
        karta.zakryjCalkiem();
        stos.addFirst(karta);
    }
    
    @Override
    public String toString() {
        StringBuilder wynik = new StringBuilder();
        
        for(Karta k : stos) {
            wynik.append(k.toString());
            wynik.append('\n');
        }
        
        return wynik.toString();
    }
    
}
