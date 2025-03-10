package com.tk.component;

import com.tk.event.EventMenuSelected;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author STNVC
 */
public final class Menu extends javax.swing.JPanel {

    private EventMenuSelected event;

    public void addEventMenuSelected(EventMenuSelected event) {
        this.event = event;
        listMenu1.addEventMenuSelected(event);
    }

    public Menu() {
        initComponents();
        setOpaque(false);
        listMenu1.setOpaque(false);
        ImageIcon originIcon = (ImageIcon) jLabel1.getIcon();

        Image img = originIcon.getImage();

        Image scaledImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        ImageIcon resizedIcon = new ImageIcon(scaledImg);

        jLabel1.setIcon(resizedIcon);

        initMenu();
    }

    public void initMenu() {
        listMenu1.addItem(new com.tk.model.MenuModel("dashboard-bx", "Dashboard", com.tk.model.MenuModel.MenuType.MENU));
        listMenu1.addItem(new com.tk.model.MenuModel("buyer", "Pelanggan", com.tk.model.MenuModel.MenuType.MENU));
        listMenu1.addItem(new com.tk.model.MenuModel("supplier", "Pemasok", com.tk.model.MenuModel.MenuType.MENU));
        listMenu1.addItem(new com.tk.model.MenuModel("products", "Barang", com.tk.model.MenuModel.MenuType.MENU));
        listMenu1.addItem(new com.tk.model.MenuModel("separate", "Kategori", com.tk.model.MenuModel.MenuType.MENU));
        listMenu1.addItem(new com.tk.model.MenuModel("cargo", "Pembelian", com.tk.model.MenuModel.MenuType.MENU));
        listMenu1.addItem(new com.tk.model.MenuModel("cash-on-delivery", "Penjualan", com.tk.model.MenuModel.MenuType.MENU));
        listMenu1.addItem(new com.tk.model.MenuModel("", "", com.tk.model.MenuModel.MenuType.EMPTY));

        listMenu1.addItem(new com.tk.model.MenuModel("", "Lainya", com.tk.model.MenuModel.MenuType.TITLE));
        listMenu1.addItem(new com.tk.model.MenuModel("report", "Laporan", com.tk.model.MenuModel.MenuType.MENU));
        listMenu1.addItem(new com.tk.model.MenuModel("", "", com.tk.model.MenuModel.MenuType.EMPTY));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMoving = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        listMenu1 = new com.tk.swing.ListMenu<>();

        panelMoving.setBackground(new java.awt.Color(255, 0, 51));
        panelMoving.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tk/icon/supermaket.png"))); // NOI18N
        jLabel1.setText("TOKO KAMERA");

        javax.swing.GroupLayout panelMovingLayout = new javax.swing.GroupLayout(panelMoving);
        panelMoving.setLayout(panelMovingLayout);
        panelMovingLayout.setHorizontalGroup(
            panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelMovingLayout.setVerticalGroup(
            panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMoving, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(listMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panelMoving, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintChildren(Graphics grphcs) {
        GradientPaint g = new GradientPaint(0, 20, Color.decode("#000000"), 0, getHeight(), Color.decode("#4A00E0"));
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
        super.paintChildren(grphcs);
    }

    private int x;
    private int y;

    public void initMoving(JFrame fram) {
        panelMoving.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent me) {
                x = me.getX();
                y = me.getY();
            }
        });
        panelMoving.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                fram.setLocation(me.getXOnScreen() - x, me.getYOnScreen() - y);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private com.tk.swing.ListMenu<String> listMenu1;
    private javax.swing.JPanel panelMoving;
    // End of variables declaration//GEN-END:variables
}
