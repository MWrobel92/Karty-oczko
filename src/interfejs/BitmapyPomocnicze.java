/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfejs;

import algorytm.WartoscKarty;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Klasa, której obekty zawierają wczytany zbiór bitmap wykorzystywanych do rysowania kart.
 * @author Michał Wróbel
 */
public class BitmapyPomocnicze {
        
    private BufferedImage pustaKarta;
    
    private BufferedImage kierMaly;
    private BufferedImage kierDuzy;
    private BufferedImage pikMaly;
    private BufferedImage pikDuzy;
    private BufferedImage karoMaly;
    private BufferedImage karoDuzy;
    private BufferedImage treflMaly;
    private BufferedImage treflDuzy;
    
    private BufferedImage czerwone2;
    private BufferedImage czarne2;
    private BufferedImage czerwone3;
    private BufferedImage czarne3;
    private BufferedImage czerwone4;
    private BufferedImage czarne4;
    private BufferedImage czerwone5;
    private BufferedImage czarne5;
    private BufferedImage czerwone6;
    private BufferedImage czarne6;
    private BufferedImage czerwone7;
    private BufferedImage czarne7;
    private BufferedImage czerwone8;
    private BufferedImage czarne8;
    private BufferedImage czerwone9;
    private BufferedImage czarne9;
    private BufferedImage czerwone10;
    private BufferedImage czarne10;
    private BufferedImage czerwoneJ;
    private BufferedImage czarneJ;
    private BufferedImage czerwoneQ;
    private BufferedImage czarneQ;
    private BufferedImage czerwoneK;
    private BufferedImage czarneK;
    private BufferedImage czerwoneA;
    private BufferedImage czarneA;
    
    private BufferedImage popiersieKrol;
    private BufferedImage popiersieDama;
    private BufferedImage popiersieWalet;
    
    private LinkedList<BufferedImage> koszulki;
    
    public BitmapyPomocnicze () throws IOException {
        
        laduj();
        
    }
    
    private void laduj() throws IOException {
    
        pustaKarta = ImageIO.read(new File("Karty/Karta.png"));
        
        kierMaly   = ImageIO.read(new File("Karty/KierMaly.png"));
        kierDuzy   = ImageIO.read(new File("Karty/KierDuzy.png"));
        pikMaly   = ImageIO.read(new File("Karty/PikMaly.png"));
        pikDuzy   = ImageIO.read(new File("Karty/PikDuzy.png"));
        karoMaly   = ImageIO.read(new File("Karty/KaroMaly.png"));
        karoDuzy   = ImageIO.read(new File("Karty/KaroDuzy.png"));
        treflMaly   = ImageIO.read(new File("Karty/TreflMaly.png"));
        treflDuzy   = ImageIO.read(new File("Karty/TreflDuzy.png"));
        
        czerwone2  = ImageIO.read(new File("Karty/2czerwone.png"));
        czarne2  = ImageIO.read(new File("Karty/2czarne.png"));
        czerwone3  = ImageIO.read(new File("Karty/3czerwone.png"));
        czarne3  = ImageIO.read(new File("Karty/3czarne.png"));
        czerwone4  = ImageIO.read(new File("Karty/4czerwone.png"));
        czarne4  = ImageIO.read(new File("Karty/4czarne.png"));
        czerwone5  = ImageIO.read(new File("Karty/5czerwone.png"));
        czarne5  = ImageIO.read(new File("Karty/5czarne.png"));
        czerwone6  = ImageIO.read(new File("Karty/6czerwone.png"));
        czarne6  = ImageIO.read(new File("Karty/6czarne.png"));
        czerwone7  = ImageIO.read(new File("Karty/7czerwone.png"));
        czarne7  = ImageIO.read(new File("Karty/7czarne.png"));
        czerwone8  = ImageIO.read(new File("Karty/8czerwone.png"));
        czarne8  = ImageIO.read(new File("Karty/8czarne.png"));
        czerwone9  = ImageIO.read(new File("Karty/9czerwone.png"));
        czarne9  = ImageIO.read(new File("Karty/9czarne.png"));
        czerwone10  = ImageIO.read(new File("Karty/10czerwone.png"));
        czarne10  = ImageIO.read(new File("Karty/10czarne.png"));
        czerwoneJ  = ImageIO.read(new File("Karty/Jczerwone.png"));
        czarneJ  = ImageIO.read(new File("Karty/Jczarne.png"));
        czerwoneQ  = ImageIO.read(new File("Karty/Qczerwone.png"));
        czarneQ  = ImageIO.read(new File("Karty/Qczarne.png"));
        czerwoneK  = ImageIO.read(new File("Karty/Kczerwone.png"));
        czarneK  = ImageIO.read(new File("Karty/Kczarne.png"));
        czerwoneA  = ImageIO.read(new File("Karty/Aczerwone.png"));
        czarneA  = ImageIO.read(new File("Karty/Aczarne.png"));
        
        popiersieKrol = ImageIO.read(new File("Karty/PopiersieKrol.png"));
        popiersieDama = ImageIO.read(new File("Karty/PopiersieDama.png"));
        popiersieWalet = ImageIO.read(new File("Karty/PopiersieWalet.png"));
        
        koszulki = new LinkedList<>();
        koszulki.add(ImageIO.read(new File("Karty/Koszulka1.png")));
        koszulki.add(ImageIO.read(new File("Karty/Koszulka2.png")));
        koszulki.add(ImageIO.read(new File("Karty/Koszulka3.png")));
        koszulki.add(ImageIO.read(new File("Karty/Koszulka4.png")));
        koszulki.add(ImageIO.read(new File("Karty/Koszulka5.png")));
        
    }
    
