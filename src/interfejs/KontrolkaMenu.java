package interfejs;

import interfejs.jezyki.Jezyk;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

/**
 * Kontrolka odpowiedzialna za wyświetlenie menu.
 * @author Michał Wróbel
 */
public class KontrolkaMenu extends JPanel {
    
    private ActionListener nasluchiwacz;
    private Jezyk jezyk;
    
    private JButton przyciskGraj;
    
    private JRadioButton tryb1b1g;
    private JRadioButton tryb2b1g;
    private JRadioButton tryb1b2g;    
    private ButtonGroup grupka;
    
    private JTextArea nazwaGracza1;
    private JTextArea nazwaGracza2;
    
    private JLabel liczbaGraczy;
    private JLabel nazwaGraczaL1;
    private JLabel nazwaGraczaL2;
    
    /** Konstruktor */
    public KontrolkaMenu(Jezyk j, ActionListener elementNadrzedny) {
        
        nasluchiwacz = elementNadrzedny;
        jezyk = j;
        
        utworzPrzyciski();
        
    }
    
    public UstawieniaGry zwrocUstawienia() {
        
        UstawieniaGry ug = new UstawieniaGry();
        
        if (tryb1b1g.isSelected())
            ug.liczbaGraczy = UstawieniaGry.LiczbaGraczy.G1B1;
        else if(tryb2b1g.isSelected())
            ug.liczbaGraczy = UstawieniaGry.LiczbaGraczy.G1B2;
        else
            ug.liczbaGraczy = UstawieniaGry.LiczbaGraczy.G2B1;
        
        ug.nazwaGracza1 = nazwaGracza1.getText();
        ug.nazwaGracza2 = nazwaGracza2.getText();
        
        return ug;
    }
    
    private void utworzPrzyciski() {
        
        przyciskGraj = new JButton();
        przyciskGraj.setActionCommand("Graj");
        przyciskGraj.addActionListener(nasluchiwacz);
        
        grupka = new ButtonGroup();
        tryb1b1g = new JRadioButton();
        tryb2b1g = new JRadioButton();
        tryb1b2g = new JRadioButton();
        grupka.add(tryb1b1g);
        grupka.add(tryb2b1g);
        grupka.add(tryb1b2g);
        tryb1b1g.setOpaque(false);
        tryb2b1g.setOpaque(false);
        tryb1b2g.setOpaque(false);
        tryb1b1g.setSelected(true);
        
        nazwaGracza1 = new JTextArea();
        nazwaGracza2 = new JTextArea();
        nazwaGracza1.setText(jezyk.gracz);
        nazwaGracza2.setText(jezyk.gracz + '2');
        
        liczbaGraczy = new JLabel();
        nazwaGraczaL1 = new JLabel();
        nazwaGraczaL2 = new JLabel();
        
        setLayout(null);
        add(przyciskGraj);
        add(tryb1b1g);
        add(tryb2b1g);
        add(tryb1b2g);
        add(nazwaGracza1);
        add(nazwaGracza2);
        add(liczbaGraczy);
        add(nazwaGraczaL1);
        add(nazwaGraczaL2);
        ustawEtykiety();
        ustawPolozeniePrzyciskow();
    }
    
    public void ustawEtykiety() {
        przyciskGraj.setText(jezyk.graj);
        tryb1b1g.setText(jezyk.gracz1bot1);
        tryb2b1g.setText(jezyk.gracz1bot2);
        tryb1b2g.setText(jezyk.gracz2bot1);
        liczbaGraczy.setText(jezyk.liczbaGraczy);
        nazwaGraczaL1.setText(jezyk.nazwaGracza1);
        nazwaGraczaL2.setText(jezyk.nazwaGracza2);
    }
    
    private void ustawPolozeniePrzyciskow() {
        
        Insets insets = getInsets();
        int wys = insets.top + getHeight()/2;
        int sze = insets.left + getWidth()/2;
        
        przyciskGraj.setBounds(sze-100, wys-100, 200, 50);
        liczbaGraczy.setBounds(sze-150, wys-25, 150, 25);
        tryb1b1g.setBounds(sze-150, wys, 150, 25);
        tryb2b1g.setBounds(sze-150, wys+25, 150, 25);
        tryb1b2g.setBounds(sze-150, wys+50, 150, 25);
        nazwaGraczaL1.setBounds(sze, wys-25, 250, 25);
        nazwaGracza1.setBounds(sze, wys, 150, 25);
        nazwaGraczaL2.setBounds(sze, wys+25, 250, 25);
        nazwaGracza2.setBounds(sze, wys+50, 150, 25);
    }
            
    @Override
    public void paint(Graphics g) {
        
        // Malowanie stołu na zielono
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, getWidth(), getHeight());
        ustawPolozeniePrzyciskow();
        paintChildren(g);
         
    }
    
}