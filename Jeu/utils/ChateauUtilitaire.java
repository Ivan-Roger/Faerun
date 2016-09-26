package Jeu.utils;

import static Jeu.Graph.serv;
import Jeu.data.Champ;
import Jeu.data.Chateau;
import Jeu.data.Field;
import Jeu.data.Guerrier;
import static Jeu.utils.GuerrierUtilitaire.printUnite;
import static Jeu.utils.GuerrierUtilitaire.printlnUnite;


/**
 *
 * @author fbm rev hb
 */
public class ChateauUtilitaire {

    
    ///////////////////////
    // METHODES D'AFFICHAGE
  	// A COMPLETER
    public static void afficheEntrainement(Chateau p) {
        Champ ch = new Champ(Field.chateau, p.index());
        Jeu.Application.aff.clear(ch);
        afficheRessources(p);
        if (p.getGuerriersPasPrets().size()>0) {
            if (Jeu.Graph.mode=="Server"&&p.index()==1) {
                serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"--- Entrainement ---");
                serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"~");
            } else {
                Jeu.Application.aff.print(ch,"--- Entrainement ---\n");
            }
            for (int i=0; i<p.getGuerriersPasPrets().size(); i++) {
                if (Jeu.Graph.mode=="Server"&&p.index()==1) {
                    serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"   > ");
                } else {
                    Jeu.Application.aff.print(ch,"   > ");
                }
                printlnUnite(ch,p.getGuerriersPasPrets().get(i));
            }
        }
    }
    
    public static void afficheGuerrierPret(Guerrier g) {
        Champ ch = new Champ(Field.chateau, g.getCastle().index());
        if (Jeu.Graph.mode=="Server"&&g.getCastle().index()==1) {
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"   --> ");
        } else {
            Jeu.Application.aff.print(ch,"   --> ");
        }
        printUnite(ch,g);
        if (Jeu.Graph.mode=="Server"&&g.getCastle().index()==1) {
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+" pret au combat !");
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"~");
        } else {
            Jeu.Application.aff.print(ch," pret au combat !\n");    
        }
            
    }
    
    public static void afficheRessources(Chateau p) {
        Champ ch = new Champ(Field.chateau, p.index());
        if (Jeu.Graph.mode=="Server"&&p.index()==1) {
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"Vous avez "+p.getRessources()+" ressources.");
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"~");
        } else {
            Jeu.Application.aff.print(ch,"Vous avez "+p.getRessources()+" ressources.\n");
        }
    }
    
    public static void afficheAjoutEntrainement(Chateau c, Guerrier g) {
        Champ ch = new Champ(Field.chateau, c.index());
        if (Jeu.Graph.mode=="Server"&&c.index()==1) {
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+g.getClass().getSimpleName()+" ajouté a l'entrainement");
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"~");
        } else {
            Jeu.Application.aff.print(ch,g.getClass().getSimpleName()+" ajouté a l'entrainement\n");
        }
    }
}
