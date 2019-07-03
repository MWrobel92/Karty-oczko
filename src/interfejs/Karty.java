package interfejs;

import algorytm.ObrazKarty;
import algorytm.oczko.OczkoCzynnosc;
import algorytm.oczko.OczkoDecyzja;
import algorytm.oczko.OczkoKontroler;
import algorytm.oczko.OczkoObrazGracza;
import algorytm.oczko.OczkoObrazGry;
import algorytm.wyjatki.NiewlasciwyStanGryException;
import algorytm.wyjatki.PodgladanieKartyException;
import algorytm.wyjatki.ZasadyGryException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author Michał Wróbel
 */
public class Karty {

    public static String drukujStanGry(OczkoObrazGry stan) throws PodgladanieKartyException {
        StringBuilder sb = new StringBuilder();
        
        // Wypisywanie talii itp.
        sb.append("Talia: ");
        for (int i=0; i<stan.przyblizonyRozmiarTalii; ++i) {
            sb.append('[');
        }
        if (stan.kartaNaWierzchuTalii != null) {
            sb.append(stan.kartaNaWierzchuTalii.toString());
            sb.append(']');
        }
        sb.append('\n');
        sb.append("Odrzucone: ");
        for (int i=0; i<stan.przyblizonyRozmiarOdrzuconych; ++i) {
            sb.append('[');
        }
        if (stan.kartaNaWierzchuOdrzuconych != null) {
            sb.append(stan.kartaNaWierzchuOdrzuconych.toString());
            sb.append(']');
        }
        sb.append('\n');
        
        // Wypisanie graczy
        for(OczkoObrazGracza g : stan.obrazStolu.gracze) {
            sb.append(g.nazwa);
            sb.append(": ");
            for(ObrazKarty k : g.karty) {
                sb.append('[');
                sb.append(k.toString());
                sb.append("] ");
            }
            if (g.maFure) {
                sb.append("- FURA");
            }
            sb.append(" (liczba zwycięstw: ");
            sb.append(g.liczbaZwyciestw);
            sb.append(")\n");
        }
        
        // Wypisanie ostatniego kroku
        OczkoCzynnosc ostatniaCzynnosc;        
        try {
            ostatniaCzynnosc = stan.czynnosci.getLast();
        }
        catch (NoSuchElementException ex) {
            ostatniaCzynnosc = new OczkoCzynnosc(OczkoCzynnosc.TypCzynnosci.BRAK, (short)-1);
        }
        switch (ostatniaCzynnosc.typ) {
            case PYTANIE:
                sb.append(stan.obrazStolu.gracze.get(ostatniaCzynnosc.indeksGracza).nazwa);
                sb.append(" został odpytany na temat bycia botem.\n");
                break;
            case ROZDANIE:
                sb.append(stan.obrazStolu.gracze.get(ostatniaCzynnosc.indeksGracza).nazwa);
                sb.append(" otrzymał kartę.\n");
                break;
            case SPASOWANIE:
                sb.append(stan.obrazStolu.gracze.get(ostatniaCzynnosc.indeksGracza).nazwa);
                sb.append(" zakończył ciągnięcie kart.\n");
                break;
            case KONIEC_ROZDANIA:
                sb.append("Koniec rozgrywki - zwyciężył ");
                sb.append(stan.obrazStolu.gracze.get(ostatniaCzynnosc.indeksGracza).nazwa);
                sb.append(".\n");
                break;
        }
        
        // Wypisanie żądania.
        switch (stan.wymaganaDecyzja) {
            case BRAK:
                sb.append("(brak decyzji do podjęcia) ");
                break;
            case POBRANIE:
                sb.append("Czy chcesz ciągnąć kolejną kartę? ");
                break;
        }
        
        return sb.toString();
    } 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ZasadyGryException {
        
        Scanner wejscie = new Scanner(System.in);
        
        OczkoKontroler gra = new OczkoKontroler();
        gra.standardowaKonfiguracja("Bankier", "Gracz");
        
        String nowaLinia;
        char znak = 'x'; 
        while (znak != 'q') {
            
            OczkoDecyzja decyzja = new OczkoDecyzja();
            if ((znak == 't') || (znak == 'T'))
                decyzja.odpowiedz = true;
            
            
            OczkoObrazGry stanGry;
            try {
                stanGry = gra.zrobKrok(decyzja);
                
                System.out.print(drukujStanGry(stanGry));
                
                nowaLinia = wejscie.nextLine();
                if (nowaLinia.isEmpty())
                    znak = 'x';
                else
                    znak = nowaLinia.charAt(0);
            
            } catch (ZasadyGryException ex) {
                
                System.out.print("BLUESCREEN!!!");
                znak = 'q';
            }
            
        }

    }
}
