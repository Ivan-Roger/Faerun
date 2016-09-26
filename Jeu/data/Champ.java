/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu.data;

/**
 *
 * @author Ivan
 */
public class Champ {
    private Field field;
    private int index;

    public int index() {
        return index;
    }

    public Field field() {
        return field;
    }

    public Champ(Field f, int i) {
        field=f;
        index=i;
    }
}
