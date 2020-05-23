package Frame;

import Domain.Data;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
/**
 * @author 许达峰
 * @time 2020.5.15
 * */

public class CheckPointFrame extends JFrame {
    private JLabel level1;
    private JPanel container;

    public CheckPointFrame(String title) throws HeadlessException {
        super(title);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(Data.x,Data.y,Data.width,Data.height);
        //set unable to change the size
        this.setResizable(false);

        JLabel background=new JLabel(new ImageIcon("static/image/map/bg_planet.jpg"));
        background.setBounds(0,0,Data.width,Data.height);
        this.getLayeredPane().add(background,new Integer(Integer.MIN_VALUE));

        container=(JPanel)this.getContentPane();
        container.setOpaque(false);

        addBackButton();

        level1=new JLabel(new ImageIcon("static/image/icon/1_level.png"));
        JPanel point=new JPanel();
        point.setOpaque(false);
        point.add(level1);
        point.setBounds(50,50,100,100);
        level1.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CheckPointFrame.this.dispose();
                GameFrame gameFrame=new GameFrame("游戏面板");
                gameFrame.setMapAddress(Data.mapAddress);
                gameFrame.setUserPlaneAddress(Data.userPlaneAddress);
                gameFrame.setUserPlaneBulletAddress(Data.userPlaneBulletAddress);
                Data.enemyNumber=2;
                Data.targetScore=10000;
                Data.speed=30;
                gameFrame.initCanvas();
            }
        });

        container.add(point);

        this.setLayout(null);
    }

    private void addBackButton(){
        JPanel backPanel=new JPanel();
        JLabel back=new JLabel(new ImageIcon("static/image/icon/back_menu.png"));
        backPanel.add(back);
        backPanel.setBounds(100,500,300,100);

        backPanel.setOpaque(false);
        back.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CheckPointFrame.this.dispose();
                MainFrame mainFrame=new MainFrame("雷霆战机");
            }
        });
        container.add(backPanel);
    }
}
