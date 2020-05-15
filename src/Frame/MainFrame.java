package Frame;

import Domain.Data;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    private JButton exit=new JButton("退出当前帐号");
    private JLabel beginLabel;


    public MainFrame(String title) throws HeadlessException {
        super(title);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(Data.x,Data.y,Data.width,Data.height);

        //set unable to change the size
        this.setResizable(false);

        ImageIcon icon=new ImageIcon("static/image/map/bg_river.jpg");
        JLabel mainBackground=new JLabel(icon);
        mainBackground.setBounds(0,0,Data.width,Data.height);
        this.getLayeredPane().add(mainBackground,new Integer(Integer.MIN_VALUE));

        //get the contain
        JPanel container=(JPanel) this.getContentPane();
        container.setOpaque(false);
        //set layout style
        container.setLayout(new FlowLayout());
        container.setBounds(Data.x,Data.y,Data.width,Data.height);

        //add the begin picture
        JPanel beginPanel=new JPanel();
        beginPanel.setLayout(new FlowLayout());
        beginPanel.setBounds((int)(Data.width*0.3),450,200,100);
        beginPanel.setOpaque(false);

        ImageIcon beginIcon=new ImageIcon("static/image/icon/begin.png");
        beginLabel=new JLabel(beginIcon);
        beginPanel.add(beginLabel);
        beginLabel.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MainFrame.this.dispose();
                CheckPointFrame checkPointFrame=new CheckPointFrame("选择关卡");
                checkPointFrame.setVisible(true);
            }
        });

        container.add(beginPanel);

        this.setLayout(null);
    }
}
