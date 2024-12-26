/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tk.swing;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;

/**
 *
 * @author STNVC
 */
public class ScrollBarCustom extends JScrollBar{

    public ScrollBarCustom() {
        setUI(new ModernScrollBarUI2());
        setPreferredSize(new Dimension(8,8));
        setForeground(new Color(180,180,180));
        setBackground(Color.WHITE);
        setUnitIncrement(20);
    }
    
}
