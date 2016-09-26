/*
 * Default License :
 * ...
 */

package Jeu.data;

import static Jeu.utils.GuerrierUtilitaire.afficheGuerrierMort;
import static Jeu.utils.GuerrierUtilitaire.printlnFight;
import static Jeu.utils.PlateauUtilitaire.De3;
import static Jeu.utils.Timeout.setTimeout;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/**
 * class : Guerrier
 * by    : rogeri
 * @author rogeri
 */
public abstract class Guerrier {
    private BufferedImage sprite;
    private static final int FORCE_BASE = 10;
    private static final int PV_BASE = 100;
    private static final int DEPLACEMENT_BASE = 1;
    private static final int ATTACK_RANGE_BASE = 0;
    private int entrainement=0;
    private int pv;
    private Chateau castle;
    private Carreau carreau;
    private Plateau plateau;
    private boolean onPlateau = false;
    private int deplacement;
    private String mode;
    private int frame;
    private boolean pic_back;
    
    public Guerrier (Chateau castle, Plateau plateau) {
        try {
            sprite=ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("assets/"+this.getClass().getSimpleName()+".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        pv=PV_BASE;
        this.castle=castle;
        this.plateau=plateau;
        mode="stand";
        frame=0;
        pic_back=false;
    }

    private void setMode(String m) {
        if (!m.equals(mode)) {
            frame=0;
            pic_back=false;
        }
        if (m=="attack" || m=="move" || m=="die") {
            mode=m;
        } else {
            mode="stand";
        }
    }
    
    private int getModeID() {
        switch (mode) {
            case "attack": // 8 Sprites
                return 2;
            case "move": // 8 Sprites
                return 1;
            case "die": // 7 Sprites
                return 6;
            case "stand": // 6 sprites
                return 0;
            default :
                return 0;
        }
    }
    
    public BufferedImage getImage() {
        if (mode=="stand") {
            if (frame==5) {
                pic_back=true;
            } else if (frame==0) {
                pic_back=false;
            }
        } else {
            if (frame==5) {
                frame=0;
            }
        }
        System.out.println("PIC "+getClass().getSimpleName()+" : "+picWidth()+"x"+picHeigth()+" - "+mode+" "+frame+" - "+(frame * picWidth())+" "+(getModeID() * picHeigth()));
        BufferedImage img =  sprite.getSubimage(frame * picWidth(),getModeID() * picHeigth(),picWidth(),picHeigth());
        if (pic_back) {frame--;} else {frame++;}
        return img;
    }
    
    public abstract int picHeigth();
    public abstract int picWidth();
    
    public int getDeplacement() {
        return deplacement;
    }

    public void resetDeplacement() {
        deplacement=DEPLACEMENT_BASE;
    }
    
    public int getAttackRange() {
        return ATTACK_RANGE_BASE;
    }
        
    public void walk() {
        setMode("move");
        deplacement--;
        int index = getCarreau().nextIndex(getCastle());
        if (index>=0&&index<plateau.size()) {
            Carreau nextC = plateau.getCarreau(index);
            nextC.enter(this);
        } else {
            getCastle().setWon();
            deplacement=0;
            setMode("stand");
        }
        setTimeout(1000,new Runnable() {
            public void run() {
                setMode("stand");
            }
        });
    }
    
    public int getForce() {
        return FORCE_BASE;
    }
    
    public Carreau getFirstCarreau() {
        return plateau.getFirstCarreau(castle);
    }
    
    public Carreau getCarreau() {
        return carreau;
    }

    public void setCarreau(Carreau carreau) {
        this.carreau = carreau;
        onPlateau=true;
    }
    
    public boolean onPlateau() {
        return onPlateau;
    }
    
    public Chateau getCastle () {
        return castle;
    }
    
    public abstract int getCost();
    
    public int getTrain() {
        return entrainement;
    }
    
    public boolean pret() {
        return getCost()==0;
    }
    
    public void train (int n) {
        entrainement+=n;
    }
    
    public int hurt (int dmg) {
        int pvOld;
        if (pv>=dmg) {
            pv-=dmg;
            return dmg;
        } else {
            setMode("die");
            pvOld=pv;
            pv=0;
            afficheGuerrierMort(this);
            this.getCarreau().leave(this);
            this.getCastle().getGuerriersPrets().remove(this);
            return pvOld;
        }
    }
    
    public void act () {
        int value = getAttackRange();
        if (getCastle().index()==0) {
            while (getCarreau().index()+value>=plateau.size()) {
                value--;
            }
        } else {
            while (getCarreau().index()-value<0) {
                value--;
            }
        }
        attack(getAttackRange());
        if (getCastle().index()==0) {
            if (plateau.getCarreau(getCarreau().index()+value).empty(plateau.getEnemy(getCastle()))) {
                if (getDeplacement()>0 && getCarreau().empty(plateau.getEnemy(getCastle()))) {
                    walk();
                    if (!getCastle().won()) {
                        act();
                    }
                }
            }
        } else {
            if (plateau.getCarreau(getCarreau().index()-value).empty(plateau.getEnemy(getCastle()))) {
                if (getDeplacement()>0 && getCarreau().empty(plateau.getEnemy(getCastle()))) {
                    walk();
                    if (!getCastle().won()) {
                        act();
                    }
                }
            }
        }
    }
    
    public void attack(int dist) {
        Carreau car = getCarreau();
        int i=car.index();
        int init = i;
        if (getCastle().index()==0) {
            while (plateau.getCarreau(i).empty(plateau.getEnemy(getCastle())) && Math.abs(init-i)<dist && i<plateau.size()) {
                i++;
            }
            car = plateau.getCarreau(i);
        } else {
            while (plateau.getCarreau(i).empty(plateau.getEnemy(getCastle())) && Math.abs(init-i)<dist && i>0) {
                i--;
            }
            car = plateau.getCarreau(i);
        }
        Guerrier g = car.getFirstOponent(this);
        int dmgD = De3(this.getForce());
        if (g!=null) {
            setMode("attack");
            printlnFight(this, g, dmgD, g.hurt(dmgD));
        }
        setTimeout(1000,new Runnable() {
            public void run() {
                setMode("stand");
            }
        });
    }
    
    public int getPV () {
        return this.pv;
    }
    
    public boolean dead () {
        return this.pv==0;
    }
    
    
}
