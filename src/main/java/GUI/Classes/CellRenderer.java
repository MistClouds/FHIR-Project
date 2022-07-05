package GUI.Classes;

import javax.swing.*;
import java.awt.*;

/**
 * This is the class that renders the checklist, so that we can check whether or not the patient is selected
 * Java Swing How to - Create JList of CheckBox. (n.d). Retrieved from:
 * http://www.java2s.com/Tutorials/Java/Swing_How_to/JList/Create_JList_of_CheckBox.htm
 * Not claimed as my own work, learned this part from this tutorial
 */
public class CellRenderer extends JCheckBox implements ListCellRenderer {

    /**
     * Constructor for the cell component.. This makes sure that the checkboxes appear ticked or unticked, depending on
     * if the user has ticked them or not.
     * @param list the list where the cell will be rendered
     * @param value the value of the cell. In this case this refers to a ListElement object
     * @param index the index of the cell.
     * @param isSelected whether or not the cell is selected. This is not used in this constructor
     * @param cellHasFocus whether or not the cell has focus. not sure what this means, but it is unused
     * @return a component that is updated properly(has checkbox ticked or not)
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setEnabled(list.isEnabled());//enables the checkbox if the list is also enabled
        setSelected(((CheckboxElement) value).isSelected());//sets the checkbox to ticked or empty based on the ListElement class
        setFont(list.getFont());//set all these to the list's settings
        setBackground(getBackground());
        setForeground(getForeground());
        setText(value.toString());//sets the text to the string part of the object
        return this;
    }
}
