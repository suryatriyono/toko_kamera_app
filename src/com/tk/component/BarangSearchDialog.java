/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tk.component;

import com.tk.controller.DatabaseControllers;
import com.tk.model.BarangModel;
import com.tk.swing.Table;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author STNVC
 */
public class BarangSearchDialog extends JDialog {

    private JTextField txtSearch;
    private Table table;
    private BarangModel selectedBarang;
    private NumberFormat currencyFormat;

    public BarangSearchDialog(Window owner) {
        super(owner, "Cari Barang", ModalityType.APPLICATION_MODAL);
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setSize(600, 400);
        setLocationRelativeTo(getOwner());

        // Panel pencarian
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblSearch = new JLabel("Cari Barang:");
        txtSearch = new JTextField(20);
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        add(searchPanel, BorderLayout.NORTH);

        // Tabel barang
        table = new Table();
        table.addHeaders(new String[]{"ID Barang", "Nama Barang", "Harga", "Stok"});
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Setup listeners
        setupSearchListener();
        setupKeyListeners();

        // Load data awal
        loadBarangData("");
    }

    private void setupSearchListener() {
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchBarang();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchBarang();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchBarang();
            }
        });
    }

    private void setupKeyListeners() {
        // Key listener for the search field
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });

        // Key listener for the table
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
    }

    private void handleKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                moveSelection(-1);
                break;
            case KeyEvent.VK_DOWN:
                moveSelection(1);
                break;
            case KeyEvent.VK_ENTER:
                selectBarang();
                break;
        }
    }

    private void moveSelection(int direction) {
        int selectedRow = table.getSelectedRow();
        int rowCount = table.getRowCount();
        if (rowCount > 0) {
            if (selectedRow == -1) {
                // No selection, select first or last row
                selectedRow = (direction > 0) ? 0 : rowCount - 1;
            } else {
                // Move selection
                selectedRow = (selectedRow + direction + rowCount) % rowCount;
            }
            table.setRowSelectionInterval(selectedRow, selectedRow);
            table.scrollRectToVisible(table.getCellRect(selectedRow, 0, true));
        }
    }

    private void searchBarang() {
        SwingUtilities.invokeLater(() -> loadBarangData(txtSearch.getText()));
    }

    private void loadBarangData(String keyword) {
        List<BarangModel> barangList = DatabaseControllers.searchBarang(keyword);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (BarangModel barang : barangList) {
            model.addRow(new Object[]{
                barang.getIdBarang(),
                barang.getNamaBarang(),
                currencyFormat.format(barang.getHarga()),
                barang.getStok()
            });
        }

        if (!barangList.isEmpty()) {
            table.setRowSelectionInterval(0, 0);
        }
    }

    private void selectBarang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idBarang = (int) table.getValueAt(selectedRow, 0);
            selectedBarang = DatabaseControllers.getBarangById(idBarang);
            dispose();
        }
    }

    public BarangModel getSelectedBarang() {
        return selectedBarang;
    }
}
