package Frame;

import Domain.Data;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author 许达峰
 * @time 2020.5.14
 * */

public class MainFrame extends JFrame {
    private JButton exit=new JButton("退出当前帐号");
    private JLabel beginLabel;
    private JPanel container;


    public MainFrame(String title) throws HeadlessException {
        super(title);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(Data.x,Data.y,Data.width,Data.height);
        //set unable to change the size
        this.setResizable(false);
        Data.backgroundMusic.changeMusic("static/music/bgm_3.wav");
        if (!"".equals(Data.user.getUserPlaneAddress())&&Data.user.getUserPlaneAddress()!=null){
            Data.userPlaneAddress=Data.user.getUserPlaneAddress();
        }
        if (!"".equals(Data.user.getUserBulletAddress())&&Data.user.getUserBulletAddress()!=null){
            Data.userPlaneBulletAddress=Data.user.getUserBulletAddress();
        }

        ImageIcon icon=new ImageIcon("static/image/map/bg_cloud.jpg");
        JLabel mainBackground=new JLabel(icon);
        mainBackground.setBounds(0,0,Data.width,Data.height);
        this.getLayeredPane().add(mainBackground,new Integer(Integer.MIN_VALUE));



        //get the contain
        container=(JPanel) this.getContentPane();
        container.setOpaque(false);
        //set layout style
        container.setLayout(new FlowLayout());
        container.setBounds(Data.x,Data.y,Data.width,Data.height);

        //add the begin picture
        JPanel beginPanel=new JPanel();
        beginPanel.setLayout(new FlowLayout());
        beginPanel.setBounds((int)(Data.width*0.3),500,200,100);
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

        addPlaneStrongButton();
        addLogo();
        addHelp();
        addExitButton();


        this.setLayout(null);
        setVisible(true);
    }

    private void addPlaneStrongButton(){

        JLabel planeStrong=new JLabel(new ImageIcon("static/image/icon/strong_plane.png"));
        JLabel armsStrong=new JLabel(new ImageIcon("static/image/icon/arms_level_up.png"));

        JPanel planeStrongPanel=new JPanel();

        planeStrongPanel.add(armsStrong);
        planeStrongPanel.add(planeStrong);

        planeStrongPanel.setBounds((int)(Data.width*0.3),350,200,400);
        planeStrongPanel.setOpaque(false);

        container.add(planeStrongPanel);

        planeStrong.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        armsStrong.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MainFrame.this.dispose();
                ArmsStrongFrame armsStrongFrame =new ArmsStrongFrame("static/image/map/bg_settlement.jpg");
            }
        });

        planeStrong.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MainFrame.this.dispose();
                PlaneStrongFrame planeStrongFrame=new PlaneStrongFrame();
            }
        });

    }

    private void addLogo(){
        JLabel logo=new JLabel(new ImageIcon("static/image/icon/person_man.png"));
        JLabel logo2=new JLabel(new ImageIcon("static/image/icon/person_girl.png"));

        JPanel logoPanel=new JPanel();
        JPanel logoPanel2=new JPanel();

        logoPanel.add(logo);
        logoPanel2.add(logo2);

        logoPanel.setOpaque(false);
        logoPanel2.setOpaque(false);

        logoPanel2.setBounds(220,120,300,300);
        logoPanel.setBounds(0,0,300,300);

        container.add(logoPanel2);
        container.add(logoPanel);
    }

    private void addHelp(){
        JLabel about=new JLabel(new ImageIcon("static/image/icon/about.png"));
        JPanel aboutPanel=new JPanel();

        aboutPanel.add(about);
        aboutPanel.setOpaque(false);
        aboutPanel.setBounds(320,580,200,200);

        about.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String message="版本信息：1.0.0\n" +
                        "作者：许达峰\n" +
                        "完成时间：2020.5.24" +
                        "游戏介绍：\n" +
                        "使用上下左右键进行操控飞机飞行，\n" +
                        "游戏过程中可按下空格键暂停游戏。\n" +
                        "开始游戏时请使用鼠标点击一下游戏屏幕以使得飞机飞行\n" +
                        "游戏愉快！";
                JOptionPane.showMessageDialog(null,message,"关于与帮助",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        container.add(aboutPanel);
    }

    private void addExitButton(){
        JLabel exit=new JLabel(new ImageIcon("static/image/icon/exit.png"));

        JPanel exitPanel=new JPanel();
        exitPanel.setOpaque(false);
        exitPanel.setBounds(320,0,200,200);
        exitPanel.add(exit);

        container.add(exitPanel);

        exit.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MainFrame.this.dispose();
                LoginFrame loginFrame=new LoginFrame("登陆界面");
            }
        });
    }

}
