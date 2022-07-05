package ObserverPattern;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilterView extends GUIElement
{
    JTextField textDia;
    JTextField textSys;

    String defaultDia = "Diastolic Blood Pressure Filter: ";
    String defaultSys = "Systolic Blood Pressure Filter: ";

    JLabel lblDia;
    JLabel lblSys;

    JButton btnUpdate;

    public FilterView(PatientTable source, JFrame frame){
        setDataSource(source);
        setFrame(frame);

        lblDia = new JLabel(defaultDia + 80);
        lblSys = new JLabel(defaultSys + 130);

        textDia = new JTextField(3);
        textSys = new JTextField(3);

        btnUpdate = new JButton("Update Filter");

        btnUpdate.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try{
                    dataSource.setDiaFilter(Integer.parseInt(textDia.getText()));
                } catch (Exception ignored){}
                try{
                    dataSource.setSysFilter(Integer.parseInt(textSys.getText()));
                } catch (Exception ignored){}
                dataSource.notifyObservers();
            }
        });

        Container Pane = frame.getContentPane();
        Pane.setLayout(new FlowLayout());

        Pane.add(lblSys);
        Pane.add(textSys);
        Pane.add(lblDia);
        Pane.add(textDia);
        Pane.add(btnUpdate);

        frame.pack();
        frame.setSize(1550, 1000);
    }

    @Override
    public void update()
    {
        lblDia.setText(defaultDia + dataSource.getDiaFilter());
        lblSys.setText(defaultSys + dataSource.getSysFilter());
    }
}
