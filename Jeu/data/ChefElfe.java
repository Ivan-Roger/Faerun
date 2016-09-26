/*
 * Default License :
 * ...
 */

package Jeu.data;

import static Jeu.utils.GuerrierUtilitaire.printlnFight;
import static Jeu.utils.PlateauUtilitaire.De3;

/**
 * class : ChefElfe
 * by    : rogeri
 * @author rogeri
 */
public class ChefElfe extends Elfe {
    private static final int COEF_FORCE = 2;
    private static final int COUT_ENTRAINEMENT = 4;

    public ChefElfe(Chateau castle, Plateau plateau) {
        super(castle, plateau);
    }
    
    @Override
    public int getCost() {
        return COUT_ENTRAINEMENT-this.getTrain();
    }
    
    @Override
    public int getForce () {
        return super.getForce()*COEF_FORCE;
    }
}
