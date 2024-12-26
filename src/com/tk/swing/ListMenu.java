package com.tk.swing;

import com.tk.event.EventMenuSelected;
import com.tk.model.MenuModel;
import java.awt.Component;
import java.awt.Dimension;
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
    private final int ITEM_WIDTH = 100;
    private final int ITEM_HEIGHT = 50;
    private int selectedIndex = -1;
    private int overIndex = -1;
    private EventMenuSelected event;

    public void addEventMenuSelected(EventMenuSelected event) {
        this.event = event;
    }

    public ListMenu() {
        model = new DefaultListModel();
        setModel(model);
        
        setFixedCellHeight(ITEM_HEIGHT);
        setFixedCellWidth(ITEM_WIDTH);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    int index = locationToIndex(me.getPoint());
                    Object o = model.getElementAt(index);
                    if (o instanceof MenuModel) {
                        MenuModel menu = (MenuModel) o;
                        if (menu.getType() == MenuModel.MenuType.MENU) {
                            selectedIndex = index;
                            if (event != null) {
                                event.selected(index);
                            }
                        }
                    } else {
                        selectedIndex = index;
                    }
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent me) {
                overIndex = -1;
                repaint();
            }

        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                int index = locationToIndex(me.getPoint());
                if (index != overIndex) {
                    Object o = model.getElementAt(index);
                    if (o instanceof MenuModel) {
                        MenuModel menu = (MenuModel) o;
                        if (menu.getType() == MenuModel.MenuType.MENU) {
                            overIndex = index;
                        } else {
                            overIndex = -1;
                        }
                        repaint();
                    }
                }
            }

        });
    }

    @Override
    public ListCellRenderer<? super E> getCellRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                MenuModel data;
                if (value instanceof MenuModel) {
                    data = (MenuModel) value;
                } else {
                    data = new MenuModel("", value + "", MenuModel.MenuType.EMPTY);
                }

                MenuItem item = new MenuItem(data);
                
                item.setPreferredSize(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));
                
                item.setSelected(selectedIndex == index);
                item.setOver(overIndex == index);
                return item;
            }

        };
    }

    public void addItem(MenuModel data) {
        model.addElement(data);
        
    }

}
