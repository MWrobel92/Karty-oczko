package algorytm.wyjatki;

import algorytm.StanGry;

/**
 * Wyjątek rzucany w przyopadku wywołania funkcji niedostępnej w danej fazie gry.
 * @author Michał Wróbel
 */
public class NiewlasciwyStanGryException extends ZasadyGryException {
    
    public StanGry oczekiwany;
    public StanGry faktyczny;
    
    /**
     * Konstruktor.
     * @param oczekiwanyStan Stan, w którym funkcja byłaby wywołana prawidłowo.
     * @param faktycznyStan Stan, który był zastany.
     */
    public NiewlasciwyStanGryException (StanGry oczekiwanyStan, StanGry faktycznyStan) {
        oczekiwany = oczekiwanyStan;
        faktyczny = faktycznyStan;
    }
    
}
