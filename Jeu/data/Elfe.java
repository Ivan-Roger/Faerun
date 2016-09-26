/*
 * Default License :
 * ...
 */

package Jeu.data;

import static Jeu.utils.PlateauUtilitaire.De3;
import static Jeu.utils.GuerrierUtilitaire.printlnFight;

/**
 * class : Elfe
 * by    : rogeri
 * @author rogeri
 */
public class Elfe extends Guerrier {
    private static final int COEF_FORCE = 2;
    private static final int COUT_ENTRAINEMENT = 2;
    private static final int ATTACK_RANGE = 1;

    public Elfe(Chateau castle, Plateau plateau) {
        super(castle, plateau);
    }
    
    public int getCost() {
        return COUT_ENTRAINEMENT-this.getTrain();
    }
    
    @Override
    public int getAttackRange() {
        return ATTACK_RANGE;
    }
    
    @Override
    public int getForce () {
        return super.getForce()*COEF_FORCE;
    }

    @Override
    public int picHeigth() {
        return 100;
    }

    @Override
    public int picWidth() {
        return 85;
    }
}
