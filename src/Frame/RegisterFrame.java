package Frame;

import Domain.Data;
import Service.UserService;
import Dialog.TripsDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RegisterFrame extends JFrame {
    private  JTextField account=new JTextField("请输入账号",15);
    private  JPasswordField passwordField=new JPasswordField("请输入密码",15);
    private  JPasswordField passwordFieldToConfirm=new JPasswordField("请再次输入密码",15);
    private  JButton submit=new JButton("提交");
    private  JButton back=new JButton("返回");


    public RegisterFrame(String title) throws HeadlessException {
        super(title);
        this.setTitle(title);
        //load the background picture
        ImageIcon bgIco=new ImageIcon("static/image/map/bg_sky_with_logo.jpg");
        JLabel bgLabel=new JLabel(bgIco);

        bgLabel.setBounds(0,0, Data.width,Data.height);
        this.getLayeredPane().add(bgLabel,new Integer(Integer.MIN_VALUE));

        JPanel container=(JPanel) this.getContentPane();
        container.setOpaque(false);

        this.setBounds(Data.x,Data.y,Data.width,Data.height);
        this.setVisible(true);
        this.setLayout(null);
        //set unable to change the size
        this.setResizable(false);

        //add a account filed
        JPanel accountPanel=new JPanel();
        accountPanel.setLayout(new FlowLayout());
        accountPanel.add(new JLabel("账号:"));
        accountPanel.add(account);
        accountPanel.setBounds(Data.width/4,927/3,250,25);
        accountPanel.setOpaque(false);
        account.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                String accountString=account.getText();
                if (accountString.equals("请输入账号")){
                    account.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                String accountString=account.getText();
                if ("".equals(accountString)){
                    account.setText("请输入账号");
                }
            }
        });

        //add a password filed
        JPanel passwordPanel=new JPanel();
        passwordPanel.add(new JLabel("密码:"));
        passwordPanel.add(passwordField);
        passwordPanel.setBounds(Data.width/4,927/3+50,250,25);
        passwordPanel.setOpaque(false);
        passwordField.setEchoChar('\0');
        //bind to focus event
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                String passwordString=String.valueOf(passwordField.getPassword());
                if (passwordString.equals("请输入密码")){
                    passwordField.setEchoChar('*');
                    passwordField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                String passwordString=String.valueOf(passwordField.getPassword());
                if ("".equals(passwordString)){
                    passwordField.setEchoChar('\0');
                    passwordField.setText("请输入密码");
                }
            }
        });

        //add a passwordToConfirm filed
        JPanel passwordToConfirmPanel=new JPanel();
        passwordToConfirmPanel.add(new JLabel("确认密码:"));
        passwordToConfirmPanel.add(passwordFieldToConfirm);
        passwordToConfirmPanel.setBounds(Data.width/4-10,927/3+100,250,25);
        passwordToConfirmPanel.setOpaque(false);
        //able to see
        passwordFieldToConfirm.setEchoChar('\0');
        //bind to focus event
        passwordFieldToConfirm.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                String passwordString=String.valueOf(passwordFieldToConfirm.getPassword());
                if (passwordString.equals("请再次输入密码")){
                    passwordFieldToConfirm.setEchoChar('*');
                    passwordFieldToConfirm.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                String passwordString=String.valueOf(passwordFieldToConfirm.getPassword());
                if ("".equals(passwordString)){
                    passwordFieldToConfirm.setEchoChar('\0');
                    passwordFieldToConfirm.setText("请再次输入密码");
                }
            }
        });

        //add a button Panel
        JPanel subPanel =new JPanel();
        subPanel.add(submit);
        subPanel.add(back);
        subPanel.setBounds(Data.width/4+20,927/3+150,250,30);
        subPanel.setOpaque(false);

        //bind the submit button to click event
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountString=account.getText();
                String passwordString=String.valueOf(passwordField.getPassword());
                String passwordToConfirmString=String.valueOf(passwordFieldToConfirm.getPassword());

                if (UserService.isLegal(accountString,passwordString,passwordToConfirmString)){
                    UserService.addUser(accountString,passwordString);
                    TripsDialog tripsDialog=new TripsDialog(RegisterFrame.this,"提示","注册成功！");
                    tripsDialog.setVisible(true);

                }else {
                       TripsDialog tripsDialog=new TripsDialog(RegisterFrame.this,"提示","账号或密码出错！，请注意账号和密码不能为空");
                       tripsDialog.setVisible(true);
                }
            }
        });

        //bind the back button to the click event
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame loginFrame=new LoginFrame("登陆界面");
                RegisterFrame.this.dispose();
            }
        });



        container.add(accountPanel);
        container.add(passwordPanel);
        container.add(passwordToConfirmPanel);
        container.add(subPanel);
    }
}
