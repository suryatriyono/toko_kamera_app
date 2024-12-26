package com.tk.swing;

import javax.swing.JComboBox;
/**
 *
 * @author STNVC
 */
public class ComboBoxSuggestion extends JComboBox<String> {

    public ComboBoxSuggestion() {
        setUI(new ComboSuggestionUI());
    }
    
}
