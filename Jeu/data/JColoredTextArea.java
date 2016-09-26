/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu.data;

import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author Ivan
 */
public class JColoredTextArea extends JTextPane {
        public void append(String msg, Color c)
        {
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

            aset = sc.addAttribute(aset, StyleConstants.Bold, false);
            aset = sc.addAttribute(aset, StyleConstants.Italic, false);
            aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
            aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

            int len = this.getDocument().getLength();
            this.setCaretPosition(len);
            this.setCharacterAttributes(aset, false);
            this.replaceSelection(msg);
        }
        public void append(String msg, Color c, Style st)
        {
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
            
            aset = sc.addAttribute(aset, StyleConstants.Bold, st.isBold());
            aset = sc.addAttribute(aset, StyleConstants.Italic, st.isItalic());
            aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
            aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

            int len = this.getDocument().getLength();
            this.setCaretPosition(len);
            this.setCharacterAttributes(aset, false);
            this.replaceSelection(msg);
        }
        public void append(String msg, Style st)
        {
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);
            
            aset = sc.addAttribute(aset, StyleConstants.Bold, st.isBold());
            aset = sc.addAttribute(aset, StyleConstants.Italic, st.isItalic());
            aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
            aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

            int len = this.getDocument().getLength();
            this.setCaretPosition(len);
            this.setCharacterAttributes(aset, false);
            this.replaceSelection(msg);
        }
        
        public void append(String msg)
        {
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);

            aset = sc.addAttribute(aset, StyleConstants.Bold, false);
            aset = sc.addAttribute(aset, StyleConstants.Italic, false);
            aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
            aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

            int len = this.getDocument().getLength();
            this.setCaretPosition(len);
            this.setCharacterAttributes(aset, false);
            this.replaceSelection(msg);
        }
        
        public JColoredTextArea() {
            super();
        }
    }
