package ObserverPattern;

import javax.swing.*;
import java.awt.*;

/**
 * This is the class that is the panel on the right, showing the patient's expanded information if selected in the table.
 * Uses a list and a list model to dynamically update the list.
 */
public class ExpandedView extends GUIElement{
    //can immediately match these as they wont be changed
    DefaultListModel listModel = new DefaultListModel();
    JList expandedInfo = new JList(listModel);

    /**
     * This is the constructor for the expandedView. takes as input the data source, and the frame so its knows which
     * Frame it is part of.
     * @param source a patientTable data source
     * @param frame a JFrame that it will be rendered on
     */
    public ExpandedView(PatientTable source, JFrame frame){
        setDataSource(source);
        setFrame(frame);

        update();

        //sets the layout
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(new JScrollPane(expandedInfo));
        frame.pack();
        frame.setSize(1550, 1000);
    }


    /**
     * this is called when anything in the data source changes. can be expanded later
     */
    @Override
    public void update() {
        showExpanded();
    }

    /**
     * This is the method that updates the panel information. It loops through the table to find the patient that
     * is "flagged" to appear on the expandedView.
     *
     * Known issue is that when the patient that is shown in the expanded view's monitoring is stopped, the info does
     * not disappear from the view. clearing the model and repainting/packing doesn't seem to help, minor issue though.
     */
    public void showExpanded(){
        int noPatients = dataSource.getpatTable().size();
        listModel.clear();
        for (int i = 0; i < noPatients; i++){
            if(dataSource.getpatTable().get(i).isInExpanded())//there is only ever 1 patient that has the flag
            {
                listModel.addElement("Birthday: " + dataSource.getpatTable().get(i).getBirthday());
                listModel.addElement("Gender: " + dataSource.getpatTable().get(i).getGender());
                listModel.addElement("Address: " + dataSource.getpatTable().get(i).getAddress());
                break;//no point to go further
            }
        }
    }
}
