package ObserverPattern;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class CholesterolGraphView extends GUIElement{

    ChartPanel chartPanel;

    /**
     * This is the constructor for the cholesterolGraphView. takes as input the data source,
     * and the frame so its knows which Frame it is part of.
     * @param source a patientTable data source
     * @param frame a JFrame that it will be rendered on
     */
    public CholesterolGraphView(PatientTable source, JFrame frame) {

        setDataSource(source);
        setFrame(frame);

        update();

        frame.setSize(1550, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * this is called when anything in the data source changes. can be expanded later
     */
    @Override
    public void update() {
        showCholesterolGraphView();
    }

    /**
     * This is the method the updates and displays the relevant information. It does this by removing the previous
     * graph that was being displayed on the GUI and then looping through the patients where they are flagged to
     * appear on the graph. Then relevant information is acquired and added to the dataset used to generate the graph.
     *
     */
    public void showCholesterolGraphView() {
        try
        {
            frame.getContentPane().remove(chartPanel);
        } catch (Exception ignored){}
        int noPatients = dataSource.getpatTable().size();
        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset( );
        for(int i = 0; i < noPatients; i++){
            if(dataSource.getpatTable().get(i).isInTableChol() && dataSource.getpatTable().get(i).getTotCholesterol() != "-"){
                dataset.addValue(
                        Double.parseDouble(dataSource.getpatTable().get(i).getTotCholesterol()),
                        "Cholesterol Value",
                        dataSource.getpatTable().get(i).getName());
            }
        }

        JFreeChart chart = ChartFactory.createBarChart("Total Cholesterol mg/dL", "Name", "Value", dataset);
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500,200));
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setSize(1550, 1000);
        return;
    }

}
