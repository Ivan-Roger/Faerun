/*
 * Default License :
 * ...
 */
package Jeu.data;

import static Jeu.utils.ChateauUtilitaire.afficheGuerrierPret;
import static Jeu.utils.ChateauUtilitaire.afficheAjoutEntrainement;
import java.awt.Color;
import java.util.LinkedList;

/**
 * class : Chateau by : rogeri
 *
 * @author rogeri
 */
public class Chateau implements Comparable<Chateau> {
    private boolean gagne;
    private int ressources;
    private Color color;
    private String couleur;
    private int index;
    private String name;
    private static final int RESSOURCES_PAR_TOUR = 1;
    private static final int RESSOURCES_MAX = 5;
    private static final int UNITE_MAX = 10;
    private LinkedList<Guerrier> entrainement = new LinkedList<>();
    private LinkedList<Guerrier> prets = new LinkedList<>();
    
    public Chateau(String name, String couleur, int index) {
        gagne=false;
        ressources = 10;
        this.color = color(couleur);
        this.couleur=couleur;
        this.index=index;
        this.name=name;
    }

    public String getName() {
        return name;
    }
    
    public static Color color(String s) {
        switch (s) {
            case "Bleu":
                return Color.BLUE;
            case "Rouge":
                return Color.RED;
            case "Vert":
                return Color.GREEN;
            case "Cyan":
                return Color.CYAN;
            case "Orange":
                return Color.ORANGE;
            case "Rose":
                return Color.PINK;
            case "Jaune":
                return Color.YELLOW;
            default:
                return Color.BLACK;
        }
    }
    public static String color(Color c) {
        if (c.equals(Color.BLUE)) {
            return "Bleu";
        } else if (c.equals(Color.RED)) {
            return "Rouge";
        } else if (c.equals(Color.GREEN)) {
            return "Vert";
        } else if (c.equals(Color.CYAN)) {
            return "Cyan";
        } else if (c.equals(Color.ORANGE)) {
            return "Orange";
        } else if (c.equals(Color.PINK)) {
            return "Rose";
        } else if (c.equals(Color.YELLOW)) {
            return "Jaune";
        } else {
            return "Noir";
        }
    }    
    
    public Color getColor() {
        return color;
    }

    public String getNomCouleur() {
        return couleur;
    }
    
    public void giveOrders() {
        for (Guerrier g : prets) {
            g.resetDeplacement();
        }
    }

    public void turn() {
        giveOrders();
        Jeu.utils.ChateauUtilitaire.afficheEntrainement(this);
        while (entrainement.size() > 0 && ressources > 0) { // Si il reste des personnes a entrainer
            int cost = entrainement.peek().getCost();
            if (ressources >= cost) { // Si assez de ressources pour entrainer completement
                entrainement.peek().train(cost); // Entrainement du guerrier
                ressources = ressources - cost;  // Retrait des ressources
                afficheGuerrierPret(entrainement.peek());
                entrainement.peek().getFirstCarreau().enter(entrainement.peek()); // Guerrier pret
                prets.add(entrainement.poll());
            } else { // Sinon
                entrainement.peek().train(ressources); // Entrainement du guerrier
                ressources = 0; // Retrait des ressources
            }
        }
        if (ressources+RESSOURCES_PAR_TOUR<=RESSOURCES_MAX) {
            ressources += RESSOURCES_PAR_TOUR;
        }
    }

    public void addGuerrier(Guerrier g) {
        if (prets.size()+entrainement.size()<UNITE_MAX) {
            if ((Jeu.Graph.mode!="Client")) {
                afficheAjoutEntrainement(this, g);
            }
            entrainement.add(g);
        }
    }

    public int getRessources() {
        return ressources;
    }
    
    public boolean won() {
        return gagne;
    }
    
    public int index() {
        return index;
    }
    
    public void setWon() {
        gagne=true;
    }

    public LinkedList<Guerrier> getGuerriersPrets() {
        return prets;
    }

    public LinkedList<Guerrier> getGuerriersPasPrets() {
        return entrainement;
    }
    
    @Override
    public int compareTo(Chateau o) {
        return Integer.compare(index,o.index());
    }
}
