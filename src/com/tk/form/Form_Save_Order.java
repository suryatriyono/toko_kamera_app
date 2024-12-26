package com.tk.form;

import com.tk.controller.DatabaseControllers;
import com.tk.model.BarangModel;
import com.tk.model.DetailPembelianModel;
import com.tk.model.PemasokModel;
import com.tk.swing.ButtonEditor;
import com.tk.swing.ButtonRenderer;
import com.tk.swing.ComboBoxSuggestion;
import com.tk.swing.ScrollBar;
import com.tk.swing.Table;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;

import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author STNVC
 */
public class Form_Save_Order extends javax.swing.JPanel {

    private final List<DetailPembelianModel> detailPembelianList;
    private final List<BarangModel> listBarang;
    private Table table;
    private ComboBoxSuggestion cbBarang;
    private JTextField txtJumlah;

    public Form_Save_Order() {
        initComponents();
        detailPembelianList = new ArrayList<>();
        listBarang = DatabaseControllers.getAllBarang();
        setOpaque(false);
        initPanelBorder();
    }

    private void initPanelBorder() {
        pb.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        pb.setPreferredSize(new Dimension(1048, 600));

        // Add title
        JLabel title = new JLabel(" Save Order ");
        title.setFont(new Font("Poppins", Font.BOLD, 24));
        title.setForeground(new Color(153, 104, 252));

        // Add table
        initializeTable();

        // Add jScrollpane
        JScrollPane sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(600, 250));
        sp.getViewport().setBackground(Color.WHITE);
        sp.setBorder(null);
        sp.setVerticalScrollBar(new ScrollBar());

        // Custom corner
        JPanel cornerPanel = new JPanel();
        cornerPanel.setBackground(Color.RED);
        sp.setCorner(JScrollPane.UPPER_RIGHT_CORNER, cornerPanel);

