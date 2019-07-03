/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfejs;

import algorytm.ObrazKarty;
import algorytm.oczko.OczkoCzynnosc;
import algorytm.oczko.OczkoDecyzja;
import algorytm.oczko.OczkoKontroler;
import algorytm.oczko.OczkoObrazGracza;
import algorytm.oczko.OczkoObrazGry;
import algorytm.oczko.OczkoObrazSpersonalizowany;
import algorytm.oczko.OczkoTypDecyzji;
import algorytm.wyjatki.ZasadyGryException;
import interfejs.jezyki.Jezyk;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Michal
 */
public class KontrolkaWidoku extends JPanel implements ActionListener {
       
    private OczkoKontroler kontroler;
    private Jezyk jezyk;
    private UstawieniaGry ug;
    
    private OczkoObrazGry obrazGry;
    private OczkoObrazSpersonalizowany obrazSpersonalizowany;
    
    private GeneratorKart gk;
    private Exception wyjatek;
    
    private JButton przyciskTak;
    private JButton przyciskNie;
    private JButton przyciskKontynuuj;
    
    private LinkedList<OczkoCzynnosc> ciekaweZdarzenia;
    private short zeton;
    private boolean zmianaSterowania;
    
    /** Konstruktor */
    public KontrolkaWidoku(Jezyk j, UstawieniaGry ug) {
        
        try {
            gk = new GeneratorKart();
            wyjatek = null;
            obrazGry = null;
            jezyk = j;
            this.ug = ug;
            utworzPrzyciski();
        } catch (IOException ex) {
            this.wyjatek = ex;
        }
        
    }
    
    public void rozpocznijGre() {
        
        kontroler = new OczkoKontroler();
        try {   
                       
            kontroler.dodajGracza(jezyk.bankier, true);
            
            switch (ug.liczbaGraczy) {
                case G1B1:
                    zeton = 1;
                    kontroler.dodajGracza(ug.nazwaGracza1, false);
                    break;
                case G1B2:
                    zeton = 2;
                    kontroler.dodajGracza(ug.nazwaGracza2, true);
                    kontroler.dodajGracza(ug.nazwaGracza1, false);
                    break;
                case G2B1:
                    zeton = 1;
                    kontroler.dodajGracza(ug.nazwaGracza1, false);
                    kontroler.dodajGracza(ug.nazwaGracza2, false);
                    break;
            }
            
            kontroler.rozpocznijGre();
        } catch (ZasadyGryException ex) {
            zglosWyjatek(ex);
        }
    }
        
    /**
     * Nasłuchiwanie nadchodzących zdarzeń.
     * @param e 
     */
    @Override
    public void actionPerformed (ActionEvent e) {
        
        OczkoDecyzja decyzja = new OczkoDecyzja();                
        
        switch (e.getActionCommand()) {
            
            case "Tak":
                decyzja.odpowiedz = decyzja.dodatkowaOdpowiedz = true;
                zrobKrok(decyzja);
                break;
            case "Nie":
                decyzja.odpowiedz = decyzja.dodatkowaOdpowiedz = false;
                zrobKrok(decyzja);
                break;
            case "Kontynuuj":
                kontynuuj();
                break;
            }
                
    };
    
    private void kontynuuj() {
        if (zmianaSterowania) {
            zmianaSterowania = false;
            analizujObrazGry();
            this.repaint();
        } else {
            zrobKrok(new OczkoDecyzja());
        }
    }
    
    /**
     * Funkcja ustawia przyciski w układzie tak/nie.
     */
    private void ustawPrzyciskiTakNie() {
        
            przyciskTak.setVisible(true);
            przyciskNie.setVisible(true);
            przyciskKontynuuj.setVisible(false);
    }
    
    /**
     * Funkcja ustawia przyciski w układzie kontynuuj.
     */
    private void ustawPrzyciskiDalej() {
        
            przyciskTak.setVisible(false);
            przyciskNie.setVisible(false);
            przyciskKontynuuj.setVisible(true);
    }
    
    private void okrojListeZdarzen () {
        
        ciekaweZdarzenia = new LinkedList<>();
        
        for(OczkoCzynnosc oc : obrazGry.czynnosci) {
            
            if ( (oc.typ == OczkoCzynnosc.TypCzynnosci.ROZDANIE) || 
                 (oc.typ == OczkoCzynnosc.TypCzynnosci.KONIEC_ROZDANIA) ) {
                ciekaweZdarzenia.add(oc);
            }
            
        }
        
    }
    
