package com.tk.main;

import com.tk.component.Header;
import com.tk.component.Menu;
import com.tk.event.EventMenuSelected;
import com.tk.form.Form_Barang;
import com.tk.form.Form_Customer;
import com.tk.form.Form_Dashboard;
import com.tk.form.Form_Dashboard_n;
import com.tk.form.Form_Kategori;
import com.tk.form.Form_Penjualan;
import com.tk.form.Form_Save_Order;
import com.tk.form.Form_Supplier;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainFullScreen extends JFrame {

    private Menu leftPanel;
    private Header topRightPanel;
    private JPanel bottomRightPanel;
    private JButton exitButton;

    public MainFullScreen() {
        setTitle("Custom Grid Layout Example - Fullscreen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupLayout();
        setupFullscreen();
        leftPanel.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) {
                System.err.println(index);
                if (index == 0) {
                    setForm(new Form_Dashboard_n());
                } else if (index == 1) {
                    setForm(new Form_Customer());
                } else if (index == 2) {
                    setForm(new Form_Supplier());
                } else if (index == 3) {
                    setForm(new Form_Barang());
                } else if (index == 4) {
                    setForm(new Form_Kategori());
                } else if (index == 5) {
                    setForm(new Form_Save_Order());
                }
                else if (index == 6) {
                    setForm(new Form_Penjualan());
                }
            }
        });
        setForm(new Form_Dashboard());
    }

    private void setForm(JComponent com) {
        bottomRightPanel.removeAll();
        bottomRightPanel.add(com);
        bottomRightPanel.repaint();
        bottomRightPanel.revalidate();
    }

    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel kiri
        leftPanel = new Menu();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 1.0;
        add(leftPanel, gbc);

        // Panel kanan atas
        topRightPanel = new Header();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        add(topRightPanel, gbc);

        // Panel kanan bawah
        bottomRightPanel = new JPanel(new BorderLayout());
        bottomRightPanel.setBackground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        add(bottomRightPanel, gbc);

        // Tombol Exit
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        bottomRightPanel.add(exitButton, BorderLayout.SOUTH);
    }

    private void setupFullscreen() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFullScreen example = new MainFullScreen();
            example.setVisible(true);
        });
    }
}
