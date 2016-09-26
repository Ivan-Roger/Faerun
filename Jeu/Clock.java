/*
 * Default License :
 * ...
 */

package Jeu;

import Jeu.data.Guerrier;
import Jeu.data.Nain;

/**
 * class : Clock
 * by    : rogeri
 * @author rogeri
 */
public class Clock extends Thread {
    private int turn;
    private Guerrier g;
    
    public Clock() {
        turn=0;
        g=null;
    }
    
    public void run() {
        while (true) {
            if (g==null) {
                if (Jeu.Graph.plate.getCastleA().getGuerriersPrets().size()>0) {
                    g=Jeu.Graph.plate.getCastleA().getGuerriersPrets().get(0);
                }
            }
            try {
                this.sleep(165);
            } catch(Exception e) {
                e.printStackTrace();
            }
            newTurn(turn++);
        }
    }
    
    private void newTurn(int i) {
        if (g!=null) {
            Jeu.Application.aff.setPic(g.getImage());
        }
    }
}
