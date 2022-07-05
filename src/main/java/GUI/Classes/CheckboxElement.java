package GUI.Classes;

/**
 * This is the class that holds the checklist item data. Has to have isSelected for the value in the checkbox
 * Java Swing How to - Create JList of CheckBox. (n.d). Retrieved from:
 * http://www.java2s.com/Tutorials/Java/Swing_How_to/JList/Create_JList_of_CheckBox.htm
 * Not claimed as my own work, learned this part from this tutorial
 */

public class CheckboxElement {
    private String text; //to be displayed
    private boolean isSelected; //used for the display

    /**
     * Constructor for the object. text is the text that will appear on the checklist.
     * @param text a string
     */
    public CheckboxElement(String text){
        this.text = text;
    }

    /**
     * getter for the boolean value isSelected()
     * @return the boolean of whether or not the list element is selected
     */
    public boolean isSelected(){
        return isSelected;
    }

    /**
     * sets selected to a boolean value
     * @param bool a boolean
     */
    public void setSelected(boolean bool){
        isSelected = bool;
    }

    /**
     * returns the text that is being displayed
     * @return the string corresponding to the text
     */
    @Override
    public String toString(){
        return text;
    }

}
