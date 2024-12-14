package com.tk.swing;

import com.tk.model.Model_Menu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

/**
 *
 * @author STNVC
 */
public class ListMenu<E extends Object> extends JList<E> {

    private final DefaultListModel model;
    private int selectedIndex = -1;

    public ListMenu() {
        model = new DefaultListModel();
        setModel(model);
        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)){
                    int index = locationToIndex(me.getPoint());
                    Object o = model.getElementAt(index);
                    if (o instanceof Model_Menu) {
                        Model_Menu menu = (Model_Menu) o;
                        if (menu.getType() == Model_Menu.MenuType.MENU) {
                            selectedIndex = index;
                        }
                    } else {
                        selectedIndex = index;
                    }
                    repaint();
                }
            }  
        });
    }

    @Override
    public ListCellRenderer<? super E> getCellRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Model_Menu data;
                if (value instanceof Model_Menu) {
                    data = (Model_Menu) value;
                } else {
                    data = new Model_Menu("", value + "", Model_Menu.MenuType.EMPTY);
                }

                MenuItem item = new MenuItem(data);
                item.setSelected(selectedIndex == index);
                return item;
            }

        };
    }

    public void addItem(Model_Menu data) {
        model.addElement(data);
    }

}
