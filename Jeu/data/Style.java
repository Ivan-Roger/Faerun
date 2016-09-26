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
public enum Style {
    Bold,
    Italic,
    ItalicBold,
    Default;

    public boolean isBold() {
        return this.equals(Bold) || this.equals(ItalicBold);
    }
    public boolean isItalic() {
        return this.equals(Italic) || this.equals(ItalicBold);
    }
}
