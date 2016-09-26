/*
 * Default License :
 * ...
 */

package Jeu.data;

/**
 * class : Nain
 * by    : rogeri
 * @author rogeri
 */
public class Nain extends Guerrier {
    private static final int COEF_RESISTANCE = 2;
    private static final int COUT_ENTRAINEMENT = 1;
    
    public Nain(Chateau castle, Plateau plateau) {
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
        return 80;
    }

    @Override
    public int picWidth() {
        return 93; //100
    }
}
