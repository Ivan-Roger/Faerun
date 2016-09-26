package Jeu;

//package ...

import Jeu.data.Chateau;
import Jeu.data.Plateau;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;



public class Application {
    public static Graph aff;
    public static JFrame frame;
    public static JFrame rulesF;
    public static JFrame servInfoF;
    public static JFrame loading;
    private static JTextField ipAdd;
    private static int plateSize;
    private static JTextField plateSizeF;
    public static JTextField personalName;
    private static JTextField castleA;
    private static JTextField castleB;
    private static JComboBox castleAC;
    private static JComboBox castleBC;
    private static JLabel loadingLbl;
    private static JLabel loadingDetailsLbl;
    
    public static void createRulesFrame() {
        rulesF = new JFrame("Faerun - Régles");
        rulesF.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JPanel rulesC = new JPanel();
        rulesC.setLayout(new BorderLayout());
        JLabel rulesI = new JLabel("Régles :");
        rulesI.setHorizontalAlignment(SwingConstants.CENTER);
        rulesC.add(rulesI,BorderLayout.NORTH);
        JTextArea rulesTxt = new JTextArea();
        rulesTxt.setText(" Bataille de Faerun :\n Par Some0ne\n\n"
                + " Vous avez deux equipes appellées chateaux. Vous devez créer leur\n  armée et les faire s'affronter.\n\n"
                + " En cliquant sur les boutons du Chateau vous ajoutez des Troupes a\n  l'entrainement. Leur cout en ressources est indiqué a coté.\n\n"
                + " Vous commencez avec 3 ressources et en gagnez une par tour. Une\n  fois une troupe entrainée elle entre sur le plateau.\n\n"
                + " A chaque nouveau tour (clic sur le bouton 'Jouer un tour') les troupes\n  avancent d'un carreau s'il n'y a personne ou affrontent les enemis\n  s'il y en a.\n\n"
                + " Bonne chance ! Et bon jeu ...");
        rulesTxt.setEditable(false);
        rulesTxt.setBorder(new EmptyBorder(10,0,10,0));
        rulesC.add(rulesTxt,BorderLayout.CENTER);
        JButton close = new JButton("Fermer");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rulesF.setVisible(false);
            }
        });
        rulesC.add(close,BorderLayout.SOUTH);
        rulesF.getContentPane().add(rulesC);
        rulesF.setSize(new Dimension(450,500));
        rulesF.setLocationRelativeTo(null);
    }
    
    public static void createServInfoFrame() {
        servInfoF = new JFrame("Faerun");
        servInfoF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel servInfoC = new JPanel();
        servInfoC.setLayout(new BorderLayout());
        JLabel servInfoLbl = new JLabel("Connexion a :");
        servInfoLbl.setHorizontalAlignment(SwingConstants.CENTER);
        servInfoC.add(servInfoLbl,BorderLayout.NORTH);
        ipAdd = new JTextField();
        servInfoC.add(ipAdd,BorderLayout.CENTER);
        JPanel servInfoBP = new JPanel();
        servInfoBP.setLayout(new BorderLayout());
        JButton servInfoBC = new JButton("Connexion");
        servInfoBC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                servInfoF.setVisible(false);
                loading = showLoading(new Runnable(){
                    @Override
                    public void run() {
                        aff = new Graph(new Chateau(castleB.getText(),(String)castleBC.getSelectedItem(),0),ipAdd.getText()); // Mode Client
                    }
                },"Connexion au serveur.","Adresse IP serveur : "+ipAdd.getText());
            }
        });
        servInfoBP.add(servInfoBC,BorderLayout.LINE_START);
        JButton servInfoBQ = new JButton("Quitter");
        servInfoBQ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                servInfoF.setVisible(false);
                frame.setVisible(true);
            }
        });
        servInfoBP.add(servInfoBQ,BorderLayout.LINE_END);
        servInfoC.add(servInfoBP,BorderLayout.SOUTH);
        servInfoF.getContentPane().add(servInfoC);
        servInfoF.setSize(new Dimension(200,100));
        servInfoF.setLocationRelativeTo(null);
    }
    
    public static JFrame createLoadingFrame(final Runnable th) {
        final JFrame loadF = new JFrame("Faerun - Chargement");
        loadF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel content = new JPanel();
        content.setOpaque(true);
        content.setLayout(new BorderLayout());
        loadingLbl = new JLabel("Loading");
        loadingLbl.setHorizontalAlignment(SwingConstants.CENTER);
        content.add(loadingLbl,BorderLayout.NORTH);
        loadingDetailsLbl = new JLabel("");
        content.add(loadingDetailsLbl,BorderLayout.CENTER);
        final JButton go = new JButton("Commencer");
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadF.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                go.setVisible(false);
                th.run();
            }
        });
        content.add(go,BorderLayout.SOUTH);
        loadF.getContentPane().add(content);
        loadF.setSize(new Dimension(300,100));
        loadF.setLocationRelativeTo(null);
        return loadF;
    }
    
    public static JFrame showLoading(Runnable th,String message,String details) {
        JFrame loadF = createLoadingFrame(th);
        loadingLbl.setText(message);
        loadingDetailsLbl.setText(details);
        loadF.setVisible(true);
        return loadF;
    }
    
    public static void main(String[] args) {
        createRulesFrame();
        createServInfoFrame();
        
        String[] colorsS = {"Bleu","Rouge","Vert","Cyan","Orange","Rose","Jaune"};
        
        plateSize = 5;
        //Create and set up the window.
        frame = new JFrame("Faerun");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        //Adds the menu bar to the frame
//        frame.setJMenuBar(createMenuBar());
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        
        JLabel txt = new JLabel("Faerun");
        txt.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(txt,BorderLayout.NORTH);
        
        JPanel inputsP = new JPanel();
        inputsP.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.ipadx = 20;
            gbc.ipady = 20;
        txt = new JLabel("Nom :");
        inputsP.add(txt,gbc);
        personalName = new JTextField();
        Random alea = new Random();
        int id = alea.nextInt();
        personalName.setText("Player_"+(id+"").substring(2, 5));
        gbc.gridx++;
        inputsP.add(personalName,gbc);
        txt = new JLabel("Taille du plateau :");
        gbc.gridx = 0;
        gbc.gridy++;
        inputsP.add(txt,gbc);
        plateSizeF = new JTextField();
        plateSizeF.setText(plateSize+"");
        gbc.gridx++;
        inputsP.add(plateSizeF,gbc);
        txt = new JLabel("Nom et couleur du Chateau A :");
        gbc.gridx = 0;
        gbc.gridy++;
        inputsP.add(txt,gbc);
        JPanel castleAP = new JPanel();
        castleAP.setLayout(new BorderLayout());
        castleA = new JTextField();
        castleA.setText("Chateau A");
        castleAP.add(castleA,BorderLayout.CENTER);
        castleAC = new JComboBox(colorsS);
        castleAC.setPreferredSize(new Dimension(100,20));
        castleAC.setSelectedIndex(0);
        castleAP.add(castleAC,BorderLayout.SOUTH);
        gbc.gridx++;
        inputsP.add(castleAP,gbc);
        txt = new JLabel("Nom et couleur du Chateau B :");
        gbc.gridx = 0;
        gbc.gridy++;
        inputsP.add(txt,gbc);
        JPanel castleBP = new JPanel();
        castleBP.setLayout(new BorderLayout());
        castleB = new JTextField();
        castleB.setText("Chateau B");
        castleBP.add(castleB,BorderLayout.CENTER);
        castleBC = new JComboBox(colorsS);
        castleBC.setSelectedIndex(1);
        castleBC.setPreferredSize(new Dimension(100,20));
        castleBP.add(castleBC,BorderLayout.SOUTH);
        gbc.gridx++;
        inputsP.add(castleBP,gbc);
        contentPane.add(inputsP,BorderLayout.CENTER);
        
        JPanel buttonsP = new JPanel();
        buttonsP.setLayout(new BorderLayout());
        JPanel playP = new JPanel();
        playP.setLayout(new BorderLayout());
        JButton play = new JButton("Tester");
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((JButton)e.getSource()).getText().equals("Tester")) {
                    frame.setVisible(false);
                    plateSize=Integer.parseInt(plateSizeF.getText());
                    Plateau plateau = new Plateau(plateSize, new Chateau(castleA.getText(),(String)castleAC.getSelectedItem(),0), new Chateau(castleB.getText(),(String)castleBC.getSelectedItem(),1));
                    aff = new Graph(plateau); // Mode Solo
                }
            }
        });
        playP.add(play,BorderLayout.NORTH);
        play = new JButton("Créer serveur");
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((JButton)e.getSource()).getText().equals("Créer serveur")) {
                    frame.setVisible(false);
                    plateSize=Integer.parseInt(plateSizeF.getText());
                    try {
                        loading = showLoading(new Runnable() {
                            @Override
                            public void run() {
                               aff = new Graph(plateSize,new Chateau(castleA.getText(),(String)castleAC.getSelectedItem(),0));
                            }
                        },"Attente d'un joueur ...","Votre IP : "+Inet4Address.getLocalHost().getHostAddress()+"             (50 secondes)");
                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                }
            }
        });
        playP.add(play,BorderLayout.CENTER);
        play = new JButton("Rejoindre serveur");
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((JButton)e.getSource()).getText().equals("Rejoindre serveur")) {
                    frame.setVisible(false);
                    servInfoF.setVisible(true);
                }
            }
        });
        playP.add(play,BorderLayout.SOUTH);
        buttonsP.add(playP,BorderLayout.NORTH);
        JButton rulesB = new JButton("Régles");
        rulesB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((JButton)e.getSource()).getText().equals("Régles")) {
                    rulesF.setVisible(true);
                }
            }
        });
        buttonsP.add(rulesB,BorderLayout.CENTER);
        JButton quitB = new JButton("Quitter");
        quitB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((JButton)e.getSource()).getText().equals("Quitter")) {
                    System.exit(0);
                }
            }
        });
        buttonsP.add(quitB,BorderLayout.SOUTH);
        contentPane.add(buttonsP,BorderLayout.SOUTH);
        
        
        frame.getContentPane().add(contentPane);

        //Set the size and position of the frame
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        //Add listener to components that can bring up popup menus.
        //Display the window.
        frame.setVisible(true);

    }
}
