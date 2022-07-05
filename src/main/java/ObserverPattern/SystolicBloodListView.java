package ObserverPattern;

import javax.swing.*;
import java.awt.*;

public class SystolicBloodListView  extends GUIElement{

    DefaultListModel listModel = new DefaultListModel();
    JList bloodListValue = new JList(listModel);

    /**
     * This is the constructor for the systolicBloodListView. takes as input the data source,
     * and the frame so its knows which Frame it is part of.
     * @param source a patientTable data source
     * @param frame a JFrame that it will be rendered on
     */
    public SystolicBloodListView(PatientTable source, JFrame frame){
        setDataSource(source);
        setFrame(frame);

        update();

        //sets the layout
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(new JScrollPane(bloodListValue)
        {
            @Override
            public Dimension getPreferredSize()
            {
                return new Dimension(1000, 200);
            }
        });
        frame.pack();
        frame.setSize(1550, 1000);
    }

    /**
     * this is called when anything in the data source changes. can be expanded later
     */
    @Override
    public void update() {
        showBloodListView();
    }

    /**
     * This is the method that updates and displays the relevant blood value of the previous 5 records. This is done
     * through the checking of a flag, which would then subsequently display the required information as provided by
     * the API calls.
     */
    public void showBloodListView(){
        int noPatients = dataSource.getpatTable().size();
        listModel.clear();
        for(int i = 0; i < noPatients; i++){
            if(dataSource.getpatTable().get(i).isInBlood()){
                String[] patientSystolicBloodValues = dataSource.getpatTable().get(i).getSysBloodPressure();
                String[] patientSystolicBloodTimes = dataSource.getpatTable().get(i).getBloodTime();
                String patientSystolicString = dataSource.getpatTable().get(i).getName() + ": ";
                for(int j = 0; j < 5; j++){
                    patientSystolicString += Double.parseDouble(patientSystolicBloodValues[j]) +
                            " (" + patientSystolicBloodTimes[j] + ")";
                    if(j != 4){
                        patientSystolicString += ", ";
                    }
                }
                listModel.addElement(patientSystolicString);
            }
        }
    }
}
