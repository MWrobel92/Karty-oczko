package interfejs;

import algorytm.ObrazKarty;
import algorytm.WartoscKarty;
import algorytm.wyjatki.PodgladanieKartyException;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * Klasa generująca bitmapy z kartami do narysowania
 * @author Michał Wróbel
 */
public class GeneratorKart {
    
    
    private BufferedImage[] karty;
    private BufferedImage[] koszulki;
       
    private AffineTransformOp obrot180;
    private int wybranaKoszulka = 0;
    
    /** Konstruktor. */
    public GeneratorKart () throws IOException {
                       
        // Utworzenie obiektu rotacji.
        AffineTransform obrot180a = new AffineTransform();
        obrot180a.rotate(Math.PI);
        obrot180 = new AffineTransformOp(obrot180a, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        
        // Przygotowanie kart
        przygotujKarty();
        wybranaKoszulka = 0;
        
    }
    
    
    private void przygotujKarty () throws IOException {
        
        BitmapyPomocnicze bp = new BitmapyPomocnicze();
        
        // Przygotowanie wszystkich kart
        karty = new BufferedImage[52];
        
        for(WartoscKarty.Wartosc wartosc : WartoscKarty.Wartosc.values()) {            
            for (WartoscKarty.Kolor kolor : WartoscKarty.Kolor.values()) {
                    
                WartoscKarty wk = new WartoscKarty(wartosc, kolor);
                BufferedImage bitmapa = narysujKarte(bp, wk);
                karty[wyliczIndeksKarty(wk)] = bitmapa;
            }
        }
        
        // Przygotowanie koszulek 
        List<BufferedImage> tymczasowaTablica = bp.tablicaKoszulek();
        koszulki = new BufferedImage[tymczasowaTablica.size()];
        
        int i = 0;
        for(BufferedImage koszulka : tymczasowaTablica) {
            
            BufferedImage bitmapa = bp.pustaKarta();
            Graphics2D karta = bitmapa.createGraphics();
            karta.drawImage(koszulka, 5, 5, null);
            koszulki[i] = bitmapa;
            ++i;
        } 

    }
    
    private BufferedImage narysujKarte(BitmapyPomocnicze bp, WartoscKarty wk) throws IOException {
               
        // Załadowanie bitmap z obiektu
        BufferedImage bitmapa = bp.pustaKarta();
        Graphics2D karta = bitmapa.createGraphics();
        
        BufferedImage nominal = bp.nominal(wk); //
        BufferedImage symbolM = bp.symbolMaly(wk);
        BufferedImage symbolD = bp.symbolDuzy(wk);
        
        // Górny nominał
        karta.drawImage(nominal, 5, 5, null);
        karta.drawImage(symbolM, 5, 20, null);
        
        // Dolny nominał 
        karta.drawImage(symbolM, obrot180, 80+symbolM.getWidth(), 115+symbolM.getHeight());
        karta.drawImage(nominal, obrot180, 80+nominal.getWidth(), 130+nominal.getHeight());
        
        // Symbole
        
        if ( (wk.wartosc != WartoscKarty.Wartosc.N2) &&
             (wk.wartosc != WartoscKarty.Wartosc.N3) &&
             (wk.wartosc != WartoscKarty.Wartosc.A) ) {
            
            karta.drawImage(symbolD, 20, 20, null);
            karta.drawImage(symbolD, obrot180, 60+symbolD.getWidth(), 110+symbolD.getHeight());
        }
        
        if ( (wk.wartosc == WartoscKarty.Wartosc.N4) ||
             (wk.wartosc == WartoscKarty.Wartosc.N5) ||
             (wk.wartosc == WartoscKarty.Wartosc.N6) ||
             (wk.wartosc == WartoscKarty.Wartosc.N7) ||
             (wk.wartosc == WartoscKarty.Wartosc.N8) ||
             (wk.wartosc == WartoscKarty.Wartosc.N9) ||
             (wk.wartosc == WartoscKarty.Wartosc.N10) ) {
            
            karta.drawImage(symbolD, 60, 20, null);
            karta.drawImage(symbolD, obrot180, 20+symbolD.getWidth(), 110+symbolD.getHeight());
        }
        
        if ( (wk.wartosc == WartoscKarty.Wartosc.N6) ||
             (wk.wartosc == WartoscKarty.Wartosc.N7) ||
             (wk.wartosc == WartoscKarty.Wartosc.N8) ||
             (wk.wartosc == WartoscKarty.Wartosc.N9) ||
             (wk.wartosc == WartoscKarty.Wartosc.N10) ) {
            
            karta.drawImage(symbolD, 20, 50, null);
            karta.drawImage(symbolD, 60, 50, null);
        }
        
        if ( (wk.wartosc == WartoscKarty.Wartosc.N8) ||
             (wk.wartosc == WartoscKarty.Wartosc.N9) ||
             (wk.wartosc == WartoscKarty.Wartosc.N10) ) {
            
            karta.drawImage(symbolD, obrot180, 20+symbolD.getWidth(), 80+symbolD.getHeight());
            karta.drawImage(symbolD, obrot180, 60+symbolD.getWidth(), 80+symbolD.getHeight());
        }
        
        if ( (wk.wartosc == WartoscKarty.Wartosc.N2) ||
             (wk.wartosc == WartoscKarty.Wartosc.N3) ||
             (wk.wartosc == WartoscKarty.Wartosc.N9) ||
             (wk.wartosc == WartoscKarty.Wartosc.N10) ) {
            
            karta.drawImage(symbolD, 40, 30, null);
        }
        
        if ( (wk.wartosc == WartoscKarty.Wartosc.A) ||
             (wk.wartosc == WartoscKarty.Wartosc.N3) ||
             (wk.wartosc == WartoscKarty.Wartosc.N5) ) {
            
            karta.drawImage(symbolD, 40, 60, null);
        }
        
        if ( (wk.wartosc == WartoscKarty.Wartosc.N2) ||
             (wk.wartosc == WartoscKarty.Wartosc.N3) ||
             (wk.wartosc == WartoscKarty.Wartosc.N7) ||
             (wk.wartosc == WartoscKarty.Wartosc.N10) ) {
            
            karta.drawImage(symbolD, obrot180, 40+symbolD.getWidth(),  90+symbolD.getHeight());
        }
        
        // Popiersie
        if ( (wk.wartosc == WartoscKarty.Wartosc.J) ||
             (wk.wartosc == WartoscKarty.Wartosc.Q) ||
             (wk.wartosc == WartoscKarty.Wartosc.K) ) {
            
            BufferedImage popiersie = bp.popiersie(wk);
            karta.drawImage(popiersie, 20, 15, null);
            karta.drawImage(popiersie, obrot180, 20+popiersie.getWidth(), 75+popiersie.getHeight());
        }
        
        return bitmapa;
        
    }
    
    private int wyliczIndeksKarty(WartoscKarty wk) {
       
        int nrKoloru;
        int nrNominalu;
        
        nrKoloru = wk.kolor.ordinal();
        nrNominalu = wk.wartosc.ordinal();
        
        return nrKoloru*13+nrNominalu;
    }
    
    public BufferedImage narysujKarte(ObrazKarty karta) throws PodgladanieKartyException {
        
        BufferedImage bitmapa = null;
        
 
        if(karta.jestWidoczna()) {
            bitmapa = karty[wyliczIndeksKarty(karta.odczytajWartosc())];
        }
        else {
            bitmapa = koszulki[wybranaKoszulka];
        }
        
        return bitmapa;
    }
    
    public BufferedImage narysujKarte(int i) throws PodgladanieKartyException {
        
        return karty[i];
    }

    void przelaczKoszulke() {
        wybranaKoszulka = (wybranaKoszulka+1)%koszulki.length;
    }
    
}
