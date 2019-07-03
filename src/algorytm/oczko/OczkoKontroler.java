package algorytm.oczko;

import algorytm.Karta;
import algorytm.ObrazKarty;
import algorytm.StanGry;
import algorytm.Talia;
import algorytm.WartoscKarty;
import algorytm.wyjatki.NieWidzeKartException;
import algorytm.wyjatki.NieprawidlowaKonfiguracjaException;
import algorytm.wyjatki.NiewlasciwyStanGryException;
import algorytm.wyjatki.ZasadyGryException;
import java.util.LinkedList;

/**
 * Klasa kontrolująca całą rozgrywkę podczas gry w oczko.
 * @author Michał Wróbel
 */
public class OczkoKontroler {
    
    /** Lista wszystkich graczy */
    private LinkedList<OczkoGracz> gracze;
    
    private StanGry stanGry;
    private Talia talia;
    private LinkedList<Karta> kartyOdrzucone; 
    
    /** Indeks gracza, z którego punktu widzenia widziana jest rozgrywka. */
    private short kierownik;
    
    /** Indeks aktualnie obslugiwanego gracza */
    private short zeton;
    /** Ile jeszcze kart trzeba rozdac graczom */
    private short iloscKartDoRozdania;
    /** Indeks gracza, który wygrał rozdanie. */
    private short indeksZwyciezcy;
    
    /** Faza pytania. */
    private OczkoFazaOdpytania faza;
    /** Ostatnia decyzja pochodząca z jednostki decyzyjnej gracza. */
    private OczkoDecyzja ostatniaDecyzjaGracza;
    /** Decyzja, która będzie wymagana od interfejsu w kolejnym, ruchu. */
    private OczkoTypDecyzji kolejnaDecyzja;
    /** Czynności wykonane podczas tego ktoku. */
    private LinkedList<OczkoCzynnosc> ostatnieCzynnosci;
    
    /** Przygotowuje obraz gry dla głównego gracza i innych graczy rzeczywistych. */
    private OczkoObrazGry przygotujObrazGry() {
        
        OczkoObrazGry obraz = new OczkoObrazGry();
        
        // Dane podstawowe
        obraz.zeton = zeton;
        obraz.wymaganaDecyzja = kolejnaDecyzja;
        
        // Przybliżone dane dotyczące talii i stosiku odrzuconych
        obraz.przyblizonyRozmiarTalii = (talia.ilosc() +9) / 10; // +9 - żeby dla 1 karty wyszło 1, a dla zera zero
        obraz.przyblizonyRozmiarOdrzuconych = (kartyOdrzucone.size() +9) / 10;
        
        obraz.kartaNaWierzchuTalii = talia.obrazWierzchu();
        
        if (kartyOdrzucone.size() > 0)
            obraz.kartaNaWierzchuOdrzuconych = new ObrazKarty(kartyOdrzucone.getLast(), false);
        else
            obraz.kartaNaWierzchuOdrzuconych = null;
        
        // Przekazanie spisu czynności
        obraz.czynnosci = ostatnieCzynnosci;
        
        // Utworzenie obrazu spersonalizowanego dla kierownika rozgrywki
        obraz.obrazStolu = przygotujObrazSpersonalizowany(kierownik);
        
        // Utworzenie obrazów spersonalizowanych dla pozostałych graczy rzeczywistych
        for (short i=0; i<gracze.size(); ++i) {
            if ((i != kierownik) && (gracze.get(i).jednostkaDecyzyjna == null))
                obraz.obrazyDlaPozostalych.add(przygotujObrazSpersonalizowany(i));
        }
        return obraz;
        
    }
    /**
     * Przygotowuje obraz stołu gry z punktu widzenia konkretnego gracza.
     * @param indeksGracza Indeks gracza, dla którego przygotowywany jest obraz.
     * @return 
     */
    private OczkoObrazSpersonalizowany przygotujObrazSpersonalizowany(short indeksGracza) {
        
        // POdstawowe czynności
        OczkoObrazSpersonalizowany obraz = new OczkoObrazSpersonalizowany();
        obraz.indeks = indeksGracza;
        
        // Zrobienie obrazów wszystkich graczy z punktu widzenia żądanego gracza
        for (int i = 0; i < gracze.size(); ++i) {
            obraz.gracze.add(new OczkoObrazGracza(gracze.get(i), (i == indeksGracza)));
        }
        
        return obraz;
    }
    
    /**
     * Zeruje kontroler - tworzy pustą listę graczy i nową, niepotasowaną talię.
     */
    private void reset() {
        gracze = new LinkedList<>();
        talia = new Talia(WartoscKarty.Wartosc.N2);
        kartyOdrzucone = new LinkedList<>();
        stanGry = StanGry.WYZEROWANA;
        kolejnaDecyzja = OczkoTypDecyzji.BRAK;
        kierownik = -1;
    }
    
