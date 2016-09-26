/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu;

import static Jeu.Application.aff;
import Jeu.data.Champ;
import static Jeu.data.Chateau.color;
import Jeu.data.Field;
import Jeu.data.Plateau;
import Jeu.data.Style;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Ivan
 */
public class Client extends Thread {
        private Socket sock;
        private PrintWriter out;
        private BufferedReader in;
        private Plateau plate;
        private Boolean turnOver;
        
        public Client(Plateau p,String ip) {
            try {
                sock = new Socket(ip,Integer.parseInt("15352"));
                out = new PrintWriter(sock.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            } catch (Exception e) {
                if ("Connection timed out: connect".equals(e.getMessage()) || "Connection refused: connect".equals(e.getMessage())) {
                    System.exit(-1);
                } else {
                    e.printStackTrace();
                }
            }
        }

        public void start(Plateau p) {
            plate = p;
            super.start();
        }
        
        public Boolean turnOver() {
            return turnOver;
        }
        
        public void turnOK() {
            turnOver=true;
        }
        
        public void print(String s) {
            out.print(s);
        }
        public void println(String s) {
            out.println(s);
        }
        public String readLine() {
            String txt = null;
            try {
                txt = in.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return txt;
        }
        
        @Override
        public void run() {
            try {
                while (true) {
                    exec(in.readLine());
                }
            } catch (SocketException e) {
            if (e.getMessage()=="Connection reset") {
                System.out.println("Enemi left ...");
                Jeu.Application.aff.print(new Champ(Field.chat, 0),Jeu.Graph.nomAdv,Style.Bold);
                Jeu.Application.aff.print(new Champ(Field.chat, 0)," est parti."+"\n");
                Jeu.Application.aff.turn.setVisible(false);
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        
        public Style style(String s) {
            switch(s) {
                case "Bold":
                    return Style.Bold;
                
                case "Italic":
                    return Style.Italic;
                    
                case "ItalicBold":
                    return Style.ItalicBold;
                    
                default:
                    return Style.Default;
            }
        }
        
        public String lineTrans(String s) {
            if ("~".equals(s)) {
                return "\n";
            } else {
                return s;
            }
        }
        
        public void exec(String s) {
            if (s!=null) {
                System.out.println("Server link > "+s);
                String[] txt = s.split(" ");
                switch(txt[0]) { // disp Field index texte          ou
                    case "disp": // disp Field index Style Color texte
                        int index = Integer.parseInt(txt[2]);                       
                        switch(txt[1]) {
                            case "carreauT":
                                index = (plate.size()-1)-index;
                                aff.print(new Champ(Field.carreauT, index),lineTrans(s.split(" ",6)[5]),color(txt[4]),style(txt[3]));
                                break;
                            case "carreauB":
                                index = (plate.size()-1)-index;
                                aff.print(new Champ(Field.carreauB, index),lineTrans(s.split(" ",6)[5]),color(txt[4]),style(txt[3]));
                                break;
                            case "carreauN":
                                index = (plate.size()-1)-index;
                                aff.printL(new Champ(Field.carreauN, index),lineTrans(s.split(" ",4)[3]));
                                break;
                            case "chateau":
                                if (index==1) {
                                    aff.print(new Champ(Field.chateau, (1-index)),lineTrans(s.split(" ",6)[5]),color(txt[4]),style(txt[3]));
                                }
                                break;
                            case "log":
                                aff.print(new Champ(Field.log, index),lineTrans(s.split(" ",6)[5]),color(txt[4]),style(txt[3]));
                                break;
                        }
                        break;
                    case "turn": // turn index
                        turnOver=false;
                        Jeu.Application.aff.setTurn(Integer.parseInt(txt[1]));
                        break;
                    case "chat": // chat texte
                        Jeu.Application.aff.print(new Champ(Field.chat, 0),Jeu.Graph.nomAdv,Style.Bold);
                        Jeu.Application.aff.print(new Champ(Field.chat, 0)," : "+s.split(" ",2)[1]+"\n");
                        break;
                    case "win": // win index
                        if (txt[1].equals("1")) {
                            System.out.println("Nous avons gagné !");
                            Jeu.Application.aff.tellWinner(plate.getCastleA());
                        } else {
                            System.out.println("Nous sommes battus !");
                            Jeu.Application.aff.tellWinner(plate.getCastleB());
                        }
                        break;
                }
            }
        }
    }
