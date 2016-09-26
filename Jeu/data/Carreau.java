/*
 * Default License :
 * ...
 */

package Jeu.data;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * class : Carreau
 * by    : rogeri
 * @author rogeri
 */
public class Carreau {
    private TreeMap<Chateau,List<Guerrier>> content;
    private int index;
    
    public Carreau (Plateau p, int index) {
        content = new TreeMap();
        this.index=index;
        content.put(p.getCastleA(), new CopyOnWriteArrayList<Guerrier>());
        content.put(p.getCastleB(), new CopyOnWriteArrayList<Guerrier>());
    }
    
    /**
     * Permet a un Guerrier de quitter une case
     * @param g Guerrier
     */
    public void leave(Guerrier g) {
        content.get(g.getCastle()).remove(g);
    }
    
    /**
     * Déplace un Guerrier sur un carreau
     * @param g Guerrier
     */
    public void enter(Guerrier g) {
        if (g.onPlateau()) {
            g.getCarreau().leave(g);
        }
        content.get(g.getCastle()).add(g);
        g.setCarreau(this);
    }
    
    /**
     * Retourne le premier Guerrier enemi pour un Guerrier donné
     * @param g Guerrier
     * @return Guerrier
     */
    public Guerrier getFirstOponent(Guerrier g) {
        Chateau c = content.higherKey(g.getCastle());
        if (c==null) {
            c = content.firstKey();
        }
        if (!content.get(c).isEmpty()) {
            return content.get(c).get(0);
        } else {
            return null;
        }
    }
    
    /**
     * Retourne l'index du carreau suivant pour un chateau donné.
     * @param cas Chateau
     * @return Index du Carreau suivant
     */
    public int nextIndex (Chateau cas) {
        if (cas.index()==0) {
            return index()+1;
        } else {
            return index()-1;
        }
    }
    
    /**
     * Retourne l'index du carreau precedent pour un chateau donné.
     * @param cas Chateau
     * @return Index du Carreau precedent
     */
    public int prevIndex (Chateau cas) {
        if (cas.index()==0) {
            return index()-1;
        } else {
            return index()+1;
        }
    }
    
    /**
     * Verifie qu'un Carreau est vide de Guerriers pour un Chateau donné
     * @param c Chateau
     * @return Vrai ou Faux (Booléen)
     */
    public boolean empty (Chateau c) {
        return content.get(c).size()==0;
    }
    /**
     * Retourne le contenu du Carreau
     * @return TreeMap de Guerriers
     */
    public TreeMap<Chateau,List<Guerrier>> getGuerriers() {
        return content;
    }
    
    public int index () {
        return index;
    }
}
