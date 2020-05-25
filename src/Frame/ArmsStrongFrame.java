package Frame;

import Domain.Data;
import Canvas.ArmsStrongCanvas;

import javax.swing.*;
import java.awt.*;

/**
 * @author 许达峰
 * time:2020.5.24
 * */

public class ArmsStrongFrame extends JFrame  {
    private String backgroundAddress;


    public ArmsStrongFrame(String backgroundAddress) throws HeadlessException {
        //basic parameter
        this.setTitle("选择武器");
        this.setVisible(true);
        this.setBounds(Data.x,Data.y,Data.width,Data.height);
        this.backgroundAddress=backgroundAddress;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        ArmsStrongCanvas armsStrongCanvas =new ArmsStrongCanvas(backgroundAddress,this);
        //remember add the canvas to this
        add(armsStrongCanvas);

        new Thread(armsStrongCanvas).start();
    }


}