    public List<BufferedImage> tablicaKoszulek () {
        return koszulki;
    }
    
    /**
     * 
     * @return Kopię bitmapy zawierającej kontur karty.
     */
    public BufferedImage pustaKarta () {        
        
        BufferedImage bitmapa = new BufferedImage(pustaKarta.getWidth(), pustaKarta.getHeight(), pustaKarta.getType());
        Graphics2D g = bitmapa.createGraphics();
        g.drawImage(pustaKarta, 0, 0, null);
        return bitmapa; 
    }
            
    public BufferedImage symbolMaly (WartoscKarty wk) {
        
        switch (wk.kolor) {
            case KIER:
                return kierMaly;
            case PIK:
                return pikMaly;
            case KARO:
                return karoMaly;
            default:
                return treflMaly;
        }
    }
    
    public BufferedImage symbolDuzy (WartoscKarty wk) {
        
        switch (wk.kolor) {
            case KIER:
                return kierDuzy;
            case PIK:
                return pikDuzy;
            case KARO:
                return karoDuzy;
            default:
                return treflDuzy;
        }
    }
    
    public BufferedImage popiersie (WartoscKarty wk) {
        
        switch (wk.wartosc) {
            case J:
                return popiersieWalet;
            case Q:
                return popiersieDama;
            default:
                return popiersieKrol;
        }
    }
    
    /**
     * Zwraca bitmapę z nominałem karty o podanej wartości.
     * @param wk
     * @return 
     */
    public BufferedImage nominal (WartoscKarty wk) {
        
        if ((wk.kolor == WartoscKarty.Kolor.KARO) || (wk.kolor == WartoscKarty.Kolor.KIER)) {
            
            // Nominał jest czerwony
            switch (wk.wartosc) {
                case A:
                    return czerwoneA;
                case K:
                    return czerwoneK;
                case Q:
                    return czerwoneQ;
                case J:
                    return czerwoneJ;
                case N10:
                    return czerwone10;
                case N9:
                    return czerwone9;
                case N8:
                    return czerwone8;
                case N7:
                    return czerwone7;
                case N6:
                    return czerwone6;
                case N5:
                    return czerwone5;
                case N4:
                    return czerwone4;
                case N3:
                    return czerwone3;
                default: // 2
                    return czerwone2;
            }
            
        }
        else {
            
            // Nominał jest czarny
            switch (wk.wartosc) {
                case A:
                    return czarneA;
                case K:
                    return czarneK;
                case Q:
                    return czarneQ;
                case J:
                    return czarneJ;
                case N10:
                    return czarne10;
                case N9:
                    return czarne9;
                case N8:
                    return czarne8;
                case N7:
                    return czarne7;
                case N6:
                    return czarne6;
                case N5:
                    return czarne5;
                case N4:
                    return czarne4;
                case N3:
                    return czarne3;
                default: // 2
                    return czarne2;
            }
        }
    }
}
