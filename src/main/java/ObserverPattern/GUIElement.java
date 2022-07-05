package ObserverPattern;

import javax.swing.*;

/**
 * abstract class for all GUI elements - they all need a data source and JFrame
 */
public abstract class GUIElement extends Observer{
    protected PatientTable dataSource;

    protected JFrame frame;

    /**
     * setter for data source. this is the data source that all observers will be looking at
     * @param table a patientTable
     */
    public void setDataSource(PatientTable table){
        dataSource = table;
    }

    /**
     * setter for frame. this frame will be called to add views to the frame
     * @param frame a JFrame
     */
    public void setFrame(JFrame frame){
        this.frame = frame;
    }
}