        // Add to main penel
        pb.add(title);
        pb.add(sp, "W 980!");
        pb.add(createInputPanel(), "w 1000!");

    }

    private void initializeTable() {
        table = new Table();
        table.addHeaders(new String[]{"ID Barang", "Nama Barang", "Harga", "Jumlah", "Subtotal", "Aksi"});
        // Menyiapkan renderer dan editor untuk kolom aksi (yang berisi tombol)
        table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor());

    }

    private void loadBarangData() {
        listBarang.forEach(barang -> cbBarang.addItem(barang.getNamaBarang()));
        SwingUtilities.invokeLater(() -> {
            cbBarang.setSelectedItem(null);
            cbBarang.requestFocus();
        });
    }

    private void addItemToOrder() {
        String namaBarang = cbBarang.getSelectedItem().toString();
        String jumlahStr = txtJumlah.getText();

        try {
            int jumlah = Integer.parseInt(jumlahStr);
            BarangModel barang = listBarang.stream()
                    .filter(b -> b.getNamaBarang().equals(namaBarang))
                    .findFirst()
                    .orElse(null);

            if (barang != null) {

                if (jumlah > 0) {
                    DetailPembelianModel detail = new DetailPembelianModel(
                            barang.getIdBarang(),
                            barang.getHarga(),
                            jumlah
                    );

                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

                    detailPembelianList.add(detail);
                    table.addRow(new Object[]{
                        detail.getIdBarang(),
                        barang.getNamaBarang(),
                        formatRupiah.format((double) detail.getHarga()),
                        detail.getJumlah(),
                        formatRupiah.format((double) detail.getSubtotal()),
                        createButtonPanel(detail)
                    });

                    cbBarang.setSelectedItem(null);
                    cbBarang.requestFocus();
                    txtJumlah.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Jumlah harus lebih dari 0", "Peringatan", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah tidak valid", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        inputPanel.setOpaque(false);

        JLabel namaBarang = new JLabel("Nama Barang ");
        namaBarang.setPreferredSize(new Dimension(100, 35));
        namaBarang.setFont(new Font("Poppins", Font.BOLD, 12));
        namaBarang.setForeground(new Color(153, 104, 252));

        cbBarang = new ComboBoxSuggestion();
        loadBarangData();

        JLabel jumlahBarang = new JLabel("Jumlah Barang ");
        jumlahBarang.setPreferredSize(new Dimension(100, 35));
        jumlahBarang.setFont(new Font("Poppins", Font.BOLD, 12));
        jumlahBarang.setForeground(new Color(153, 104, 252));
        txtJumlah = new JTextField();
        txtJumlah.setPreferredSize(new Dimension(100, 35));

        JButton btnSimpan = new JButton("Simpan Order");
        btnSimpan.setPreferredSize(new Dimension(125, 35));
        btnSimpan.addActionListener(e -> saveOrder());

        cbBarang.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && cbBarang.getSelectedItem() != null) {
                    txtJumlah.requestFocus();
                }
            }

        });

        txtJumlah.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addItemToOrder();
                }
            }

        });
        inputPanel.add(namaBarang);
        inputPanel.add(cbBarang);
        inputPanel.add(jumlahBarang);
        inputPanel.add(txtJumlah);
        inputPanel.add(btnSimpan);

        return inputPanel;
    }

    private JPanel createButtonPanel(DetailPembelianModel detail) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        buttonPanel.setOpaque(false);

        JButton btnHapus = new JButton("Hapus");
        btnHapus.addActionListener(e -> {
            int rsOption = JOptionPane.showConfirmDialog(this, "Apakah anda yakin!", "Peringatan", JOptionPane.OK_CANCEL_OPTION);
            if (rsOption == JOptionPane.OK_OPTION) {
                detailPembelianList.remove(detail);
                refreshTable();
                cbBarang.requestFocus();
            }

        });

        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(e -> editDeteilPembelian(detail));

        buttonPanel.add(btnEdit);
        buttonPanel.add(btnHapus);
        return buttonPanel;
    }

    private void editDeteilPembelian(DetailPembelianModel detail) {
        String jumlahStr = JOptionPane.showInputDialog("Edit jumlah:", detail.getJumlah());
        if (jumlahStr != null) {
            try {
                int newJumlah = Integer.parseInt(jumlahStr);
                if (newJumlah > 0) {
                    detail.setJumlah(newJumlah);
                    detail.setSubtotal(detail.getHarga() * newJumlah);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Jumlah harus lebih dari 0", "Peringatan", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Jumlah tidak valid", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void refreshTable() {

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        for (DetailPembelianModel detail : detailPembelianList) {

            BarangModel barang = listBarang.stream()
                    .filter(b -> b.getIdBarang().equals(detail.getIdBarang()))
                    .findFirst()
                    .orElse(null);
            String namaBarang = barang != null ? barang.getNamaBarang() : "";

            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

            table.addRow(new Object[]{
                detail.getIdBarang(),
                namaBarang,
                formatRupiah.format((double) barang.getHarga()),
                detail.getJumlah(),
                formatRupiah.format((double) detail.getSubtotal()),
                createButtonPanel(detail)
            });

        }

    }

    private void saveOrder() {
        if (detailPembelianList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada detail pembelian untuk di simpain.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ComboBoxSuggestion cbPemasok = new ComboBoxSuggestion();
        List<PemasokModel> listPemasok = DatabaseControllers.getAllPemasok();

        listPemasok.forEach(pemasok -> cbPemasok.addItem(pemasok.getNamaPemasok()));

        int rsOption = JOptionPane.showConfirmDialog(this, cbPemasok, "Pilih Pemasok", JOptionPane.OK_CANCEL_OPTION);

        if (rsOption == JOptionPane.OK_OPTION) {
            String namaPemasok = (String) cbPemasok.getSelectedItem();
            PemasokModel pemasok = listPemasok.stream()
                    .filter(p -> p.getNamaPemasok().equals(namaPemasok))
                    .findFirst()
                    .orElse(null);
            if (pemasok.getIdPemasok() != null) {
                boolean success = DatabaseControllers.saveOrder(detailPembelianList, pemasok.getIdPemasok());

                if (success) {
                    JOptionPane.showMessageDialog(this, "Order berhasil disimpan!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    detailPembelianList.clear();
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menyimpan", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pemasok tidak di temukan", "Error", JOptionPane.ERROR_MESSAGE);
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
