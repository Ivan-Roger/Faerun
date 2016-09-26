package Jeu;

import static Jeu.Application.personalName;
import Jeu.data.Champ;
import Jeu.data.Chateau;
import Jeu.data.ChefElfe;
import Jeu.data.ChefNain;
import Jeu.data.Elfe;
import Jeu.data.Field;
import Jeu.data.JColoredTextArea;
import Jeu.data.Nain;
import Jeu.data.Plateau;
import Jeu.data.Style;
import static Jeu.utils.ChateauUtilitaire.afficheAjoutEntrainement;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class Graph implements ActionListener, ItemListener {

    public boolean debug = false;
    private static boolean ready;
    public String version = "0.1.3";
    public static Plateau plate;
    private JFrame frame;
    public static String nomAdv;
    
    public static String mode;
    
    public static Server serv;
    public static Client client;
    public static Clock clock;
    
    private TreeMap<Integer,JColoredTextArea[]> plateau;
    private TreeMap<Integer,JLabel> plateauNb;
    private JColoredTextArea castleALog;
    private JColoredTextArea castleBLog;
    private JLabel turnLbl;
    private JColoredTextArea log;
    public JButton turn;
    private JTextField chatIT;
    private JColoredTextArea chat;
    private JLabel pic;
    
    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     * @return The menu bar
     */
    private JMenuBar createMenuBar() {
        //Create the menu bar
        JMenuBar menu = new JMenuBar();
        
        //Create the Settings menu
        JMenu settings = new JMenu("Menu");
        settings.setMnemonic(KeyEvent.VK_M);

        //Adds a Debug checkbox to the menu
        JCheckBoxMenuItem debugCK = new JCheckBoxMenuItem("Debug");
        debugCK.setMnemonic(KeyEvent.VK_D);
        debugCK.addItemListener(this);
        settings.add(debugCK);
        
        //Adds a Regles item to the settings menu
        JMenuItem rules = new JMenuItem("Régles", KeyEvent.VK_R);
        rules.addActionListener(this);
        settings.add(rules);

        //Adds a Replay item to the settings menu
        JMenuItem replay = new JMenuItem("Rejouer", KeyEvent.VK_J);
        replay.addActionListener(this);
        settings.add(replay);
        menu.add(settings);

        //Adds a Quit item to the settings menu
        JMenuItem quit = new JMenuItem("Quit", KeyEvent.VK_Q);
        quit.addActionListener(this);
        settings.add(quit);

        return menu;
    }

    private Container createContentPane() {
        //Create the content-pane-to-be.
        JPanel contentPane = new JPanel();
        contentPane.setOpaque(true);
        contentPane.setLayout(new BorderLayout());
        
        JPanel carreaux = new JPanel(new BorderLayout());
        carreaux.setLayout(new FlowLayout());
        JPanel cont;
        JLabel txt;
        JScrollPane scrollPane;
        
        BufferedImage picCastleA;
        BufferedImage picCastleB;
        try {
            picCastleA = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("assets/Castle.png"));
            carreaux.add(new JLabel(new ImageIcon(picCastleA)));
        } catch(Exception e) {
            e.printStackTrace();
        }
        pic = new JLabel(new ImageIcon());
        pic.setPreferredSize(new Dimension(100,100));
        carreaux.add(pic);
        
        for (int i=0; i<plate.size(); i++) {
            cont = new JPanel();
            cont.setLayout(new BorderLayout());
            
            JPanel carP = new JPanel();
            carP.setLayout(new BorderLayout());
            JColoredTextArea carA = new JColoredTextArea();
            carA.setEditable(true);
            carA.setPreferredSize(new Dimension(125,85));
            carP.add(carA,BorderLayout.NORTH);
            JColoredTextArea carB = new JColoredTextArea();
            carB.setEditable(true);
            carB.setPreferredSize(new Dimension(125,85));
            carP.add(carB,BorderLayout.SOUTH);
            
            JLabel units = new JLabel("0 - 0");   
            units.setHorizontalAlignment(SwingConstants.CENTER);
            JColoredTextArea[] car = {null,null};
            car[0]=carA;
            car[1]=carB;
            plateau.put(i, car);
            plateauNb.put(i,units);
            txt = new JLabel("Carreau "+(i+1));
            txt.setHorizontalAlignment(SwingConstants.CENTER);
            cont.add(txt,BorderLayout.NORTH);
            cont.add(carP,BorderLayout.CENTER);
            cont.add(units,BorderLayout.SOUTH);
            carreaux.add(cont);
        }
        
        try {
            picCastleB = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("assets/Castle.png"));
            carreaux.add(new JLabel(new ImageIcon(picCastleB)));
        } catch(Exception e) {
            e.printStackTrace();
        }
                
        JScrollPane carreauxScroll = new JScrollPane(carreaux);
        carreauxScroll.setOpaque(true);
        carreauxScroll.setPreferredSize(new Dimension(500,225));
        contentPane.add(carreauxScroll,BorderLayout.NORTH);
                
        //Create the castlePane
        JPanel chateauA = new JPanel();
        chateauA.setLayout(new BorderLayout());
        JLabel castleALbl = new JLabel(plate.getCastleA().getName());
        castleALbl.setForeground(plate.getCastleA().getColor());
        castleALbl.setHorizontalAlignment(SwingConstants.CENTER);
        chateauA.add(castleALbl,BorderLayout.NORTH);
        //Create a CastleLog (scrolled text area)
        castleALog = new JColoredTextArea();
        castleALog.setEditable(true);
        castleALog.setPreferredSize(new Dimension(300,200));
        scrollPane = new JScrollPane(castleALog);
        chateauA.add(scrollPane,BorderLayout.LINE_START);
        //Create add buttons
        JPanel addButtons = new JPanel();
        addButtons.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.ipadx = 20;
            gbc.ipady = 20;
        //Adds text label to the castle add pane
        txt = new JLabel("Ajouter :");
        addButtons.add(txt,gbc);
        //Adds dwarfs item to the castle add pane
        JButton dw = new JButton("Nain (1)");
        dw.setName("A");
        dw.addActionListener(this);
        gbc.gridy++;
        addButtons.add(dw,gbc);
        JButton dwM = new JButton("Chef Nain (3)");
        dwM.setName("A");
        dwM.addActionListener(this);
        gbc.gridy++;
        addButtons.add(dwM,gbc);
        //Adds elfs item to the castle add pane
        JButton elf = new JButton("Elfe (2)");
        elf.setName("A");
        elf.addActionListener(this);
        gbc.gridy++;
        addButtons.add(elf,gbc);
        JButton elfM = new JButton("Chef Elfe (4)");
        elfM.setName("A");
        elfM.addActionListener(this);
        gbc.gridy++;
        addButtons.add(elfM,gbc);
        //Adds the add buttons to the castle pane
        chateauA.add(addButtons,BorderLayout.LINE_END);
        //Adds the castlePane to the contentPane
        contentPane.add(chateauA,BorderLayout.LINE_START);
        
        if (mode=="Solo") {
            //Create the castlePane
            JPanel chateauB = new JPanel();
            chateauB.setLayout(new BorderLayout());
            JLabel castleBLbl = new JLabel(plate.getCastleB().getName());
            castleBLbl.setForeground(plate.getCastleB().getColor());
            castleBLbl.setHorizontalAlignment(SwingConstants.CENTER);
            chateauB.add(castleBLbl,BorderLayout.NORTH);
            //Create a CastleLog (scrolled text area)
            castleBLog = new JColoredTextArea();
            castleBLog.setEditable(true);
            castleBLog.setPreferredSize(new Dimension(300,200));
            scrollPane = new JScrollPane(castleBLog);
            chateauB.add(scrollPane,BorderLayout.LINE_END);
            //Create add buttons
            addButtons = new JPanel();
            addButtons.setLayout(new GridBagLayout());
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.ipadx = 20;
                gbc.ipady = 20;
            //Adds text label to the castle add pane
            txt = new JLabel("Ajouter :");
            addButtons.add(txt,gbc);
            //Adds dwarfs item to the castle add pane
            dw = new JButton("Nain (1)");
            dw.setName("B");
            dw.addActionListener(this);
            gbc.gridy++;
            addButtons.add(dw,gbc);
            dwM = new JButton("Chef Nain (3)");
            dwM.setName("B");
            dwM.addActionListener(this);
            gbc.gridy++;
            addButtons.add(dwM,gbc);
            //Adds elfs item to the castle add pane
            elf = new JButton("Elfe (2)");
            elf.setName("B");
            elf.addActionListener(this);
            gbc.gridy++;
            addButtons.add(elf,gbc);
            elfM = new JButton("Chef Elfe (4)");
            elfM.setName("B");
            elfM.addActionListener(this);
            gbc.gridy++;
            addButtons.add(elfM,gbc);
            //Adds the add buttons to the castle pane
            chateauB.add(addButtons,BorderLayout.LINE_START);
            //Adds the castlePane to the contentPane
            contentPane.add(chateauB,BorderLayout.LINE_END);
        } else {
            JPanel infoOponent = new JPanel();
            infoOponent.setPreferredSize(new Dimension(250,50));
            infoOponent.setLayout(new BorderLayout());
            JLabel infoOponentLbl = new JLabel(plate.getCastleB().getName());
            infoOponentLbl.setForeground(plate.getCastleB().getColor());
            infoOponent.add(infoOponentLbl,BorderLayout.NORTH);
            
            JPanel chatP = new JPanel();
            chatP.setLayout(new BorderLayout());
            chatP.add(new JLabel(nomAdv),BorderLayout.NORTH);
            chat = new JColoredTextArea();
            JScrollPane chatS = new JScrollPane(chat);
            chatP.add(chatS,BorderLayout.CENTER);
            JPanel chatI = new JPanel();
            chatI.setLayout(new BorderLayout());
            chatIT = new JTextField();
            chatIT.setName("InputChat");
            chatIT.addActionListener(this);
            chatI.add(chatIT,BorderLayout.CENTER);
            JButton chatIB = new JButton("Envoyer");
            chatIB.addActionListener(this);
            chatI.add(chatIB,BorderLayout.SOUTH);
            chatP.add(chatI,BorderLayout.SOUTH);
            infoOponent.add(chatP,BorderLayout.CENTER);
            
            contentPane.add(infoOponent,BorderLayout.LINE_END);
        }
        //Create the generalPane
        JPanel generalP = new JPanel();
        generalP.setLayout(new BorderLayout());
        generalP.setPreferredSize(new Dimension(500,150));
        //Create a turn label
        turnLbl = new JLabel("Tour 0");
        turnLbl.setHorizontalAlignment(SwingConstants.CENTER);
        generalP.add(turnLbl,BorderLayout.NORTH);
        
        //Create a log (scrolled text area)
        log = new JColoredTextArea();
        log.setEditable(true);
        scrollPane = new JScrollPane(log);
        generalP.add(scrollPane,BorderLayout.CENTER);
        
        //Create a turn button 
        if (mode=="Solo") {
            turn = new JButton("Jouer un tour");
        } else {
            turn = new JButton("Valider le tour");
        }
        turn.addActionListener(this);
        generalP.add(turn,BorderLayout.SOUTH);
        
        contentPane.add(generalP,BorderLayout.SOUTH);

        return contentPane;
    }

    private void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("Faerun - "+mode);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Adds the menu bar to the frame
        frame.setJMenuBar(createMenuBar());
        frame.getContentPane().add(createContentPane());

        //Set the size and position of the frame
        frame.setSize(1250, 850);
        frame.setLocationRelativeTo(null);

        //Add listener to components that can bring up popup menus.
        //Display the window.
        frame.setVisible(true);
        ready=true;
    }

    public boolean ready() {
        return ready;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().getClass().getSimpleName().equals("JMenuItem")) {
            JMenuItem source = (JMenuItem) (e.getSource());
            if (debug) {
                print(new Champ(Field.log,0),source.getText()+"\n");
            }
            if (source.getText().equals("Régles")) {
                Jeu.Application.rulesF.setVisible(true);
            } else if (source.getText().equals("Quit")) {
                System.exit(0);
            } else if (source.getText().equals("Rejouer")) {
                frame.setVisible(false);
                Jeu.Application.frame.setVisible(true);
            } else {
                print(new Champ(Field.log,0),source.getText()+"\n");                
            }
        } else if (e.getSource().getClass().getSimpleName().equals("JButton")) {
            JButton source = (JButton)(e.getSource());
            if (debug) {
                print(new Champ(Field.log,0),source.getText()+" : "+source.getName()+"\n");
            }
            if (source.getText().equals("Jouer un tour")||source.getText().equals("Valider le tour")) {
                if (mode=="Client") {
                    client.println("act");
                    print(new Champ(Field.log, 0),"En attente de l'adversaire.\n");
                } else if (mode=="Server") {
                    endTurn();
                } else {
                    plate.act();
                }
            } else if (source.getText().equals("Envoyer")) {                
                if (mode=="Client") {
                    client.println("chat "+chatIT.getText());
                    print(new Champ(Field.chat, 0),personalName.getText(),Style.Italic);
                    print(new Champ(Field.chat, 0)," : "+chatIT.getText()+"\n");
                } else {
                    serv.println("chat "+chatIT.getText());
                    print(new Champ(Field.chat, 0),personalName.getText(),Style.Italic);
                    print(new Champ(Field.chat, 0)," : "+chatIT.getText()+"\n");
                }
                chatIT.setText("");
            } else if (source.getName()=="A") {
                switch (source.getText()) {
                    case "Nain (1)":
                        if (mode=="Client") {
                            client.println("addG Nain");
                        } else {
                            plate.getCastleA().addGuerrier(new Nain(plate.getCastleA(),plate));
                        }
                        break;
                    case "Chef Nain (3)":
                        if (mode=="Client") {
                            client.println("addG ChefNain");
                        } else {
                            plate.getCastleA().addGuerrier(new ChefNain(plate.getCastleA(),plate));
                        }
                        break;
                    case "Elfe (2)":
                        if (mode=="Client") {
                            client.println("addG Elfe");
                        } else {
                            plate.getCastleA().addGuerrier(new Elfe(plate.getCastleA(),plate));
                        }
                        break;
                    case "Chef Elfe (4)":
                        if (mode=="Client") {
                            client.println("addG ChefElfe");
                        } else {
                            plate.getCastleA().addGuerrier(new ChefElfe(plate.getCastleA(),plate));
                        }
                        break;
                }
            } else if (source.getName()=="B") {
                switch (source.getText()) {
                    case "Nain (1)":
                        plate.getCastleB().addGuerrier(new Nain(plate.getCastleB(),plate));
                        break;
                    case "Chef Nain (3)":
                        plate.getCastleB().addGuerrier(new ChefNain(plate.getCastleB(),plate));
                        break;
                    case "Elfe (2)":
                        plate.getCastleB().addGuerrier(new Elfe(plate.getCastleB(),plate));
                        break;
                    case "Chef Elfe (4)":
                        plate.getCastleB().addGuerrier(new ChefElfe(plate.getCastleB(),plate));
                        break;
                }
            }
        } else if (e.getSource().getClass().getSimpleName().equals("JTextField")) {  
            JTextField source = (JTextField)(e.getSource());
            if (source.getName().equals("InputChat")) {
                if (mode=="Client") {
                    client.println("chat "+chatIT.getText());
                    print(new Champ(Field.chat, 0),personalName.getText(),Style.Italic);
                    print(new Champ(Field.chat, 0)," : "+chatIT.getText()+"\n");
                } else {
                    serv.println("chat "+chatIT.getText());
                    print(new Champ(Field.chat, 0),personalName.getText(),Style.Italic);
                    print(new Champ(Field.chat, 0)," : "+chatIT.getText()+"\n");
                }
                chatIT.setText("");
            } else {
                print(new Champ(Field.log,0),e.getSource().toString()+"\n");
            }
        } else {
            print(new Champ(Field.log,0),e.getSource().toString()+"\n");
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JMenuItem source = (JMenuItem) (e.getSource());
        if (source.getText().equals("Debug")) {
            debug = (e.getStateChange() == ItemEvent.SELECTED);
            print(new Champ(Field.log,0),"Debug mode : " + debug +"\n");
        }
        if (debug) {
            print(new Champ(Field.log,0),source.getText() + " : ");
            print(new Champ(Field.log,0),((e.getStateChange() == ItemEvent.SELECTED) ? "Check" : "Uncheck" )+ "\n");
        }
    }
    
    private JColoredTextArea getItem(Champ c) {
        if (c.field()==Field.carreauT) {
            return plateau.get(c.index())[0];
        } else if (c.field()==Field.carreauB) {
            return plateau.get(c.index())[1];
        } else if (c.field()==Field.chateau) {
            if (c.index()==0) {
                return castleALog;
            } else if (mode=="Solo") {
                return castleBLog;
            } else {
                return log;
            }
        } else if (c.field()==Field.chat) {
            return chat;
        } else {
            return log;
        }
    }
    
    private JLabel getItemL(Champ c) {
        if (c.field()==Field.carreauN) {
            return plateauNb.get(c.index());
        } else {
            return null;
        }
    }
    
    public void setPic(BufferedImage img) {
        pic.setIcon(new ImageIcon(img));
    }
    
    public void printL(Champ c, String s) {
        getItemL(c).setText(s);
    }
    
    public void print(Champ ch, String s, Color cl) {
        //System.out.print(s);
        getItem(ch).append(s,cl);
    }
    
    public void print(Champ ch, String s, Color cl, Style st) {
        //System.out.print(s);
        getItem(ch).append(s,cl,st);
    }
    
    public void print(Champ ch, String s, Style st) {
        //System.out.print(s);
        getItem(ch).append(s,st);
    }
    
    public void print(Champ ch, String s) {
        //System.out.print(s);
        getItem(ch).append(s);
    }
    
    public void clear(Champ ch) {
        getItem(ch).setText("");
    }
    
    public void clearAll() {
        for (int i=0; i<plate.size(); i++) {
            plateau.get(i)[0].setText("");
            plateau.get(i)[1].setText("");
            plateauNb.get(i).setText("0 - 0");
        }
        castleALog.setText("");
        if (mode=="Solo") {
            castleBLog.setText("");
        }
        log.setText("");
    }
    
    public void setTurn(int t) {
        System.out.println("\n===>>> Tour "+ t +" <<<===\n");
        turnLbl.setText("Tour "+t);
        clearAll();
        if (mode=="Server") {
            serv.newTurn();
            serv.println("turn "+t); //Lien serveur
        }
    }
    
    public void tellWinner(Chateau c) {
        turn.setVisible(false);
        clear(new Champ(Field.log,0));
        print(new Champ(Field.log,0),c.getName(),c.getColor(),Style.Bold);
        print(new Champ(Field.log,0)," a gagné !!!\n",Style.Bold);
        if (mode=="Server") {
            serv.println("win "+c.index()); //Lien serveur
        }
    }
    
    public void endTurn() {
        if (mode=="Server") {
//            System.out.println("Processing datas ...");
//            while (!plate.ready) {}
//            System.out.println("Process over.");
            if (serv.turnOver()) {
                plate.act();
            } else {
                print(new Champ(Field.log,0),"Veuillez attendre que l'adversaire ai joué.\n");
            }
        }
    }
    
    public Graph (Plateau p) { // Mode Solo
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        this.mode="Solo";
        ready=false;
        plate=p;
        plateau=new TreeMap();
        plateauNb=new TreeMap();
        try {
            createAndShowGUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        clock = new Clock();
        clock.start();
    }
    
    public Graph(int plateSize, Chateau c) { // Mode Server
        this.mode="Server";
        ready=false;
        plateau=new TreeMap();
        plateauNb=new TreeMap();
        System.out.println("DEBUG : Server mode");
        
        try {
            serv = new Server();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        serv.println("init Nom "+Jeu.Application.personalName.getText());
        serv.println("init Chateau "+c.getNomCouleur()+" "+c.getName());
        serv.println("init Plateau "+plateSize);
        serv.println("init Fin");
        
        String nomB = "";
        String couleurB;
        //getClient info
        String txt;
        try {
            while ((txt=serv.readLine())==null) {} // init Nom
            System.out.println("Client link > "+txt);
                nomAdv = txt.split(" ",3)[2]; // Nom de l'adversaire
            txt = serv.readLine(); // init Chateau
            System.out.println("Client link > "+txt);
                String[] txtA = txt.split(" ",4);
                couleurB = txtA[2]; // Couleur du chateau adverse
                nomB = txtA[3]; // Nom du Chateau adverse
            txt = serv.readLine(); // init Fin
            System.out.println("Client link > "+txt);

            plate = new Plateau(plateSize, c, new Chateau(nomB,couleurB,1));
            Jeu.Application.loading.setVisible(false);
            createAndShowGUI();
            serv.start(plate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Graph(Chateau c,String ip) { // Mode Client
        this.mode="Client";
        ready=false;
        plateau=new TreeMap();
        plateauNb=new TreeMap();
        System.out.println("DEBUG : Client mode");
        
        try {
            client = new Client(plate, ip);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        
        int plateSize;
        String nomB = "";
        String couleurB;
        
        //getServer init
        String txt;
        try {
            while ((txt=client.readLine())==null) {} // init Nom
                nomAdv = txt.split(" ",3)[2]; // Nom de l'adversaire
            System.out.println("Server link > "+txt);
            txt = client.readLine(); // init Chateau
                String[] txtA = txt.split(" ",4);
                couleurB = txtA[2]; // Couleur du chateau adverse
                nomB = txtA[3]; // Nom du Chateau adverse
            System.out.println("Server link > "+txt);
            txt = client.readLine(); // init Plateau
                plateSize = Integer.parseInt(txt.split(" ")[2]); // Taille du Plateau
            System.out.println("Server link > "+txt);
            txt = client.readLine(); // init Fin
            System.out.println("Server link > "+txt);
            
            plate = new Plateau(plateSize, c, new Chateau(nomB,couleurB,1));
            Jeu.Application.loading.setVisible(false);
            createAndShowGUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        client.println("info Nom "+Jeu.Application.personalName.getText());
        client.println("info Chateau "+c.getNomCouleur()+" "+c.getName());
        client.println("info Fin");
        
        client.start(plate);
    }
}