    /**
     * Funkcja wykonująca jeden ruch (takt) gry.
     * @param decyzja Obiekt decyzji wygenerowany przez odpytanego gracza.
     * @return Obraz stanu gry po wykonanym takcie.
     * @throws NiewlasciwyStanGryException Jeśli w danym stanie nie jest możliwe wykonanie taktu.
     */
    public OczkoObrazGry zrobKrok (OczkoDecyzja decyzja) throws ZasadyGryException {
        
        ostatniaDecyzjaGracza = decyzja;
        ostatnieCzynnosci = new LinkedList<>();
                
        switch (stanGry) {
            case ROZDAWANIE_KART:
                rozdajKarte();
                break;
            case W_TOKU:
                wykonajKrok();
                break;
            case KONIEC_ROZDANIA:
                przygotujKolejneRozdanie();
                break;
                
        }
        
        OczkoObrazGry obraz = przygotujObrazGry();
        obraz.wymaganaDecyzja = kolejnaDecyzja;
                
        return obraz;        
    }
    
    public void dodajGracza(String nazwaGracza, boolean jestBotem) throws NiewlasciwyStanGryException {
        
        // Sprawdzenie, czy funkcja została wywołana w odpowiednim momencie
        if (stanGry == StanGry.WYZEROWANA) {
            stanGry = StanGry.PRZYGOTOWYWANIE;
        }
        else if (stanGry != StanGry.PRZYGOTOWYWANIE) {
            throw new NiewlasciwyStanGryException(StanGry.PRZYGOTOWYWANIE, stanGry);
        }
        
        // Dodanie gracza
        OczkoGracz nowyGracz = new OczkoGracz(jestBotem);
        nowyGracz.ustawNazwe(nazwaGracza);
        gracze.add(nowyGracz);
        
        // Jeśli nie ma gracza decydującego, dodaj jego indeks
        if ((!jestBotem) && (kierownik < 0)) {
            kierownik = (short)(gracze.size() - 1);
        }
        
    }
    
    public void rozpocznijGre() throws ZasadyGryException {
        
        if (stanGry != StanGry.PRZYGOTOWYWANIE) {
            throw new NiewlasciwyStanGryException(StanGry.PRZYGOTOWYWANIE, stanGry);
        }
        
        if ((gracze.size() < 2) || (kierownik < 0)) {
            throw new NieprawidlowaKonfiguracjaException();
        }
        
        talia.potasuj();
        przygotujKolejneRozdanie();
    }
    /**
     * Przygotowuje standardową rozgrywkę jednego gracza z bankierem.
     * Aby użycie tej funkcji było możliwe, kontroler musi być świeżo utworzony
     * lub wyzerowany przy użyciu funkcji reset();
     * throws NiewlasciwyStanGryException W przypadku stanu gry innym niż WYZEROWANA.
     */
    public void standardowaKonfiguracja(String nazwaBankiera, String nazwaGracza) throws ZasadyGryException {
        
        if (stanGry != StanGry.WYZEROWANA)
            throw new NiewlasciwyStanGryException(StanGry.WYZEROWANA, stanGry);
        
        dodajGracza(nazwaBankiera, true);
        dodajGracza(nazwaGracza, false);
        rozpocznijGre();        
    }
    
    /** Funkcja wykonywana w trakcie interakcji z graczem */
    private void wykonajKrok() throws ZasadyGryException {
        
        switch(faza) {
            case PYTANIE_O_TYP:
                wykonajPytanie();
                break;
            case DECYZJA:
                wykonajDecyzje();
                break;
        }
        
    }
    
    public void rozdajKarte() throws NiewlasciwyStanGryException {
        
        if (stanGry != StanGry.ROZDAWANIE_KART)
            throw new NiewlasciwyStanGryException(StanGry.ROZDAWANIE_KART, stanGry);
        
        if (talia.ilosc() == 0) {
            przerzucStosik();
        }
        
        Karta karta = talia.pobierzKarte();
        gracze.get(zeton).dodajKarte(karta);
        
        ostatnieCzynnosci.add(new OczkoCzynnosc(OczkoCzynnosc.TypCzynnosci.ROZDANIE, zeton));
        
        przesunZeton();
        
        if(iloscKartDoRozdania > 1) {
            --iloscKartDoRozdania;
        }
        else {
            stanGry = StanGry.W_TOKU;
            zeton = 1;
            faza = OczkoFazaOdpytania.PYTANIE_O_TYP;
        }
    }
    
    public OczkoKontroler () {
        reset();
    }

    /**
     * Przesuwa żeton o jedno pole.
     * @return True, jeśli zakończyła się kolejka.
     */
    private boolean przesunZeton() {
        
        boolean wynik = false;
        zeton = ++zeton;
        
        if (zeton >= gracze.size()) {
            zeton = (short)(zeton%gracze.size());
            wynik = true;
        }
        
        return wynik;
    }

