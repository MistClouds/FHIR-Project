package ObserverPattern;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SystolicBloodGraphView extends  GUIElement{

    ArrayList<ChartPanel> arrChartPanel = new ArrayList<>();

    /**
     * This is the constructor for the systolicBloodGraphView. takes as input the data source,
     * and the frame so its knows which Frame it is part of.
     * @param source a patientTable data source
     * @param frame a JFrame that it will be rendered on
     */
    public SystolicBloodGraphView(PatientTable source, JFrame frame) {

        setDataSource(source);
        setFrame(frame);

        update();

        frame.setSize(1550, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        frame.setSize(1550, 1000);
    }

    /**
     * this is called when anything in the data source changes. can be expanded later
     */
    @Override
    public void update() {
        showSystolicBloodGraphView();
    }


    /**
     * This is the method that updates and displays the relevant systolic blood graphs of patients that are flagged
     * to appear in the display. This is done by ensuring that the array containing the existing information is wiped
     * clean to allow for new information to be inserted into the arraylist which is then used to create the graphs.
     */
    public void showSystolicBloodGraphView(){
        int noPatients = dataSource.getpatTable().size();

        if(arrChartPanel.size() != 0){
            for (ChartPanel panel : arrChartPanel)
            {
                frame.getContentPane().remove(panel);
            }
            arrChartPanel.clear();
        }

        for(int i = 0; i < noPatients; i++){
            XYSeriesCollection dataset = new XYSeriesCollection();
            XYSeries series = new XYSeries("Time");
            if(dataSource.getpatTable().get(i).isInBlood()){
                String[] patientSystolicBloodValues = dataSource.getpatTable().get(i).getSysBloodPressure();
                String patientName = dataSource.getpatTable().get(i).getName();
                for(int j = 0; j < 5; j++){
                    if(patientSystolicBloodValues[j] != "-"){
                        series.add(j, Double.parseDouble(patientSystolicBloodValues[j]));
                    }
                }
                dataset.addSeries(series);

                JFreeChart chart = ChartFactory.createXYLineChart(patientName, null, null, dataset);
                arrChartPanel.add(new ChartPanel(chart));

            }
        }

        System.out.println(arrChartPanel);

        for (ChartPanel panel : arrChartPanel)
        {
            panel.setPreferredSize(new Dimension(300, 200));
            frame.getContentPane().add(panel);
        }

        frame.pack();
        frame.setSize(1550, 1000);
    }
}
