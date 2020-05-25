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
    private JLabel level2;
    private JLabel level3;
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

        //in fact those code can use `for` to generate

        level1=new JLabel(new ImageIcon("static/image/icon/1_level.png"));
        level2=new JLabel(new ImageIcon("static/image/icon/2_level.png"));
        level3=new JLabel(new ImageIcon("static/image/icon/3_level.png"));
        JPanel point=new JPanel();
        point.setOpaque(false);
        point.add(level1);
        point.add(level2);
        point.add(level3);
        point.setBounds(0,200,500,300);
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
                Data.hasBoos=false;
                Data.enemyType=2;
                Data.enemyBulletType=2;

                gameFrame.initCanvas();
            }
        });
        level2.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CheckPointFrame.this.dispose();
                GameFrame gameFrame=new GameFrame("游戏面板");
                Data.mapAddress="static/image/map/bg_start_cloud.jpg";
                gameFrame.setMapAddress(Data.mapAddress);
                gameFrame.setUserPlaneAddress(Data.userPlaneAddress);
                gameFrame.setUserPlaneBulletAddress(Data.userPlaneBulletAddress);
                gameFrame.setBoosPlaneAddress("static/image/plane/boss_1.png");
                gameFrame.setBoosPlaneBulletAddress("static/image/bullet/enemy_bullet_2.png");

                Data.enemyNumber=3;
                Data.targetScore=15000;
                Data.speed=30;
                Data.hasBoos=true;
                Data.proportion=0.55f;
                Data.enemyType=4;
                Data.enemyBulletType=3;

                gameFrame.initCanvas();
            }
        });
        level3.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CheckPointFrame.this.dispose();
                GameFrame gameFrame=new GameFrame("游戏面板");
                Data.mapAddress="static/image/map/bg_volcanic.jpg";
                gameFrame.setMapAddress(Data.mapAddress);
                gameFrame.setUserPlaneAddress(Data.userPlaneAddress);
                gameFrame.setUserPlaneBulletAddress(Data.userPlaneBulletAddress);
                Data.enemyNumber=4;
                Data.targetScore=20000;
                Data.speed=30;
                Data.hasBoos=false;
                Data.proportion=0.6f;
                Data.enemyType=6;
                Data.enemyBulletType=5;
                gameFrame.initCanvas();
            }
        });

        container.add(point);
        this.setLayout(null);
    }

    //add back button
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
