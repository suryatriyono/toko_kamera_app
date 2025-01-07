package com.tk.form;

import com.tk.component.BarangSearchDialog;
import com.tk.controller.DatabaseControllers;
import com.tk.model.BarangModel;
import com.tk.model.DetailPenjualanModel;
import com.tk.model.PelangganModel;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

import java.util.ArrayList;
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
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author STNVC
 */
public class Form_Penjualan extends javax.swing.JPanel {

    private Table table;
    private ComboBoxSuggestion cbPelanggan;
    private JTextField txtNamaBarang, txtJumlah, txtHarga;
    private JButton btnTambahBarang, btnSimpanPenjualan;
    private List<DetailPenjualanModel> detailPenjualanList;
    private List<PelangganModel> listPelanggan;
    private NumberFormat currencyFormat;

    public Form_Penjualan() {
        initComponents();
        setOpaque(false);
        detailPenjualanList = new ArrayList<>();
        listPelanggan = new ArrayList<>();
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        initPelangganComboBox();
        initPanelBorder();
    }

    private void initPelangganComboBox() {
        cbPelanggan = new ComboBoxSuggestion();
        loadPelangganData();

        cbPelanggan.setEditable(true);
        cbPelanggan.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(() -> {
                    String input = cbPelanggan.getEditor().getItem().toString().trim().toLowerCase();
                    if (!input.isEmpty()) {
                        cbPelanggan.removeAllItems();
                        for (PelangganModel pelanggan : listPelanggan) {
                            if (pelanggan.getNamaPelanggan().toLowerCase().contains(input)) {
                                cbPelanggan.addItem(pelanggan.getNamaPelanggan());
                            }
                        }
                        cbPelanggan.setPopupVisible(true);
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });

        cbPelanggan.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String input = cbPelanggan.getEditor().getItem().toString().trim();
                    if (!input.isEmpty()) {
                        PelangganModel pelanggan = findPelangganByName(input);
                        if (pelanggan == null) {
                            int confirm = JOptionPane.showConfirmDialog(
                                    Form_Penjualan.this,
                                    "Pelanggan baru akan dibuat. Lanjutkan?",
                                    "Konfirmasi",
                                    JOptionPane.YES_NO_OPTION);

                            if (confirm == JOptionPane.YES_OPTION) {
                                pelanggan = createNewPelanggan(input);
                                if (pelanggan != null) {
                                    listPelanggan.add(pelanggan);
                                    cbPelanggan.addItem(pelanggan.getNamaPelanggan());
                                    cbPelanggan.setSelectedItem(pelanggan.getNamaPelanggan());
                                }
                            }
                        } else {
                            cbPelanggan.setSelectedItem(pelanggan.getNamaPelanggan());
                        }
                    }
                }
            }
        });
    }

    private PelangganModel findPelangganByName(String name) {
        return listPelanggan.stream()
                .filter(p -> p.getNamaPelanggan().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    private PelangganModel createNewPelanggan(String namaPelanggan) {
        // Implementasi dialog untuk input data pelanggan baru
        String alamat = JOptionPane.showInputDialog("Masukkan alamat pelanggan:");
        String noHp = JOptionPane.showInputDialog("Masukkan nomor HP pelanggan:");

        if (alamat != null && noHp != null) {
            PelangganModel newPelanggan = new PelangganModel(0, namaPelanggan, alamat, noHp);
            // Simpan pelanggan baru ke database
            if (DatabaseControllers.savePelanggan(newPelanggan)) {
                return newPelanggan;
            }
        }
        return null;
    }

    private void loadPelangganData() {
        listPelanggan = DatabaseControllers.getAllPelanggan();
        for (PelangganModel pelanggan : listPelanggan) {
            cbPelanggan.addItem(pelanggan.getNamaPelanggan());
        }
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
        JLabel title = new JLabel("Form Penjualan", SwingConstants.CENTER);
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
        table.addHeaders(new String[]{"ID Barang", "Nama Barang", "Harga", "Jumlah", "Subtotal", "Aksi"});
        table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor());
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Pelanggan
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(createInputGroup("Pelanggan", cbPelanggan), gbc);

        // Nama Barang
        gbc.gridx = 1;
        txtNamaBarang = new JTextField();
        txtNamaBarang.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    showBarangSearchDialog();
                }
            }
        });
        inputPanel.add(createInputGroup("Nama Barang", txtNamaBarang), gbc);

        // Jumlah
        gbc.gridx = 0;
        gbc.gridy = 1;
        txtJumlah = new JTextField();
        txtJumlah.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addBarangToDetail();
                }
            }
        });
        inputPanel.add(createInputGroup("Jumlah", txtJumlah), gbc);

        // Harga
        gbc.gridx = 1;
        txtHarga = new JTextField();
        txtHarga.setEditable(false);
        inputPanel.add(createInputGroup("Harga", txtHarga), gbc);

        // Tombol Tambah Barang
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        btnTambahBarang = new JButton("Tambah Barang");
        btnTambahBarang.addActionListener(e -> addBarangToDetail());
        inputPanel.add(btnTambahBarang, gbc);

        // Tombol Simpan Penjualan
        gbc.gridy = 3;
        btnSimpanPenjualan = new JButton("Simpan Penjualan");
        btnSimpanPenjualan.addActionListener(e -> savePenjualan());
        inputPanel.add(btnSimpanPenjualan, gbc);

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

    private void showBarangSearchDialog() {
        BarangSearchDialog dialog = new BarangSearchDialog(SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
        BarangModel selectedBarang = dialog.getSelectedBarang();
        if (selectedBarang != null) {
            txtNamaBarang.setText(selectedBarang.getNamaBarang());
            txtHarga.setText(currencyFormat.format(selectedBarang.getHarga()));
            txtJumlah.requestFocus();
        }
    }

    private void addBarangToDetail() {
        try {
            String namaBarang = txtNamaBarang.getText();
            int jumlah = Integer.parseInt(txtJumlah.getText());
            double harga = Double.parseDouble(txtHarga.getText().replaceAll("[^\\d.]", ""));

            BarangModel barang = DatabaseControllers.getBarangByNama(namaBarang);
            if (barang != null && jumlah > 0) {
                DetailPenjualanModel detail = new DetailPenjualanModel(0, barang.getIdBarang(), harga, jumlah);
                detailPenjualanList.add(detail);
                refreshTable();
                clearInputFields();
                txtNamaBarang.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Data barang tidak valid atau jumlah harus lebih dari 0", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input tidak valid untuk jumlah atau harga", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (DetailPenjualanModel detail : detailPenjualanList) {
            BarangModel barang = DatabaseControllers.getBarangById(detail.getIdBarang());
            model.addRow(new Object[]{
                detail.getIdBarang(),
                barang.getNamaBarang(),
                currencyFormat.format(detail.getHarga()),
                detail.getJumlah(),
                currencyFormat.format(detail.getSubtotal()),
                createButtonPanel(detail)
            });
        }
    }

    private JPanel createButtonPanel(DetailPenjualanModel detail) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        buttonPanel.setOpaque(false);

        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(e -> editDetailPenjualan(detail));

        JButton btnHapus = new JButton("Hapus");
        btnHapus.addActionListener(e -> {
            detailPenjualanList.remove(detail);
            refreshTable();
        });

        buttonPanel.add(btnEdit);
        buttonPanel.add(btnHapus);
        return buttonPanel;
    }

    private void editDetailPenjualan(DetailPenjualanModel detail) {
        String jumlahStr = JOptionPane.showInputDialog(this, "Edit jumlah:", detail.getJumlah());
        if (jumlahStr != null && !jumlahStr.isEmpty()) {
            try {
                int newJumlah = Integer.parseInt(jumlahStr);
                if (newJumlah > 0) {
                    detail.setJumlah(newJumlah);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Jumlah harus lebih dari 0", "Peringatan", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Input tidak valid", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void savePenjualan() {
        if (detailPenjualanList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada barang yang ditambahkan", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String selectedPelangganName = (String) cbPelanggan.getSelectedItem();
        PelangganModel selectedPelanggan = findPelangganByName(selectedPelangganName);
        if (selectedPelanggan == null) {
            JOptionPane.showMessageDialog(this, "Pilih pelanggan terlebih dahulu", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double totalHarga = detailPenjualanList.stream()
                .mapToDouble(DetailPenjualanModel::getSubtotal)
                .sum();

        boolean success = DatabaseControllers.savePenjualan(selectedPelanggan.getIdPelanggan(), totalHarga, detailPenjualanList);

        if (success) {
            JOptionPane.showMessageDialog(this, "Penjualan berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            clearAll();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan penjualan", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearInputFields() {
        txtNamaBarang.setText("");
        txtJumlah.setText("");
        txtHarga.setText("");
    }

    private void clearAll() {
        clearInputFields();
        detailPenjualanList.clear();
        refreshTable();
        cbPelanggan.setSelectedIndex(0);
        txtNamaBarang.requestFocus();
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
