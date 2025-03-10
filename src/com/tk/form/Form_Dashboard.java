/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tk.form;

import com.tk.controller.DatabaseControllers;
import com.tk.model.BarangModel;
import com.tk.model.CardModel;
import com.tk.model.StatusTypeModel;
import com.tk.swing.ScrollBar;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author STNVC
 */
public class Form_Dashboard extends javax.swing.JPanel {

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    private static final int LOW_STOCK_THRESHOLD = 10;

    public Form_Dashboard() {
        initComponents();
        setOpaque(false);
        initDashboard();
        
        // add row 
        spTable.setVerticalScrollBar(new ScrollBar());
        spTable.getVerticalScrollBar().setBackground(Color.WHITE);
        spTable.getViewport().setBackground(Color.WHITE);
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        spTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        table.setInnerCellStatus(true);
        table.setColStatus(4);
        table.addRow(new Object[]{"Andrew Strauss", "andrewstrauss@gmail.com", "Editor", "25 Apr,2018", StatusTypeModel.APPROVED});
        table.addRow(new Object[]{"Ross Kopelman", "rosskopelman@gmail.com", "Subscriber", "25 Apr,2018", StatusTypeModel.APPROVED});
        table.addRow(new Object[]{"Mike Hussy", "mikehussy@gmail.com", "Admin", "25 Apr,2018", StatusTypeModel.REJECT});
        table.addRow(new Object[]{"Kevin Pietersen", "kevinpietersen@gmail.com", "Admin", "25 Apr,2018", StatusTypeModel.PENDING});
        table.addRow(new Object[]{"Andrew Strauss", "andrewstrauss@gmail.com", "Editor", "25 Apr,2018", StatusTypeModel.APPROVED});
        table.addRow(new Object[]{"Ross Kopelman", "rosskopelman@gmail.com", "Subscriber", "25 Apr,2018", StatusTypeModel.APPROVED});
        table.addRow(new Object[]{"Mike Hussy", "mikehussy@gmail.com", "Admin", "25 Apr,2018", StatusTypeModel.REJECT});
        table.addRow(new Object[]{"Kevin Pietersen", "kevinpietersen@gmail.com", "Admin", "25 Apr,2018", StatusTypeModel.PENDING});
        table.addRow(new Object[]{"Kevin Pietersen", "kevinpietersen@gmail.com", "Admin", "25 Apr,2018", StatusTypeModel.PENDING});
        table.addRow(new Object[]{"Andrew Strauss", "andrewstrauss@gmail.com", "Editor", "25 Apr,2018", StatusTypeModel.APPROVED});
        table.addRow(new Object[]{"Ross Kopelman", "rosskopelman@gmail.com", "Subscriber", "25 Apr,2018", StatusTypeModel.APPROVED});
        table.addRow(new Object[]{"Mike Hussy", "mikehussy@gmail.com", "Admin", "25 Apr,2018", StatusTypeModel.REJECT});
        table.addRow(new Object[]{"Kevin Pietersen", "kevinpietersen@gmail.com", "Admin", "25 Apr,2018", StatusTypeModel.PENDING});
    }

    private void initDashboard() {
        // Inisialisasi card
        card1.setData(new CardModel(new ImageIcon(getClass().getResource("/com/tk/icon/sale.png")), "Total Penjualan Hari Ini", formatCurrency(DatabaseControllers.getTotalSalesToday()), ""));
        card2.setData(new CardModel(new ImageIcon(getClass().getResource("/com/tk/icon/new-product.png")), "Produk Terjual Hari Ini", String.valueOf(DatabaseControllers.getProductsSoldToday()), ""));
        card3.setData(new CardModel(new ImageIcon(getClass().getResource("/com/tk/icon/out-of-stock.png")), "Produk Stok Rendah", String.valueOf(DatabaseControllers.getLowStockProductCount()), ""));

        // Setup tabel
        setupTable();
        loadTopSellingProducts();
    }

    private void setupTable() {
        spTable.setVerticalScrollBar(new ScrollBar());
        spTable.getVerticalScrollBar().setBackground(Color.WHITE);
        spTable.getViewport().setBackground(Color.WHITE);
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        spTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        table.setInnerCellStatus(true);
        table.setColStatus(4);

        // Ubah model tabel
        table.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Produk", "Nama Produk", "Kategori", "Stok", "Status"}
        ) {
            boolean[] canEdit = new boolean[]{false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
    }

    private void loadTopSellingProducts() {
        List<BarangModel> topProducts = DatabaseControllers.getTopSellingProducts(10);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (BarangModel barang : topProducts) {
            model.addRow(new Object[]{
                barang.getIdBarang(),
                barang.getNamaBarang(),
                barang.getIdKategori(),
                barang.getStok(),
                getStockStatus(barang.getStok())
            });
        }
    }

    private StatusTypeModel getStockStatus(int stock) {
        if (stock > LOW_STOCK_THRESHOLD * 2) {
            return StatusTypeModel.APPROVED;
        }
        if (stock > LOW_STOCK_THRESHOLD) {
            return StatusTypeModel.PENDING;
        }
        return StatusTypeModel.REJECT;
    }

    private String formatCurrency(BigDecimal amount) {
        return currencyFormat.format(amount);
    }

    public void refreshDashboard() {
//        card1.setData(new CardModel(new ImageIcon(getClass().getResource("/com/tk/icon/stock.png")), "Stock Total", "200000", "Incrase by 60%"));
//        card1.setData(new CardModel(new ImageIcon(getClass().getResource("/com/tk/icon/sale.png")), "Total Penjualan Hari Ini", formatCurrency(DatabaseControllers.getTotalSalesToday()), ""));
//        card2.setData(new CardModel(new ImageIcon(getClass().getResource("/com/tk/icon/new-product.png")), "Produk Terjual Hari Ini", String.valueOf(DatabaseControllers.getProductsSoldToday()), ""));
//        card3.setData(new CardModel(new ImageIcon(getClass().getResource("/com/tk/icon/out-of-stock.png")), "Produk Stok Rendah", String.valueOf(DatabaseControllers.getLowStockProductCount()), ""));
        loadTopSellingProducts();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        card1 = new com.tk.component.Card();
        card2 = new com.tk.component.Card();
        card3 = new com.tk.component.Card();
        panelBorder1 = new com.tk.swing.PanelBorder();
        jLabel1 = new javax.swing.JLabel();
        spTable = new javax.swing.JScrollPane();
        table = new com.tk.swing.Table();

        jPanel1.setLayout(new java.awt.GridLayout(1, 0, 10, 10));

        card1.setColor1(new java.awt.Color(142, 142, 250));
        card1.setColor2(new java.awt.Color(123, 123, 245));
        jPanel1.add(card1);

        card2.setColor1(new java.awt.Color(186, 123, 247));
        card2.setColor2(new java.awt.Color(167, 95, 236));
        jPanel1.add(card2);

        card3.setColor1(new java.awt.Color(241, 208, 62));
        card3.setColor2(new java.awt.Color(211, 184, 61));
        jPanel1.add(card3);

        panelBorder1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(127, 127, 127));
        jLabel1.setText("Table Desing");

        spTable.setBorder(null);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Email", "User Type", "Joined", "Sataus"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spTable.setViewportView(table);

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 1357, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tk.component.Card card1;
    private com.tk.component.Card card2;
    private com.tk.component.Card card3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private com.tk.swing.PanelBorder panelBorder1;
    private javax.swing.JScrollPane spTable;
    private com.tk.swing.Table table;
    // End of variables declaration//GEN-END:variables
}
