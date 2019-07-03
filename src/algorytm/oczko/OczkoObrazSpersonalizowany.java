/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorytm.oczko;

import java.util.LinkedList;

/**
 * Obraz stołu widziany z perspektywy konkretnego gracza.
 * @author Michał Wróbel
 */
public class OczkoObrazSpersonalizowany {
    
    /** Indeks gracza, z którego perspektywy widziany jest obraz. */
    public short indeks;
    /** Lista obrazów poszczególnych graczy. */
    public LinkedList<OczkoObrazGracza> gracze;
    
    /** Konstruktor. */
    public OczkoObrazSpersonalizowany() {
        gracze = new LinkedList<>();
    }
    
}