    private boolean analizujObrazGry() {
        
        boolean rezultat = true;        
        OczkoObrazGry s = obrazGry; // Skrócenie nazwy
        
        if (zmianaSterowania) {
            ustawPrzyciskiDalej();
        }
        else if (s.wymaganaDecyzja == OczkoTypDecyzji.BRAK) {
            
            // Sprawdź, czy wydarzyło się cokolwiek godnego uwagi
            if (ciekaweZdarzenia.size() > 0) {
                ustawPrzyciskiDalej();
            }
            else {
                rezultat = false;
            }
            
        }
        else {
            ustawPrzyciskiTakNie();
        }
        
        return rezultat;
        
    }
   
    public void zrobKrok(OczkoDecyzja decyzja) {
        
        
        boolean kontynuuj;
                    
        do {
            
            try {
                przekazStanGry(kontroler.zrobKrok(decyzja));
                okrojListeZdarzen();
            } catch (ZasadyGryException ex) {
                zglosWyjatek(ex);
                break;
            }
            
            // Jeśli nie ma nic godnego pokazania, wykonaj kolejny krok
            kontynuuj = !analizujObrazGry();
            
        } while (kontynuuj);
                   
        this.repaint();
        
    };
    
    private void utworzPrzyciski() {
        
        przyciskTak = new JButton();
        przyciskNie = new JButton();
        przyciskKontynuuj = new JButton();
        przyciskTak.setActionCommand("Tak");
        przyciskNie.setActionCommand("Nie");
        przyciskKontynuuj.setActionCommand("Kontynuuj");
        przyciskTak.addActionListener(this);        
        przyciskNie.addActionListener(this);
        przyciskKontynuuj.addActionListener(this);        
        ustawPolozeniePrzyciskow();
        setLayout(null);
        add(przyciskTak);
        add(przyciskNie);
        add(przyciskKontynuuj);
        ustawEtykiety();
    }
    
    private void ustawPolozeniePrzyciskow() {
        
        Insets insets = getInsets();
        int wys = insets.top + getHeight()/2;
        int sze = insets.left + getWidth();
        
        przyciskTak.setBounds(sze-200, wys, 75, 30);
        przyciskNie.setBounds(sze-100, wys, 75, 30);
        przyciskKontynuuj.setBounds(sze-150, wys, 100, 30);
    }
    
    public void ustawEtykiety() {
        przyciskTak.setText(jezyk.tak);
        przyciskNie.setText(jezyk.nie);
        przyciskKontynuuj.setText(jezyk.kontynuuj);
    }
    
    public void zglosWyjatek(Exception ex) {
        this.wyjatek = ex;
    }
    
    public void przekazStanGry(OczkoObrazGry stan) {
        
        obrazGry = stan;
        
        // Wyłuskanie przydatnych indeksów
        short kierownik = stan.obrazStolu.indeks;
        short nowyZeton = stan.zeton;
        
        // Podjęcie decyzji dotyczących zmiany osoby sterującej
        zmianaSterowania = false;
        
        if (zeton != nowyZeton) {
            
            boolean staryToKierownik = (zeton == kierownik);
            boolean nowyToBot = stan.obrazStolu.gracze.get(nowyZeton).jestBotem;
            boolean decyzjaDoPodjecia = (stan.wymaganaDecyzja != OczkoTypDecyzji.BRAK);

            // Analiza przesunięcia żetonu
            if (nowyToBot) {
                if (!staryToKierownik) {
                    zeton = kierownik;
                    zmianaSterowania = true; 
                }
            } else {
                if (!(staryToKierownik && !decyzjaDoPodjecia)) {
                    zeton = nowyZeton;
                    zmianaSterowania = true;
                }
            }
        }
        
        // Załadowanie poprawnego obrazu gry
        obrazSpersonalizowany = stan.obrazStolu;
        if (zeton != kierownik) {
            for (OczkoObrazSpersonalizowany os : stan.obrazyDlaPozostalych) {
                if (os.indeks == zeton) {
                    obrazSpersonalizowany = os;
                    break;
                }
            }
        }
    }
    
    /**
     * Funkcja odpowiadająca za rysowanie kontrolki
     * @param g 
     */
    @Override
    public void paint(Graphics g) {
        
        
        if (wyjatek != null) {
            
            // W grze wystąpił nieobsługiwany wyjątek - trzeba wyświetlić "ekran śmierci"
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.LIGHT_GRAY);
            g.drawString("BLUESCREEN", 10, 10);
            g.drawString(wyjatek.toString(), 10, 25);
            
        }
        else if (zmianaSterowania) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.drawString(jezyk.zmianaSterowania + obrazGry.obrazStolu.gracze.get(zeton).nazwa + '.',
                    getWidth()/2-100, getHeight()/2);
            
