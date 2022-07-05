package GUI.Classes;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class is the renderer for all cells in the table. It basically just sets the background to red if the
 * average Cholesterol level of the given patient is higher than the average. All the patients that need to be
 * highlighted are added to an arraylist before being passed into the constructor of this renderer.
 */
public class TableCellRenderer extends DefaultTableCellRenderer {

    //these are needed to colour the proper rows
    private ArrayList<Integer> rowsToColourRed;
    private ArrayList<Integer> rowsToColourPurple1;
    private ArrayList<Integer> rowsToColourPurple2;
    private ArrayList<Integer> rowsToColourGreen;

    /**
     * Constructor for TableCellRenderer. Takes rowsToColour as input and stores it for later usage.
     * @param rowsToColour an arraylist of integers
     */
    public TableCellRenderer(ArrayList<Integer> rowsToColour1, ArrayList<Integer> rowsToColour2,
                             ArrayList<Integer> rowsToColour3, ArrayList<Integer> rowsToColour4){
        this.rowsToColourRed = rowsToColour1;
        this.rowsToColourPurple1 = rowsToColour2;
        this.rowsToColourPurple2 = rowsToColour3;
        this.rowsToColourGreen = rowsToColour4;
        setOpaque(true);
    }

    /**
     * The renderer. Just checks if the current row needs to be coloured or not, and colours the cell.
     * @param table the table where the cell will be rendered
     * @param value the value of the cell.
     * @param isSelected whether or not the cell is selected. doesn't do anything here.
     * @param hasFocus whether or not the cell has focus. doesn't do anything here
     * @param row the row of the cell. compared to see if it should be coloured or not
     * @param column the column of the cell. isn't really used here.
     * @return the cell, coloured corresponding to whether or not the row is higher than the average cholesterol.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setFont(table.getFont());//set all these to the list's settings
        if (rowsToColourRed.contains(row) && column == 1)
        {//if the row needs to be coloured, colour it
            setBackground(new Color (255, 204, 203));
        }
        else if (rowsToColourPurple1.contains(row) && column == 3)
        {//if the row needs to be coloured, colour it
            setBackground(new Color (198, 138, 209));
        }
        else if (rowsToColourPurple2.contains(row) && column == 4)
        {//if the row needs to be coloured, colour it
            setBackground(new Color (198, 138, 209));
        }
        else if (rowsToColourGreen.contains(row) && column == 5)
        {//if the row needs to be coloured, colour it
            setBackground(new Color (164, 230, 126));
        }
        else
        {
            setBackground(table.getBackground()); //otherwise just get the usual
        }



        setForeground(table.getForeground());
        setText(value.toString());//sets the text to the string part of the object
        return this;
    }
}
