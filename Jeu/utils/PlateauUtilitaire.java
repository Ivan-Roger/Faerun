package Jeu.utils;

import static Jeu.Graph.serv;
import Jeu.data.Guerrier;
import Jeu.data.Carreau;
import Jeu.data.Champ;
import Jeu.data.Field;
import Jeu.data.Plateau;
import static Jeu.utils.GuerrierUtilitaire.printlnUnite;
import java.util.List;
import java.util.Random;

/**
 *
 * @author fbm rev hb
 */
public class PlateauUtilitaire {

    private static final Random RANDOM = new Random();

    // nombre de cases des différents plateaux
        /**
     * lancé d'un dé trois faces
     * @return Entier entre 1 et 3
     */
    public static int De3() {
        return RANDOM.nextInt(3)+1;
    }

    /**
     * nombreLances d'un dé 3 faces
     * @param nombreLances Nombre de lancés
     * @return Somme des lancés
     */
    public static int De3(int nombreLances) {
        int somme = 0;
        for (int i = 0; i < nombreLances; i++) {
            somme = somme + De3();
        }
        return somme;
    }

        ///////////////////////
    // AFFICHAGE
    
    /**
     * Affichage des Guerriers sur chaque Carreaux du plateau
     * @param p Plateau
     */
    public static void affichePlateau(Plateau p) {
        for (int i=0; i<p.size(); i++) {
            afficheCarreau(p.getCarreau(i),p);
        }
    }
    
    /**
     * Affichage de l'équipe gagnante
     * @param plateau
     */
    

    /**
     * Affichage de tous les Guerriers d'un Carreau
     * @param c Chateau
     * @param p Plateau
     */
    public static void afficheCarreau(Carreau c, Plateau p) {
        if (Jeu.Graph.mode!="Client") {
            Jeu.Application.aff.printL(new Champ(Field.carreauN,c.index()),c.getGuerriers().get(p.getCastleA()).size()+" - "+c.getGuerriers().get(p.getCastleB()).size());
        }
        if (Jeu.Graph.mode=="Server") {
            serv.println("disp "+Field.carreauN.toString()+" "+c.index()+" "+c.getGuerriers().get(p.getCastleA()).size()+" - "+c.getGuerriers().get(p.getCastleB()).size());
        }
        for (Guerrier g : c.getGuerriers().get(p.getCastleA())) {
            printlnUnite(new Champ(Field.carreauT, c.index()),g);
        }
        for (Guerrier g : c.getGuerriers().get(p.getCastleB())) {
            printlnUnite(new Champ(Field.carreauB, c.index()),g);
        }
    }
    
}
