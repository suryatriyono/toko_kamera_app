/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tk.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author STNVC
 */
public class ButtonEditor extends DefaultCellEditor {
    private JPanel panel;
    private boolean isClicked;

    public ButtonEditor() {
        super(new JCheckBox());
        panel = new JPanel();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof JPanel) {
            panel = (JPanel) value;
            
            for (Component comp : panel.getComponents()) {
                if (comp instanceof  JButton) {
                    ((JButton) comp).addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            fireEditingStopped();
                        }
                        
                    });
                }
            }
            
            return panel;
        }
        
        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }

    @Override
    public Object getCellEditorValue() {
        return panel;
    }
    
    
    
    
    
    
}
