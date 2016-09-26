/*
 * Default License :
 * ...
 */

package Jeu.data;

/**
 * class : ChefNain
 * by    : rogeri
 * @author rogeri
 */
public class ChefNain extends Nain {
    private static final int COEF_RESISTANCE = 2;
    private static final int COUT_ENTRAINEMENT = 3;
    
    public ChefNain (Chateau castle, Plateau plateau) {
        super(castle, plateau);
    }
    
    @Override
    public int getCost() {
        return COUT_ENTRAINEMENT-this.getTrain();
    }
    
    @Override
    public int hurt (int dmg) {
        return super.hurt(dmg/COEF_RESISTANCE);
    }
    
    @Override
    public int picHeigth() {
        return 85;
    }
}
