package com.tk.swing;

import com.tk.model.StatusTypeModel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author STNVC
 */
public class Table extends JTable {

    /**
     * @return the innerCellStatus
     */
    public boolean isInnerCellStatus() {
        return innerCellStatus;
    }

    /**
     * @param innerCellStatus the innerCellStatus to set
     */
    public void setInnerCellStatus(boolean innerCellStatus) {
        this.innerCellStatus = innerCellStatus;
    }

    /**
     * @return the colStatus
     */
    public int getColStatus() {
        return colStatus;
    }

    /**
     * @param colStatus the colStatus to set
     */
    public void setColStatus(int colStatus) {
        this.colStatus = colStatus;
    }
    private boolean innerCellStatus;
    private int colStatus = -1;

    public Table() {
        customizeTable();
    }

    private void customizeTable() {
        setShowHorizontalLines(true);
        setGridColor(new Color(203, 203, 203));
        setRowHeight(40);
        getTableHeader().setReorderingAllowed(false);

        // Custom header render
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                TableHeader header = new TableHeader(value + "");
                if (column == colStatus && innerCellStatus) {
                    header.setHorizontalAlignment(JLabel.CENTER);
                }
                return header;
            }
        });
        // Custom cell renderer
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (column == colStatus && innerCellStatus) {
                    StatusTypeModel type = (StatusTypeModel) value;
                    CellStatus cell = new CellStatus(type);
                    return cell;
                } else {
                    Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    com.setBackground(Color.WHITE);
                    setBorder(noFocusBorder);
                    if (isSelected) {
                        com.setForeground(new Color(15, 89, 140));
                    } else {
                        com.setForeground(new Color(102, 102, 102));
                    }
                    return com;
                }
            }

        });
    }

    public void addHeader(String header) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addColumn(header);
    }

    public void addHeaders(String[] headers) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        for (String header : headers) {
            model.addColumn(header);
        }
    }

    public void addRow(Object[] row) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(row);

    }

    public void addRows(Object[][] rows) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        for (Object[] row : rows) {
            model.addRow(row);
        }
    }

}
