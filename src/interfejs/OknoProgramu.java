package interfejs;

import algorytm.oczko.OczkoDecyzja;
import interfejs.jezyki.Jezyk;
import interfejs.jezyki.TworcaJezykow;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * Okno główne gry.
 * @author Michał Wróbel
 */
public class OknoProgramu extends JPanel implements ActionListener{
         
    private JFrame frame;
    private Jezyk jezyk;
    
    KontrolkaWidoku widok;
    KontrolkaMenu menu;
       
    private JMenu menuOpcje;
    private JMenuItem menuOpcjeKoszulka;
    private JCheckBox menuOpcjePusteRuchy;
    private JMenu menuGra;
    private JMenuItem menuGraPowrot;
   
    /**
     * Nasłuchiwanie nadchodzących zdarzeń.
     * @param e 
     */
    @Override
    public void actionPerformed (ActionEvent e) {
        
        String komenda = e.getActionCommand();
        
        switch (komenda) {
            case "Zmiana koszulki":
                if (widok != null) 
                    widok.przelaczKoszulke();
                break;
            case "Powrót do menu":
                if(widok != null)
                {
                    this.remove(widok);
                    widok = null;
                }
                this.add(menu);
                repaint();
                break;
            case "Graj":
                // Uruchomienie gry
                this.remove(menu);
                widok = new KontrolkaWidoku(jezyk, menu.zwrocUstawienia());
                this.add(widok, BorderLayout.CENTER);
                widok.rozpocznijGre();
                widok.zrobKrok(new OczkoDecyzja());
                break;
            default:
                
        }        
    };
    
    private void przygotujIPokazGUI() {        
        
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame(jezyk.nazwaProgramu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //prepairing panel
        OknoProgramu newContentPane = new OknoProgramu();
        newContentPane.setOpaque(true); 
        frame.setContentPane(newContentPane);

        //showing window
        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
    
    /**
     * Konstruktor
     */
    public OknoProgramu() {
        
        // Tworzenie podstawowych elementów
        jezyk = TworcaJezykow.utworzJezykPolski();
        
        this.setLayout(new BorderLayout());
        
        menu = new KontrolkaMenu(jezyk, this);
        this.add(menu, BorderLayout.CENTER);
        
        // Tworzenie menu
        JMenuBar pasekMenu = new JMenuBar();
        add(pasekMenu, BorderLayout.NORTH);
        menuGra = new JMenu();
        pasekMenu.add(menuGra);
        menuGraPowrot = new JMenuItem();
        menuGra.add(menuGraPowrot);
        menuGraPowrot.addActionListener(this);
        menuGraPowrot.setActionCommand("Powrót do menu");
        menuOpcje = new JMenu();
        pasekMenu.add(menuOpcje);
        menuOpcjeKoszulka = new JMenuItem();
        menuOpcje.add(menuOpcjeKoszulka);
        menuOpcjeKoszulka.addActionListener(this);
        menuOpcjeKoszulka.setActionCommand("Zmiana koszulki");
        menuOpcjePusteRuchy = new JCheckBox();
        //menuOpcje.add(menuOpcjePusteRuchy);
        ustawEtykiety();
        
    }
   
    public void start() {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {           
            @Override
            public void run() {
                przygotujIPokazGUI();
            };
        });
    }
    
    private void ustawEtykiety() {
        menuOpcje.setText(jezyk.menuOpcje);
        menuOpcjeKoszulka.setText(jezyk.menuKoszulka);
        menuOpcjePusteRuchy.setText(jezyk.menuPusteRuchy);
        menuGra.setText(jezyk.menuGra);
        menuGraPowrot.setText(jezyk.menuPowrot);
        
        if (widok != null)
            widok.ustawEtykiety();
        if (menu != null)
            menu.ustawEtykiety();
    }
    
}
