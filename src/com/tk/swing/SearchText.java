package com.tk.swing;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author STNVC
 */
public class SearchText extends JTextField {

    private final String hint = "Search here...";

    public SearchText() {
        // Set default border and selection color if needed
        setBorder(new EmptyBorder(5, 5, 5, 5));  // You can adjust this as needed
//        setSelectionColor(new Color(220, 204, 182)); // You can customize this color
        setForeground(Color.decode("#7600FF"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (getText().length() == 0) {  // Show hint only when there's no text
            int h = getHeight();
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Insets ins = getInsets();
            FontMetrics fm = g.getFontMetrics();

            // Use a standard color for the hint text
            g.setColor(new Color(118, 0, 255));  // A light gray color for the hint text

            // Draw the hint text, ensuring it's centered vertically
            int x = ins.left;
            int y = (h + fm.getAscent() - fm.getDescent()) / 2;

            g.drawString(hint, x, y);
        }
    }

}