            // Narysowanie przycisków
            ustawPolozeniePrzyciskow();
            paintChildren(g);
        }
        else {
        
            // Malowanie stołu na zielono
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, getWidth(), getHeight());

            // Wywołanie właściwej funkcji rysującej
            if (obrazGry != null) {
                rysujStanGry(g);
            }
            
            // Narysowanie przycisków
            ustawPolozeniePrzyciskow();
            paintChildren(g);
       }   
    }
    
    private void rysujKarte(Graphics g, ObrazKarty karta, int x, int y) {
        
        try {
            g.drawImage(gk.narysujKarte(karta), x, y, this);
        }
        catch (Exception ex) {            
            this.wyjatek = ex;          
            repaint();
        }
        
    }
    
    private void rysujStanGry(Graphics g) {
        
        int ySrodek = getHeight() / 2;
        // Rysowanie graczy
        rysujGracza(g, obrazSpersonalizowany.gracze.get(0), 0,  15);
        if (obrazSpersonalizowany.gracze.size() == 2) {
            rysujGracza(g, obrazSpersonalizowany.gracze.get(1), 0, getHeight()-165);
        }
        else {
            rysujGracza(g, obrazSpersonalizowany.gracze.get(1), getWidth()-350,  50);
            rysujGracza(g, obrazSpersonalizowany.gracze.get(2), 0, getHeight()-165);
        }
        
        // Rysowanie talii
        rysujKupkeKart(g, obrazGry.przyblizonyRozmiarTalii, obrazGry.kartaNaWierzchuTalii, 25, ySrodek-75);
        rysujKupkeKart(g, obrazGry.przyblizonyRozmiarOdrzuconych, obrazGry.kartaNaWierzchuOdrzuconych, 175, ySrodek-75);
        
        // Rysowanie komunikatu
        rysujKomunikat(g, getWidth()-200 ,ySrodek-10);
            
    }
    
    private void rysujGracza(Graphics g, OczkoObrazGracza obraz, int x, int y) {
        
        g.setColor(Color.BLACK);        
        g.drawString(obraz.nazwa, x+10, y+20);
        String liczbaZwyciestw = jezyk.liczbaZwyciestw + obraz.liczbaZwyciestw;
        g.drawString(liczbaZwyciestw, x+10, y+40);
        
        if (obraz.maFure) {
            g.drawString(jezyk.fura, x+10, y+60);
        }
        
        int xx = x+150; // Pozycja y rysowanej karty
        for (ObrazKarty ok : obraz.karty) {
            rysujKarte(g, ok, xx, y);
            xx += 20;
        }
    }
    
    private void rysujKupkeKart(Graphics g, int szacowanaWysokosc, ObrazKarty naWierzchu, int x, int y) {
        
        --szacowanaWysokosc;
        
        for (int i=0; i<(szacowanaWysokosc); ++i) {
            rysujKarte(g, new ObrazKarty(), x+3*i, y);
        }
        
        if (naWierzchu != null) {
            rysujKarte(g, naWierzchu, x+3*szacowanaWysokosc, y);
        }
    }
    
    private void rysujKomunikat(Graphics g, int x, int y) {
        
        StringBuilder sb = new StringBuilder();
        
        OczkoCzynnosc ostatniaCzynnosc;        
        try {
            ostatniaCzynnosc = obrazGry.czynnosci.getLast();
        }
        catch (NoSuchElementException ex) {
            ostatniaCzynnosc = new OczkoCzynnosc(OczkoCzynnosc.TypCzynnosci.BRAK, (short)-1);
        }
        
        if (obrazGry.wymaganaDecyzja == OczkoTypDecyzji.POBRANIE) {
            sb.append(jezyk.pytanieOKarte);            
        }
        else {
            switch (ostatniaCzynnosc.typ) {
                case ROZDANIE:
                    sb.append(obrazSpersonalizowany.gracze.get(ostatniaCzynnosc.indeksGracza).nazwa);
                    sb.append(jezyk.otrzymalKarte);
                    break;
                case KONIEC_ROZDANIA:
                    if (ostatniaCzynnosc.indeksGracza < 0) {
                        sb.append(jezyk.remis);
                    } else {
                        sb.append(jezyk.koniecRozdania);
                        sb.append(obrazSpersonalizowany.gracze.get(ostatniaCzynnosc.indeksGracza).nazwa);
                    }
                    break;
            }
        }
        
        g.setColor(Color.BLACK);
        g.drawString(sb.toString(), x, y);
        
    }

    public void przelaczKoszulke() {
        gk.przelaczKoszulke();
        repaint();
    }
}
