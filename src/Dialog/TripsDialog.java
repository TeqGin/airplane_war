package Dialog;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author 许达峰
 * @time 2020.5.14
 * Abandoned
 * */

public class TripsDialog extends JDialog  {
    private String trips;
    private JLabel background;

    public TripsDialog(Frame owner, String title, String trips) {
        super(owner, title);
        this.trips = trips;
        this.setLayout(new FlowLayout());

        background=new JLabel(new ImageIcon("static/image/map/warn_bg.png"));
        background.setBounds(0,0,300,100);
        this.getLayeredPane().add(background);

        JPanel container=(JPanel) this.getContentPane();
        container.setOpaque(false);

        container.add(new JLabel(trips));
        this.setBounds(600,400,300,100);
        this.setResizable(false);
        JLabel sure=new JLabel(new ImageIcon("static/image/icon/confirm.png"));
        sure.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TripsDialog.this.dispose();
            }
        });
        container.add(sure);
        this.setVisible(true);
    }
}
