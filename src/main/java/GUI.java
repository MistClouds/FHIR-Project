import ObserverPattern.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.Timer;

/**
 * GUI class that holds all the GUI elements
 */
public class GUI {
    JFrame frame = new JFrame();

    JTextField textField = new JTextField(10);
    JButton button = new JButton("Submit");

    ListView patientList;
    TableView patientTable;
    ExpandedView patientExpanded;
    FilterView filterValues;
    SystolicBloodListView patientSystolicListExpanded;
    CholesterolGraphView patientCholesterolGraphExpanded;
    SystolicBloodGraphView patientSystolicGraphExpanded;

    private Client client;

    CounterView counterView;
    private Timer timer;

    private int timerInterval;

    /**
     * constructor for GUI - creates the initial frame
     */
    public GUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        timer = new Timer();

        frame.setSize(1550, 1000);
        frame.setVisible(true);
    }


    /**
     * shows the Practitioner ID input screen
     * @param client a client object
     */
    public void showIDScreen(final Client client){
        JLabel label = new JLabel("Please Input Practitioner ID: ");
        final JPanel panel = new JPanel();

        this.client = client;

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(panel);
                frame.repaint();
                client.initialPopulate(textField.getText());
            }
        });

        panel.add(label);
        panel.add(textField);
        panel.add(button);

        frame.add((panel));
        frame.pack();
        frame.setSize(1550, 1000);
    }

    /**
     * This is called after the initial API call to build the main 3 views: the list of patients,
     * the table of monitored patients and the panel where a monitored patient
     * @param table a patient table
     */
    public void createApp(PatientTable table){
        patientList = new ListView(table, frame);
        table.attach(patientList);

        patientTable = new TableView(table, frame);
        table.attach(patientTable);

        patientExpanded = new ExpandedView(table, frame);
        table.attach(patientExpanded);

        filterValues = new FilterView(table, frame);
        table.attach(filterValues);

        counterView = new CounterView(this);

        patientSystolicListExpanded = new SystolicBloodListView(table, frame);
        table.attach(patientSystolicListExpanded);

        patientCholesterolGraphExpanded = new CholesterolGraphView(table, frame);
        table.attach(patientCholesterolGraphExpanded);

        patientSystolicGraphExpanded = new SystolicBloodGraphView(table, frame);
        table.attach(patientSystolicGraphExpanded);



        setTimerInterval(30000);//every thirty seconds
    }

    /**
     * This is the method that sets the timer interval(to check for updates in the server)
     * @param interval an integer
     */
    public void setTimerInterval(int interval) {
        this.timerInterval = interval;

        timer.cancel();//cancels old timer task
        timer.purge();//purges old timer

        timer = new Timer();//creates new timer

        timer.schedule(new refreshTableTask(client), 3000, timerInterval);//has delay of 3 seconds
    }
}
