package algorytm.oczko;

import java.util.LinkedList;
import java.util.List;

/**
 * Klasa pomocnicza służąca do rozstrzygania, ilu graczy jest w grze i kto jest zwycięzcą.
 * @author Michał Wróbel
 */
public class OczkoObserwatorGraczy {
    
    /** Kompletna lista graczy. */
    private LinkedList<OczkoGracz> listaGraczy;
    /** Lista indeksów graczy, którzy nie mają fury. */
    private LinkedList<Integer> indeksyGraczyWGrze;
    
    public OczkoObserwatorGraczy(LinkedList<OczkoGracz> gracze) {
        listaGraczy = gracze;
        wstepniePrzygotuj();
    }
    
    private int wstepniePrzygotuj() {
        
        int wynik = 0;
        indeksyGraczyWGrze = new LinkedList<>();
        
        int i = 0;
        for (OczkoGracz o : listaGraczy) {
            if (!o.maFure()) {
                indeksyGraczyWGrze.add(new Integer(i));
                ++wynik;
            }
            ++i;
        }
        
        return wynik;
        
    }
    
    public int liczbaGraczyWGrze() {
        return indeksyGraczyWGrze.size();
    }
    /**
    * Sprawdza rezultat rozgrywki. Zwraca indeks wygranego gracza lub -1 w przypadku remisu.
    * @return Listę indeksów graczy z wyłączeniem graczy z furą, posortowanych od najlepszego do najgorszego.
    */
    public short podajIndeksZwyciezcy () {
        
        short indeksZwyciezcy = -1;
        
        if (indeksyGraczyWGrze.size() == 1) {
            indeksZwyciezcy = indeksyGraczyWGrze.get(0).shortValue();
        }
        else {
            
            int liczbaPunktowZwyciezcy = 0;
            
            for (Integer in : indeksyGraczyWGrze) {
                
                int punktyGracza = listaGraczy.get(in.intValue()).policzPunkty();
                if (punktyGracza > liczbaPunktowZwyciezcy) {
                    liczbaPunktowZwyciezcy = punktyGracza;
                    indeksZwyciezcy = in.shortValue();
                }
                else if (punktyGracza == liczbaPunktowZwyciezcy) {
                    indeksZwyciezcy = -1; // Remis
                }
            }
        }
        
        return indeksZwyciezcy;
    }
}
