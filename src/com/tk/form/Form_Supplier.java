package com.tk.form;

import com.tk.controller.DatabaseControllers;
import com.tk.model.BarangModel;
import com.tk.model.KategoriModel;
import com.tk.model.PemasokModel;
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
public class Form_Supplier extends javax.swing.JPanel {

    private Table table;
    private JTextField txtNamaPemasok, txtAlamat, txtNoHp;
    private int currentEditingPemasokId = -1;

    public Form_Supplier() {
        initComponents();
        setOpaque(false);
        initPanelBorder();
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
        JLabel title = new JLabel("Manajemen Pemasok", SwingConstants.CENTER);
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
        table.addHeaders(new String[]{"ID Pemasok", "Nama Pemasok", "Alamat", "No. HP", "Aksi"});
        table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor());
        loadDataToTable();
    }

    private void loadDataToTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        List<PemasokModel> listPemasok = DatabaseControllers.getAllPemasok();

        for (PemasokModel pemasok : listPemasok) {
            model.addRow(new Object[]{
                pemasok.getIdPemasok(),
                pemasok.getNamaPemasok(),
                pemasok.getAlamat(),
                pemasok.getNoHp(),
                createButtonPanel(pemasok)
            });
        }
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
        txtNamaPemasok = createTextField();
        inputPanel.add(createInputGroup("Nama Pemasok", txtNamaPemasok), gbc);

        gbc.gridx = 1;
        txtAlamat = createTextField();
        inputPanel.add(createInputGroup("Alamat", txtAlamat), gbc);

        // Baris 2
        gbc.gridy = 1;
        gbc.gridx = 0;
        txtNoHp = createTextField();
        inputPanel.add(createInputGroup("No. HP", txtNoHp), gbc);

        // Tombol Simpan
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.addActionListener(e -> savePemasok());
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

    private JPanel createButtonPanel(PemasokModel pemasok) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        buttonPanel.setOpaque(false);

        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(e -> editPemasok(pemasok));

        JButton btnHapus = new JButton("Hapus");
        btnHapus.addActionListener(e -> deletePemasok(pemasok));

        buttonPanel.add(btnEdit);
        buttonPanel.add(btnHapus);
        return buttonPanel;
    }

    private void savePemasok() {
        try {
            String namaPemasok = txtNamaPemasok.getText();
            String alamat = txtAlamat.getText();
            String noHp = txtNoHp.getText();

            if (namaPemasok.isEmpty() || alamat.isEmpty() || noHp.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            PemasokModel pemasok = new PemasokModel(currentEditingPemasokId, namaPemasok, alamat, noHp);
            boolean success;

            if (currentEditingPemasokId == -1) {
                // Ini adalah penambahan baru
                success = DatabaseControllers.savePemasok(pemasok);
            } else {
                // Ini adalah pengeditan
                success = DatabaseControllers.updatePemasok(pemasok);
            }

            if (success) {
                JOptionPane.showMessageDialog(this, currentEditingPemasokId == -1 ? "Pemasok berhasil disimpan!" : "Pemasok berhasil diperbarui!");
                clearInputFields();
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan/memperbarui pemasok", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editPemasok(PemasokModel pemasok) {
        currentEditingPemasokId = pemasok.getIdPemasok();
        txtNamaPemasok.setText(pemasok.getNamaPemasok());
        txtAlamat.setText(pemasok.getAlamat());
        txtNoHp.setText(pemasok.getNoHp());
    }

    private void deletePemasok(PemasokModel pemasok) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus pemasok ini?", "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = DatabaseControllers.deletePemasok(pemasok.getIdPemasok());
            if (success) {
                JOptionPane.showMessageDialog(this, "Pemasok berhasil dihapus!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus pemasok", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearInputFields() {
        currentEditingPemasokId = -1;
        txtNamaPemasok.setText("");
        txtAlamat.setText("");
        txtNoHp.setText("");
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
