package Frame;


import Domain.Data;
import Dialog.TripsDialog;
import Domain.User;
import Service.UserService;
import Utils.MusicUtil;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;

/**
 * @author 许达峰
 * @time 2020.5.14
 * */
public class LoginFrame extends JFrame {
    private  JTextField name=new JTextField("请输入账号",15);
    private  JPasswordField password=new JPasswordField("请输入密码",15);
    private JLabel submitLabel;
    private JLabel register;


    public LoginFrame(String title) throws HeadlessException {
        super(title);
        //play the music and avoid load too much music file
        if (Data.backgroundMusic==null){
            Data.backgroundMusic=new MusicUtil();
            Data.backgroundMusic.loadMusic("static/music/bg_music_1.wav");
            Data.backgroundMusic.play();
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //load a picture
        ImageIcon ico=new ImageIcon("static/image/map/bg_sky_with_logo.jpg");
        // initialize a label by ImageIcon
        JLabel imageLabel=new JLabel(ico);
        Data.width =ico.getIconWidth();
        Data.height=ico.getIconHeight();
        imageLabel.setBounds(0,0,Data.width,Data.height);
        //add into the container
        this.getLayeredPane().add(imageLabel,new Integer(Integer.MIN_VALUE));
        //set unable to change the size
        this.setResizable(false);

        //only the JPanel have the function 'setOpaque',so transform the Container to JPanel
        JPanel container=(JPanel) this.getContentPane();
        //使容器透明
        container.setOpaque(false);

        //add the account filed
        JPanel namePanel=new JPanel();
        namePanel.setLayout(new FlowLayout());
        namePanel.add(new JLabel("账号"));
        namePanel.add(name);
        namePanel.setBounds(Data.width/4,927/3,250,50);
        namePanel.setOpaque(false);
        //bind the focus event to the TextFiled
        name.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (name.getText().equals("请输入账号")){
                    name.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (name.getText().equals("")){
                    name.setText("请输入账号");
                }
            }

        });

        //add the password text
        JPanel passwordPanel=new JPanel();
        passwordPanel.add(new JLabel("密码"));
        passwordPanel.add(password);
        //able to see passwordFiled
        password.setEchoChar('\0');
        passwordPanel.setBounds(Data.width/4,927/3+50,250,50);
        passwordPanel.setOpaque(false);
        password.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (String.valueOf(password.getPassword()).equals("请输入密码")){
                    password.setEchoChar('*');
                    password.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (String.valueOf(password.getPassword()).equals("")){
                    password.setEchoChar('\0');
                    password.setText("请输入密码");
                }
            }
        });

        //add the button
        JPanel subPanel=new JPanel();
        submitLabel=new JLabel(new ImageIcon("static/image/icon/confirm.png"));
        register=new JLabel(new ImageIcon("static/image/icon/register.png"));
        subPanel.add(submitLabel);
        subPanel.add(register);

        subPanel.setBounds(Data.width/4-20,927/3+100,300,200);
        subPanel.setOpaque(false);


        //add the items into the container
        container.add(namePanel);
        container.add(passwordPanel);
        container.add(subPanel);

        this.setTitle(title);
        //able to exit the program
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(Data.x,Data.y,Data.width,Data.height);
        //make the frame null-layout
        this.setLayout(null);
        //able to see
        this.setVisible(true);

        //to bind the submit picture to click event
        submitLabel.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //get the content of the textField
                String account=name.getText();
                String passwordString= String.valueOf(password.getPassword());

                if (UserService.isExist(account,passwordString)){
                    //record the account and password
                    Data.user=UserService.findUserById(account);

                    MainFrame mainFrame=new MainFrame("雷霆战机");
                    //destroy the login interface by use the outer-this
                    LoginFrame.this.dispose();
                }else {
                    JOptionPane.showMessageDialog(null,"账号或密码错误!","提示",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        register.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                RegisterFrame registerFrame=new RegisterFrame("注册");
                LoginFrame.this.dispose();
            }
        });
    }

}
