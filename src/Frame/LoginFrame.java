package Frame;

import Dao.UserDao;
import Domain.Data;
import Domain.User;
import Dialog.TripsDialog;
import Service.UserService;
import Utils.MusicUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class LoginFrame extends JFrame {
    private  JTextField name=new JTextField("请输入账号",15);
    private  JPasswordField password=new JPasswordField("请输入密码",15);
    private  JButton  submit=new JButton("登陆");
    private  JButton registration=new JButton("注册");

    public LoginFrame(String title) throws HeadlessException {
        super(title);
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

        //add the account text
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
                name.setText("");
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
                password.setEchoChar('*');
                password.setText("");
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
        subPanel.add(submit);
        subPanel.add(registration);
        submit.setSize(65,35);
        registration.setSize(65,35);
        subPanel.setBounds(Data.width/4+30,927/3+100,200,200);
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

        //bind the submit to a action
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get the content of the textField
                String account=name.getText();
                String passwordString= String.valueOf(password.getPassword());

                if (UserService.isExist(account,passwordString)){
                    MainFrame mainFrame=new MainFrame("雷霆战机");
                    mainFrame.setVisible(true);
                    //destroy the login interface by use the outer-this
                    LoginFrame.this.dispose();
                }else {
                    TripsDialog dialog=new TripsDialog(LoginFrame.this,"提示","账号或密码错误!");
                    //set the relative location
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                }
            }
        });

        registration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterFrame registerFrame=new RegisterFrame("注册");
                LoginFrame.this.dispose();
            }
        });
    }

}
