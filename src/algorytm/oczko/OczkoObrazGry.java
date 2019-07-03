package algorytm.oczko;

import algorytm.ObrazKarty;
import java.util.LinkedList;

/**
 * Reprezentacja obecnego stanu rozgrywki w oczko.
 * @author Michał Wróbel
 */
public class OczkoObrazGry {
    
    /** Indeks gracza, przy którym obecnie odbywa się rozgrywka. */
    public short zeton;    
    /** Decyzja, która musi być podjęta w imieniu gracza, na którego wzkazuje żeton. */
    public OczkoTypDecyzji wymaganaDecyzja;
    
    /** Przybliżony rozmiar talii leżącej na stole (wyrażony w dziesiątkach kart). */
    public int przyblizonyRozmiarTalii;
    /** Obraz karty znajdującej się na wierzchu talii (null w przypadku talii pustej). */
    public ObrazKarty kartaNaWierzchuTalii;
    /** Przybliżony rozmiar stosika odrzuconych kart (wyrażony w dziesiątkach kart). */
    public int przyblizonyRozmiarOdrzuconych;
    /** Obraz karty znajdującej się na wierzchu stosika odrzuconych kart (null w przypadku pustego stosu). */
    public ObrazKarty kartaNaWierzchuOdrzuconych;
    
    /** Lista czynności wykonanych od poprzedniego kroku. */
    public LinkedList<OczkoCzynnosc> czynnosci;
    /** Obraz stołu widziany przez gracza będącego kierownikiem rozgrywki. */
    public OczkoObrazSpersonalizowany obrazStolu;
    /** Lista obrazów dla pozostałych graczy rzeczywistych (nie botów). */
    public LinkedList<OczkoObrazSpersonalizowany> obrazyDlaPozostalych;
    
    /** Konstruktor. */
    public OczkoObrazGry() {
        czynnosci = new LinkedList<>();
        obrazyDlaPozostalych = new LinkedList<>();
    }   
       
}