    private void wykonajPytanie() {
        
        OczkoJednostkaDecyzyjna jednostka = gracze.get(zeton).jednostkaDecyzyjna;
        
        if (jednostka == null) {            
            // Mamy gracza rzeczywistego - trzeba ustawić żądanie dla interfejsu
            kolejnaDecyzja = OczkoTypDecyzji.POBRANIE;            
        }
        else {            
            // Mamy bota - trzeba go zapytać o decyzję
            kolejnaDecyzja = OczkoTypDecyzji.BRAK;            
        }
        
        ostatnieCzynnosci.add(new OczkoCzynnosc(OczkoCzynnosc.TypCzynnosci.PYTANIE, zeton));
        faza = OczkoFazaOdpytania.DECYZJA;
    }

    private void wykonajDecyzje() throws NieWidzeKartException {
        
        OczkoJednostkaDecyzyjna jednostka = gracze.get(zeton).jednostkaDecyzyjna;
        OczkoDecyzja decyzja;
        
        if (jednostka == null) {            
            // Mamy gracza rzeczywistego - trzeba odczytać decyzję z obiektu
            decyzja = ostatniaDecyzjaGracza;            
        }
        else {            
            // Mamy bota - trzeba go zapytać o decyzję
            decyzja = jednostka.podejmijDecyzje(przygotujObrazSpersonalizowany(zeton));            
        }
        
        if (decyzja.odpowiedz == true) {
            
            // Gracz chce ciągnąc kolejną kartę
            wydajGraczowiKarte();
            
        }
        else {
            
            // Gracz nie chce ciągnąć kolejnej karty
            zakonczCiagniecieKart();
            
        }
    
    }

    private void wydajGraczowiKarte() {  
        
        OczkoGracz gracz = gracze.get(zeton);
        
        if (talia.ilosc() == 0) {
            przerzucStosik();
        }
        
        Karta karta = talia.pobierzKarte();
        karta.zakryj();
        
        gracz.dodajKarte(karta);
        
        
        if (gracz.maFure()) {
            
            // Porażka!            
            OczkoObserwatorGraczy obserwator = new OczkoObserwatorGraczy(gracze);
            
            if (obserwator.liczbaGraczyWGrze() == 1) {
                
                // Pozostał tylko jeden gracz - rozdanie jest zakończone
                zakonczKolejke(obserwator.podajIndeksZwyciezcy());

            }
            else {
                
                // Przejdź do kolejnego gracza
                zakonczCiagniecieKart();
            }                 
            
        }    
        else {
            
            // Gracz nie ma fury
            ostatnieCzynnosci.add(new OczkoCzynnosc(OczkoCzynnosc.TypCzynnosci.ROZDANIE, zeton));
            
        }
        
    }
    


    private void zakonczCiagniecieKart() {        
        
        
        if (zeton == 0) { // Bankier ciągnie ostatni                     
            OczkoObserwatorGraczy obserwator = new OczkoObserwatorGraczy(gracze);
            zakonczKolejke(obserwator.podajIndeksZwyciezcy());            
        }
        else {
            ostatnieCzynnosci.add(new OczkoCzynnosc(OczkoCzynnosc.TypCzynnosci.SPASOWANIE, zeton));
            przesunZeton();
            faza = OczkoFazaOdpytania.PYTANIE_O_TYP;
            kolejnaDecyzja = OczkoTypDecyzji.BRAK;
            
        }
        
    }
    
    private void zakonczKolejke(short zwyciezca) {
        
        for(OczkoGracz g : gracze) {
            g.odkryjKarty();
        }
        
        stanGry = StanGry.KONIEC_ROZDANIA;
        indeksZwyciezcy = zwyciezca;
        if (indeksZwyciezcy >= 0) {
            // Inkrementacja licznika zwycięstw
            ++(gracze.get(indeksZwyciezcy).liczbaZwyciestw);
        }
        ostatnieCzynnosci.add(new OczkoCzynnosc(OczkoCzynnosc.TypCzynnosci.KONIEC_ROZDANIA, indeksZwyciezcy));
        kolejnaDecyzja = OczkoTypDecyzji.BRAK;
    }

    private void przygotujKolejneRozdanie() {
        
        zrzucKarty();            
        zeton = 1;
        iloscKartDoRozdania = (short) (2 * gracze.size() - 1);    
        stanGry = StanGry.ROZDAWANIE_KART;
        kolejnaDecyzja = OczkoTypDecyzji.BRAK;
    }

    private void zrzucKarty() {
        
        for(OczkoGracz g : gracze) {
            
            LinkedList<Karta> kartyGracza = g.zabierzKarty();
            for (Karta k : kartyGracza) {
                k.odkryj();
                kartyOdrzucone.add(k);
            }
        }
    }
    
    private void przerzucStosik () {
        
        for(Karta k : kartyOdrzucone) {
            talia.odlorzKarteNaSpod(k);
        }
        kartyOdrzucone.clear();
        talia.potasuj();
    }
 
}
