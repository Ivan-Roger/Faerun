/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu;

import Jeu.data.Champ;
import Jeu.data.ChefElfe;
import Jeu.data.ChefNain;
import Jeu.data.Elfe;
import Jeu.data.Field;
import Jeu.data.Guerrier;
import Jeu.data.Nain;
import Jeu.data.Plateau;
import Jeu.data.Style;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author rogeri
 */
public class Server extends Thread {
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;
    private Plateau plate;
    private Boolean turnOver = false;
    
    public Server() {
        try {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt("15352"));
            serverSocket.setSoTimeout(50000);
            System.out.println("Waiting for clients on port "+ serverSocket.getLocalPort() + "...");
            client = serverSocket.accept();
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Client "+client.getInetAddress()+":"+client.getPort()+" connected !");
        } catch (Exception e) {
            if ("Accept timed out".equals(e.getMessage())) {
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

    public Boolean turnOver() {
        return turnOver;
    }

    public void newTurn() {
        turnOver=false;
    }
    
    public void exec(String s) {
        if (s!=null) {
            System.out.println("Client link > "+s);
            String[] txt;
            txt = s.split(" ");
            switch(txt[0]) {
                case "addG":
                    switch(txt[1]) {
                        case "Nain":
                            plate.getCastleB().addGuerrier(new Nain(plate.getCastleB(),plate));
                            break;
                        case "ChefNain":
                            plate.getCastleB().addGuerrier(new ChefNain(plate.getCastleB(),plate));
                            break;
                        case "Elfe":
                            plate.getCastleB().addGuerrier(new Elfe(plate.getCastleB(),plate));
                            break;
                        case "ChefElfe":
                            plate.getCastleB().addGuerrier(new ChefElfe(plate.getCastleB(),plate));
                            break;
                    }
                    break;
                case "act":
                    turnOver=true;
                    Jeu.Application.aff.print(new Champ(Field.log, 0), "L'adversaire a fini son tour.\n");
                    break;
                case "chat": // chat texte
                    Jeu.Application.aff.print(new Champ(Field.chat, 0),Jeu.Graph.nomAdv,Style.Bold);
                    Jeu.Application.aff.print(new Champ(Field.chat, 0)," : "+s.split(" ",2)[1]+"\n");
                    break;
            }
        }
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
}
