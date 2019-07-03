package algorytm;

/**
 * Typ wyliczeniowy pokazujący, w której fazie znajduje się rozgrywka.
 * @author Michał Wróbel
 */

public enum StanGry {
    /** Świeżo po utworzeniu/zresetowaniu kontrolera. */
    WYZEROWANA,
    /** W fazie tworzenia graczy i tasowania talii. */
    PRZYGOTOWYWANIE,
    /** Rozdawanie kart (przechodzenie po graczach nie wymagające od nich żadnych decyzji). */ 
    ROZDAWANIE_KART,
    /** Gra rozpoczęta, można wykonywać kolejne ruchy. */
    W_TOKU,
    /** Zakończyło się jedno rozdanie. */
    KONIEC_ROZDANIA;
}
