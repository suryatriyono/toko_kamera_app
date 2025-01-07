
package com.tk.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author STNVC
 */
public class GradientPanelBorde extends JPanel{
    private Color color1 = Color.decode("#000000");
    private Color color2 = Color.decode("#4A00E0");

    public GradientPanelBorde() {
        setOpaque(false);
        setLayout(new BorderLayout());
    }

    public void setGradientColors(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        g2d.dispose();
    }
}
