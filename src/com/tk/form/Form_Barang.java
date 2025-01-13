package com.tk.form;

import com.tk.controller.DatabaseControllers;
import com.tk.model.BarangModel;
import com.tk.model.KategoriModel;
import com.tk.swing.ButtonEditor;
import com.tk.swing.ButtonRenderer;
import com.tk.swing.ComboBoxSuggestion;
import com.tk.swing.ScrollBar;
import com.tk.swing.Table;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author STNVC
 */
public class Form_Barang extends javax.swing.JPanel {

    private Table table;
    private ComboBoxSuggestion cbKategori;
    private JTextField txtNamaBarang, txtDeskripsi, txtHarga, txtStok;
    private List<KategoriModel> listKategori;
     private int currentEditingBarangId = -1; 

    public Form_Barang() {
        initComponents();
        setOpaque(false);
        loadKategoriData();
        initPanelBorder();
    }

    private void loadKategoriData() {
        listKategori = DatabaseControllers.getAllKategori();
    }

    private void initPanelBorder() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        pb.setLayout(new GridBagLayout());
        pb.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add title
        JLabel title = new JLabel("Manajemen Barang", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 24));
        title.setForeground(Color.decode("#4A00E0"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.gridwidth = 2;
        pb.add(title, gbc);

        // Add table
        initializeTable();
        JScrollPane sp = new JScrollPane(table);
        sp.setVerticalScrollBar(new ScrollBar());
        sp.setBorder(null);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        pb.add(sp, gbc);

        // Add input panel
        JPanel inputPanel = createInputPanel();
        gbc.gridy = 2;
        gbc.weighty = 0;
        pb.add(inputPanel, gbc);

        add(pb, BorderLayout.CENTER);
    }

    private void initializeTable() {
        table = new Table();
        table.addHeaders(new String[]{"ID Barang", "Kategori", "Nama Barang", "Deskripsi", "Harga", "Stok", "Aksi"});
        table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor());
        loadDataToTable();
    }

    private void loadDataToTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        List<BarangModel> listBarang = DatabaseControllers.getAllBarang();
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        for (BarangModel barang : listBarang) {
            String kategoriNama = "Unknown";
            if (listKategori != null) {
                kategoriNama = listKategori.stream()
                        .filter(k -> k.getIdKategori().equals(barang.getIdKategori()))
                        .findFirst()
                        .map(KategoriModel::getKategori)
                        .orElse("Unknown");
            }
            model.addRow(new Object[]{
                barang.getIdBarang(),
                kategoriNama,
                barang.getNamaBarang(),
                barang.getDeskripsi(),
                formatRupiah.format(barang.getHarga()),
                barang.getStok(),
                createButtonPanel(barang)
            });
        }
    }

    private ComboBoxSuggestion createCategoryComboBox() {
        ComboBoxSuggestion comboBox = new ComboBoxSuggestion();
        comboBox.setPreferredSize(new Dimension(240, 25));
        if (listKategori != null) {
            for (KategoriModel kategori : listKategori) {
                comboBox.addItem(kategori.getKategori());
            }
        }
        return comboBox;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Baris 1
        gbc.gridy = 0;
        gbc.gridx = 0;
        cbKategori = createCategoryComboBox();
        inputPanel.add(createInputGroup("Kategori", cbKategori), gbc);

        gbc.gridx = 1;
        txtNamaBarang = createTextField();
        inputPanel.add(createInputGroup("Nama Barang", txtNamaBarang), gbc);

        gbc.gridx = 2;
        txtHarga = createTextField();
        inputPanel.add(createInputGroup("Harga", txtHarga), gbc);

        // Baris 2
        gbc.gridy = 1;
        gbc.gridx = 0;
        txtStok = createTextField();
        inputPanel.add(createInputGroup("Stok", txtStok), gbc);

        gbc.gridx = 1;
        txtDeskripsi = createTextField();
        inputPanel.add(createInputGroup("Deskripsi", txtDeskripsi), gbc);

        // Tombol Simpan
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.addActionListener(e -> saveBarang());
        inputPanel.add(btnSimpan, gbc);

        return inputPanel;
    }

    private JPanel createInputGroup(String labelText, JComponent inputComponent) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(250, 50));

        JLabel label = new JLabel(labelText);
        panel.add(label, BorderLayout.NORTH);
        panel.add(inputComponent, BorderLayout.CENTER);

        return panel;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(240, 25));
        return textField;
    }

    private JPanel createButtonPanel(BarangModel barang) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        buttonPanel.setOpaque(false);

        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(e -> editBarang(barang));

        JButton btnHapus = new JButton("Hapus");
        btnHapus.addActionListener(e -> deleteBarang(barang));

        buttonPanel.add(btnEdit);
        buttonPanel.add(btnHapus);
        return buttonPanel;
    }

     private void editBarang(BarangModel barang) {
        currentEditingBarangId = barang.getIdBarang(); // Set ID barang yang sedang diedit
        String kategoriNama = listKategori.stream()
                .filter(k -> k.getIdKategori().equals(barang.getIdKategori()))
                .findFirst()
                .map(KategoriModel::getKategori)
                .orElse("");
        cbKategori.setSelectedItem(kategoriNama);
        txtNamaBarang.setText(barang.getNamaBarang());
        txtDeskripsi.setText(barang.getDeskripsi());
        txtHarga.setText(String.valueOf(barang.getHarga()));
        txtStok.setText(String.valueOf(barang.getStok()));
    }

    private void saveBarang() {
        try {
            int selectedIndex = cbKategori.getSelectedIndex();
            if (selectedIndex == -1 || listKategori == null) {
                JOptionPane.showMessageDialog(this, "Pilih kategori terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String idKategori = listKategori.get(selectedIndex).getIdKategori();
            String namaBarang = txtNamaBarang.getText();
            String deskripsi = txtDeskripsi.getText();
            BigDecimal harga = new BigDecimal(txtHarga.getText());
            int stok = Integer.parseInt(txtStok.getText());

            BarangModel barang = new BarangModel(currentEditingBarangId, idKategori, namaBarang, deskripsi, harga, stok);
            boolean success;

            if (currentEditingBarangId == -1) {
                // Ini adalah penambahan baru
                success = DatabaseControllers.saveBarang(barang);
            } else {
                // Ini adalah pengeditan
                success = DatabaseControllers.updateBarang(barang);
            }

            if (success) {
                JOptionPane.showMessageDialog(this, currentEditingBarangId == -1 ? "Barang berhasil disimpan!" : "Barang berhasil diperbarui!");
                clearInputFields();
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan/memperbarui barang", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid untuk harga atau stok", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearInputFields() {
        currentEditingBarangId = -1; // Reset ID barang yang sedang diedit
        cbKategori.setSelectedIndex(0);
        txtNamaBarang.setText("");
        txtDeskripsi.setText("");
        txtHarga.setText("");
        txtStok.setText("");
    }


    private void deleteBarang(BarangModel barang) {
    int confirm = JOptionPane.showConfirmDialog(this,
            "Apakah Anda yakin ingin menghapus barang '" + barang.getNamaBarang() + "'?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        try {
            boolean success = DatabaseControllers.deleteBarang(barang.getIdBarang());
            if (success) {
                JOptionPane.showMessageDialog(this, "Barang '" + barang.getNamaBarang() + "' berhasil dihapus!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus barang '" + barang.getNamaBarang() + "'", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menghapus barang: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pb = new com.tk.swing.PanelBorder();

        pb.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pbLayout = new javax.swing.GroupLayout(pb);
        pb.setLayout(pbLayout);
        pbLayout.setHorizontalGroup(
            pbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1044, Short.MAX_VALUE)
        );
        pbLayout.setVerticalGroup(
            pbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 552, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tk.swing.PanelBorder pb;
    // End of variables declaration//GEN-END:variables
}
