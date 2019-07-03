package algorytm.oczko;

/**
 * Typ wyliczeniowy informujący, w jakiej fazie interakcji z graczem znajduje się algorytm.
 * @author Michał Wróbel
 */
public enum OczkoFazaOdpytania {
    
    /** Pytanie gracza, kto ma podjąc za niego decyzję (własna jednostka decyzyjna, czy interfejs). */
    PYTANIE_O_TYP,
    /** Wykonanie operacji żądanej przez gracza. */
    DECYZJA
  
}
