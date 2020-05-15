package Dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TripsDialog extends JDialog implements ActionListener {
    private String trips;

    public TripsDialog(Frame owner, String title, String trips) {
        super(owner, title);
        this.trips = trips;
        this.setLayout(new FlowLayout());

        this.add(new JLabel(trips));
        this.setBounds(600,400,300,100);
        JButton confirm=new JButton("确定");
        confirm.addActionListener(this);
        this.add(confirm);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
    }
}
