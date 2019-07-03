package algorytm.oczko;

import algorytm.ObrazKarty;
import algorytm.wyjatki.NieWidzeKartException;
import algorytm.wyjatki.PodgladanieKartyException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Klasa podejmująca decyzje za pewnego gracza w oczko.
 * @author Michał Wróbel
 */
public class OczkoJednostkaDecyzyjna {
    
    private Random r;
    
    public OczkoJednostkaDecyzyjna() {
        r = new Random();
    }
    
    public OczkoDecyzja podejmijDecyzje(OczkoObrazSpersonalizowany stanGry) throws NieWidzeKartException {  
        
        OczkoDecyzja decyzja = new OczkoDecyzja();
                
        OczkoObrazGracza mojStan = stanGry.gracze.get(stanGry.indeks);
        ArrayList<ObrazKarty> mojeKarty = mojStan.karty;
        
        int mojWynik = 0;
        for (ObrazKarty k : mojeKarty) {
            try {
                mojWynik += k.odczytajWartosc().punktyOczko();
            } catch (PodgladanieKartyException ex) {
                throw new NieWidzeKartException();
            }
        }
        
        // Próg ma wynosić od 16 do 19
        int prog = 16 + r.nextInt(3);
                
        decyzja.odpowiedz = (mojWynik < prog);
        return decyzja;
        
    }
    
}
