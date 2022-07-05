package ObserverPattern;

import GUI.Classes.TableCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * The TableView is the class that holds the patient's data in a table format.
 * There is an event listener to flag which patient should be shown in the extended view.
 * The red highlighting is whether or not the patient has an blood cholesterol level
 * than the average of all monitored patients.
 */
public class TableView extends GUIElement {

    DefaultTableModel tableModel = new DefaultTableModel(){

        /**
         * Returns all cells to be not editable
         * @param row a row
         * @param column a column
         * @return a boolean - will always be false in this case
         */
    @Override
    public boolean isCellEditable(int row, int column) {
       //all cells false
       return false;
    }
};
    JTable table = new JTable(tableModel);

    TableCellRenderer tableCellRenderer;

    /**
     * the constructor of the TableView object. takes as input a patientTable data source and a Jframe.
     * @param source a patientTable
     * @param frame a JFrame
     */
    public TableView(PatientTable source, JFrame frame){
        setDataSource(source);
        setFrame(frame);

        //set the table columns
        tableModel.addColumn("Name");
        tableModel.addColumn("Total Cholesterol (mg/dL)");
        tableModel.addColumn("Time");
        tableModel.addColumn("Systolic Blood Pressure (mmHg)");
        tableModel.addColumn("Diastolic Blood Pressure (mmHg)");
        tableModel.addColumn("Time");

        table.setCellSelectionEnabled(true);//allow the table rows to be selected
        table.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int index = table.rowAtPoint(e.getPoint());// Get index of item clicked
                int col = table.columnAtPoint(e.getPoint());

                if (index != -1) {//if the index is valid
                    if (col <= 2)
                    {
                        String currName = (String) table.getValueAt(index, 0);//assumes name is unique

                        int noPatients = dataSource.getpatTable().size();

                        for (int i = 0; i < noPatients; i++)
                        {
                            Patient currPat = dataSource.getpatTable().get(i);//check the data against every patient
                            if (currPat.getName() == currName)
                            {
                                //if the patient matches flip the boolean
                                dataSource.flagPatientExpanded(i);
                                break;
                            }
                        }
                    }
                    else
                    {
                        String currName = (String) table.getValueAt(index, 0);//assumes name is unique

                        int noPatients = dataSource.getpatTable().size();

                        for (int i = 0; i < noPatients; i++)
                        {
                            Patient currPat = dataSource.getpatTable().get(i);//check the data against every patient
                            if (currPat.getName() == currName)
                            {
                                //if the patient matches flip the boolean
                                if (col == 3 && !((String) table.getValueAt(index, 3)).equals("-"))
                                {
                                    if (Float.parseFloat(currPat.getSysBloodPressure()[0]) >= dataSource.getSysFilter())
                                    {//validation
                                        dataSource.flagPatientBlood(i);
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        });

        update();

        frame.setLayout(new FlowLayout());
        frame.getContentPane().setBounds(0, 0, 1000, 300);
        Container Pane = frame.getContentPane();
        Pane.setLayout(new FlowLayout());

        Pane.add(new JScrollPane(table){
            @Override
            public Dimension getPreferredSize(){
                return new Dimension(1000, 300);
            }
        });

        frame.pack();
        frame.setSize(1550, 1000);
    }

    /**
     * This is the method that is run when the data source is updated
     * resets the current selection so it doesn't bug out
     */
    @Override
    public void update() {
        table.setRowSelectionAllowed(false);
        table.setRowSelectionAllowed(true);
        repopulateTable();
    }

    /**
     * Populates the table by checking whether the patient's isInTable is true or false.
     * If it is true, it checks if it is already in the table. If it isn't, add it
     * If it is false, it checks if it is already in the table. If it is, remove it
     */
    public void repopulateTable(){
        //creates a new model
        int noPatients = dataSource.getpatTable().size();

        ArrayList<Integer> rowsToColourRed= new ArrayList();
        ArrayList<Integer> rowsToColourPurple = new ArrayList();
        ArrayList<Integer> rowsToColourPurple2 = new ArrayList();
        ArrayList<Integer> rowsToColourGreen = new ArrayList();

        float totalChol = 0;
        float totalCholData = 0;
        for (int i = 0; i < noPatients; i++) // for each patient
        {
            if(dataSource.getpatTable().get(i).isInTableChol() || dataSource.getpatTable().get(i).isInTableBlood())
            {
                Patient patient = dataSource.getpatTable().get(i);
                Object[] newEntry = new Object[]{patient.getName(), "-", "-", "-", "-", "-"};

                float currChol = 0;
                if (patient.isInTableChol())
                {
                    newEntry[1] = patient.getTotCholesterol();
                    newEntry[2] = patient.getTime();
                    try
                    {
                        currChol = Float.parseFloat(patient.getTotCholesterol());
                        totalCholData += 1;
                    } catch (Exception ignored) {}
                }
                if (patient.isInTableBlood())
                {
                    newEntry[3] = Float.toString(Float.parseFloat(patient.getSysBloodPressure()[0]));
                    newEntry[4] = Float.toString(Float.parseFloat(patient.getDiaBloodPressure()[0]));
                    newEntry[5] = patient.getBloodTime()[0];
                }

                totalChol += currChol;//helps to calculate average cholesterol
                int[] resultTable = inTable(table, newEntry);
                if (resultTable[0] == 0)//if the patient that is true is not in the table
                {
                    tableModel.addRow(newEntry);//add it
                }
                else if (resultTable[0] == 1)//if the patient that is true is in the table
                {
                    for (int j = 1; j < 6; j += 1)
                    {
                        tableModel.setValueAt(newEntry[j], resultTable[1], j);//update the entire row to the new entry
                    }
                }

            }
            else if(!dataSource.getpatTable().get(i).isInTableChol())
            {
                Patient patient = dataSource.getpatTable().get(i);
                Object[] oldEntry = new Object[]{patient.getName(), patient.getTotCholesterol(), patient.getTime()};
                int[] resultTable = inTable(table, oldEntry);
                if (resultTable[0] == 1)//if the patient that is false is in the table
                {
                    if (resultTable[1] != -1){
                        tableModel.removeRow(resultTable[1]);//remove it
                    }
                }
            }
        }

        float averageChol = totalChol / totalCholData;

        int sysFilter = dataSource.getSysFilter();
        int diaFilter = dataSource.getDiaFilter();

        //rows that are higher than the average get "flagged"
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String choldata = (String) tableModel.getValueAt(i, 1);
            if (choldata != "-")
            {
                if (Float.parseFloat(choldata) > averageChol)
                {
                    rowsToColourRed.add(i);
                }
            }

            //make the 2 text boxes uneditable
            //here check the input of the 2 boxes and flag the correct rows
            //make the 2 text boxes editable again

            Float sysData = Float.parseFloat("-1");
            Float diaData = Float.parseFloat("-1");
            try
            {
                sysData = Float.parseFloat((String) tableModel.getValueAt(i, 3));
            } catch (Exception ignored) {}

            try
            {
                diaData = Float.parseFloat((String) tableModel.getValueAt(i, 4));
            } catch (Exception ignored){}


            if (sysData >= sysFilter)
                rowsToColourPurple.add(i);
            if (diaData >= diaFilter)
                rowsToColourPurple2.add(i);

            String currName = (String) tableModel.getValueAt(i, 0);//assumes name is unique

            for (int j = 0; j < noPatients; j++)
            {
                Patient currPat = dataSource.getpatTable().get(j);//check the data against every patient
                if (currPat.getName() == currName)
                {
                    if (currPat.isInBlood())
                        rowsToColourGreen.add(i);
                }
            }
        }


        //flagged rows are coloured in the cell renderer
        tableCellRenderer = new TableCellRenderer(rowsToColourRed, rowsToColourPurple, rowsToColourPurple2, rowsToColourGreen);


        table.setDefaultRenderer(Object.class, tableCellRenderer);//set the cell renderer
        table.repaint();
    }

    /**
     * This is a function to check whether a patient exists in the table.
     * If it does, return its index too
     * If it doesn't, return -1 signifying no index
     * Ajusal Sugathan.(2015). How to check if a value exists in jTable which I am trying to add?
     * Retrieved from https://stackoverflow.com/questions/15639611/how-to-check-if-a-value-exists-in-jtable-which-i-am-trying-to-add
     * @param table the table to be looked at
     * @param newPatient the patient to be found
     * @return an int[]
     */
    public int[] inTable(JTable table, Object[] newPatient){
        int rows = table.getRowCount();

        String newPat = newPatient[0].toString();//patient name

        for (int i = 0; i < rows; i++)
        {
            String currPat = table.getValueAt(i, 0).toString();//make the current value into string
            if (currPat.equalsIgnoreCase(newPat)) //compare them
            {
                return new int[]{1, i};//if they match return
            }
        }
        return new int[]{0, -1};//if none match return this
    }
}
