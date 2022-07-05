package ObserverPattern;

import GUI.Classes.CellRenderer;
import GUI.Classes.CheckboxElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * The ListView class is the view that contains all the patients, on the left.
 * There is an event listener to flag patients to be shown in the TableView.
 * The check box corresponds to whether or not the patient should be in the table or not.
 */
public class ListView extends GUIElement{

    JList list;
    JList bloodList;

    /**
     * The constructor for the ListView.
     * @param source the source patientTable.
     * @param frame the JFrame that the view will exist within.
     */
    public ListView(PatientTable source, JFrame frame){
        setDataSource(source);//use the inherited methods to set the data source and frame
        setFrame(frame);

        update();

        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(new JScrollPane(list));
        frame.getContentPane().add(new JScrollPane(bloodList));
        frame.pack();
        frame.setSize(1550, 1000);
    }

    /**
     * this is called when anything in the data source changes. can be expanded later
     */
    @Override
    public void update() {
        //update the list here
        recreateList();

    }

    /**
     * This is the method that recreates the list. Realistically, this should never need to be called past the
     * first time because it never gets changed - nothing the other observers do actually change it.
     * I modularised it because this might not be the case in the future - for example a person's name could be edited
     * or something similar.
     */
    public void recreateList(){

        //the list only shows name, put all the names in data
        int noPatients = dataSource.getpatTable().size();
        CheckboxElement[] data = new CheckboxElement[noPatients]; //uses a ListElement array. custom class required for checkbox
        CheckboxElement[] boxes = new CheckboxElement[noPatients];

        for (int i = 0; i < noPatients; i++){
            Patient patient = dataSource.getpatTable().get(i);
            data[i] = new CheckboxElement(patient.getName());
            boxes[i] = new CheckboxElement("");
        }

        //create a new list with the data.
        list = new JList(data);
        bloodList = new JList(boxes);

        //set the cell renderer to the custom class so the check boxes are handled properly
        list.setCellRenderer(new CellRenderer());
        bloodList.setCellRenderer(new CellRenderer());

        ArrayList<Integer> blank = new ArrayList();


        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//only 1 index can be selected at a time
        bloodList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // reassign the list
                JList list = (JList) e.getSource();
                int index = list.locationToIndex(e.getPoint());// Get index of item clicked

                CheckboxElement item = (CheckboxElement) list.getModel().getElementAt(index);// get the item at the index
                item.setSelected(!item.isSelected()); // Toggle selected state

                //using index here is fine because the list is made from the data source.
                dataSource.flagPatientTableChol(index); //searching for the correct item from the index takes power
                                                    //that isn't necessary here, but is in other views.

                list.repaint(list.getCellBounds(index, index));// Repaint cell
            }
        });

        bloodList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // reassign the list
                JList list = (JList) e.getSource();
                int index = list.locationToIndex(e.getPoint());// Get index of item clicked

                CheckboxElement item = (CheckboxElement) list.getModel().getElementAt(index);// get the item at the index
                item.setSelected(!item.isSelected()); // Toggle selected state

                //using index here is fine because the list is made from the data source.
                dataSource.flagPatientTableBlood(index); //searching for the correct item from the index takes power
                                                    //that isn't necessary here, but is in other views.

                list.repaint(list.getCellBounds(index, index));// Repaint cell
            }
        });
    }
}
