import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the view where the counter for N will be incremented up and down.
 * When the interval is changed(a button is clicked) then the method in GUI is called to update the interval time.
 */
public class CounterView{

    int interval;

    private JLabel label;

    /**
     * The constructor. Makes a label and two buttons, one to make the time go up and one to make it go down.
     * @param gui the GUI it is attached to. calls its method.
     */
    public CounterView(final GUI gui){
        //set up label and buttons here.
        //buttons update the timer interval in GUI
        JFrame frame = gui.frame;
        interval = 30;
        JButton btnDown = new JButton("Down");
        Dimension btnDimension = new Dimension(70, 30);
        JButton btnUp = new JButton("Up");

        btnDown.setPreferredSize(btnDimension);
        btnUp.setPreferredSize(btnDimension);

        label = new JLabel("30");

        label.setPreferredSize(new Dimension(30, 30));

        btnDown.addActionListener(new ActionListener() {
            @Override
            /**
             * makes the button update the interval downwards.
             */
            public void actionPerformed(ActionEvent e) {
                interval -= 1;
                label.setText(Integer.toString(interval));
                gui.setTimerInterval(interval*1000);//accepts it in milliseconds
            }
        });

        btnUp.addActionListener(new ActionListener() {
            @Override
            /**
             * makes the button update the interval upwards.
             */
            public void actionPerformed(ActionEvent e) {
                interval += 1;
                label.setText(Integer.toString(interval));
                gui.setTimerInterval(interval*1000);//accepts it in milliseconds
            }
        });

        //sets the layout
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(new JScrollPane(btnDown));
        frame.getContentPane().add(new JScrollPane(label));
        frame.getContentPane().add(new JScrollPane(btnUp));
        frame.pack();
        frame.setSize(1550, 1000);

    }
}
