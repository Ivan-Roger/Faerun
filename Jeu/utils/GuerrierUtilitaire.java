package Jeu.utils;

import Jeu.data.Champ;
import Jeu.data.Field;
import Jeu.data.Guerrier;
import static Jeu.Graph.client;
import static Jeu.Graph.serv;
import static Jeu.data.Chateau.color;

/**import data.ChefElfe;
import data.ChefNain;
import data.Couleur;
import data.Elfe;
import data.Guerrier;
import data.Nain;
 **/

/**
 * Classe qui permet de regrouper des méthodes relatives à tous types de
 * guerriers
 *
 * @author fbm
 */
public class GuerrierUtilitaire {
    //////////////////////////////////
    // METHODES D'AFFICHAGE
    public static void printUnite(Champ ch, Guerrier unite) {
        // A COMPLETER
        if (Jeu.Graph.mode=="Server"&&ch.field().equals(Field.chateau)&&ch.index()==1) {
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+color(unite.getCastle().getColor())+" "+unite.getClass().getSimpleName());
            if (unite.onPlateau()&&ch.field().toString().equals("log")) {
                serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"("+(unite.getCarreau().index()+1)+")");
            }
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"["+unite.getPV()+" PV]");
        } else if (Jeu.Graph.mode=="Server"&&(ch.field().equals(Field.log)||(ch.field().equals(Field.carreauT)||ch.field().equals(Field.carreauB)))){
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+color(unite.getCastle().getColor())+" "+unite.getClass().getSimpleName());
            if (unite.onPlateau()&&ch.field().toString().equals("log")) {
                serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"("+(unite.getCarreau().index()+1)+")");
            }
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"["+unite.getPV()+" PV]");
            Jeu.Application.aff.print(ch,unite.getClass().getSimpleName(),unite.getCastle().getColor());
            if (unite.onPlateau()&&ch.field().toString().equals("log")) {
                Jeu.Application.aff.print(ch,"("+(unite.getCarreau().index()+1)+")");
            }
            Jeu.Application.aff.print(ch,"["+unite.getPV()+" PV]");
        } else {
            Jeu.Application.aff.print(ch,unite.getClass().getSimpleName(),unite.getCastle().getColor());
            if (unite.onPlateau()&&ch.field().toString().equals("log")) {
                Jeu.Application.aff.print(ch,"("+(unite.getCarreau().index()+1)+")");
            }
            Jeu.Application.aff.print(ch,"["+unite.getPV()+" PV]");
        }
        //AIDE : si vous souhaitez afficher le nom de la classe d’un objet, vous pouvez utiliser les méthodes : nainBleu.getClass().getName() et nainBleu.getClass().getSimpleName().
    }

    public static void printlnUnite(Champ ch, Guerrier unite) {
        // A COMPLETER
        printUnite(ch, unite);
        if (Jeu.Graph.mode=="Server"&&ch.field().equals(Field.chateau)&&ch.index()==1) {
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"~");
        } else if (Jeu.Graph.mode=="Server"&&(ch.field().equals(Field.log)||(ch.field().equals(Field.carreauT)||ch.field().equals(Field.carreauB)))){
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"~");
            Jeu.Application.aff.print(ch,"\n");
        } else {
            Jeu.Application.aff.print(ch,"\n");
        }
    }

    public static void printlnFight(Guerrier g1, Guerrier g2, int dmgD, int dmgT) {
        Champ ch = new Champ(Field.log, 0);
        Jeu.Application.aff.print(ch,"COMBAT : ");
        if (Jeu.Graph.mode=="Server") {
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"COMBAT : ");
        }
        printUnite(ch,g1);
        Jeu.Application.aff.print(ch," attaque ");
        if (Jeu.Graph.mode=="Server") {
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+" attaque ");
        }
        printUnite(ch,g2);
        Jeu.Application.aff.print(ch," -> Dégats : "+dmgD+" données, "+dmgT+" subis.\n");
        if (Jeu.Graph.mode=="Server") {
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+" -> Dégats : "+dmgD+" données, "+dmgT+" subis.");
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"~");
        }
    }
    
    // A COMPLETER : d'autres méthodes d'affichage si vous le souhaitez
    public static void afficheGuerrierMort(Guerrier g) {
        Champ ch = new Champ(Field.log, 0);
        printUnite(ch, g);
        if (Jeu.Graph.mode=="Server"&&ch.field().equals(Field.chateau)&&ch.index()==1) {
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+" est mort sur le carreau "+(g.getCarreau().index()+1)+".");
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"~");
        } else if (Jeu.Graph.mode=="Server"&&ch.field().equals(Field.log)){
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+" est mort sur le carreau "+(g.getCarreau().index()+1)+".");
            serv.println("disp "+ch.field().toString()+" "+ch.index()+" "+"Default"+" "+"Default"+" "+"~");
            Jeu.Application.aff.print(ch," est mort sur le carreau "+(g.getCarreau().index()+1)+".\n");
        } else {
            Jeu.Application.aff.print(ch," est mort sur le carreau "+(g.getCarreau().index()+1)+".\n");
        }
    }
}
