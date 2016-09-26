/*
 * Default License :
 * ...
 */

package Jeu.data;

import static Jeu.Application.aff;
import static Jeu.utils.PlateauUtilitaire.affichePlateau;
import java.util.LinkedList;
import java.util.Random;

/**
 * class : Plateau
 * by    : rogeri
 * @author rogeri
 */
public class Plateau {
    private LinkedList<Carreau> plateau;
    private Chateau castleA;
    private Chateau castleB;
    private int turn = 0;
    
    public Plateau (int longueur, Chateau castleA, Chateau castleB) {
        plateau = new LinkedList<>();
        this.castleA=castleA;
        this.castleB=castleB;
        for (int i=0; i<longueur; i++) {
            plateau.add(new Carreau(this,i));
        }
    }

    public int getTurn() {
        return turn;
    }
    
    /**
     * Retourne le premier carreau pour un chateau donné.
     * @param castle Chateau
     * @return Carreau
     */
    public Carreau getFirstCarreau(Chateau castle) {
        if (castle.equals(castleA)) {
            return getCarreau(0);
        } else {
            return getCarreau(plateau.size()-1);
        }
    }
    
    public void gameOver(Chateau c) {
        aff.tellWinner(c);
    }
    
    /**
     * Fais agir chaque carreau :
     * Attaque ou Avance le premier Guerrier de chaque Chateau sur chaque Carreau
     */
    public void act() {
        if (getCastleA().won()) {
            gameOver(getCastleA());
        } else if (getCastleB().won()) {
            gameOver(getCastleB());
        } else {
            aff.setTurn(++turn);
            getCastleA().turn();
            if (Jeu.Graph.mode!="Client") {
                getCastleB().turn();
            }
            Chateau[] ch = getRandomCastle();
            Chateau A = ch[0];
            Chateau B = ch[1];

            for (Carreau c : plateau) {
                if (!c.empty(A)) { // Si le carreau contient des A
                    for (Guerrier g : c.getGuerriers().get(A)) {
                        g.act();
                    }
                }
                if (!c.empty(B)) { // Si le carreau contient des B
                    for (Guerrier g : c.getGuerriers().get(B)) {
                        g.act();
                    }
                }
            }
            affichePlateau(this);
        }
    }
    
    public Chateau getEnemy(Chateau c) {
        if (c.index()==0)
            return getCastleB();
        else {
            return getCastleA();
        }
    }
    
    /**
     * Retourne un Chateau aléatoire.
     * @return Chateau
     */
    public Chateau[] getRandomCastle() {
        Random alea = new Random();
        if (alea.nextBoolean()) {
        Chateau[] ch={getCastleA(),getCastleB()}; // Randomiser
        return ch;
        } else {
        Chateau[] ch={getCastleB(),getCastleA()}; // Randomiser
        return ch;
        }
    }
    
    /**
     * Retourne le premier Chateau
     * @return Chateau
     */
    public Chateau getCastleA () {
        return castleA;
    }
    
    /**
     * Retourne le deuxième Chateau
     * @return Chateau
     */
    public Chateau getCastleB() {
        return castleB;
    }
    
    /**
     * Retourne le carreau d'index i du plateau
     * @param i Index
     * @return Carreau
     */
    public Carreau getCarreau (int i) {
        if (i>=plateau.size()) {
            return null;
        } else {
            return plateau.get(i);
        }
    }
    
    /**
     * Retourne la taille du plateau
     * @return Taille
     */
    public int size() {
        return plateau.size();
    }
}
