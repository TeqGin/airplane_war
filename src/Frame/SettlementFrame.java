package Frame;

import Canvas.SettlementCanvas;
import Domain.Data;

import javax.swing.*;
import java.awt.*;

public class SettlementFrame extends JFrame {
    private String backgroundAddress;
    public SettlementFrame(String title,String backgroundAddress) throws HeadlessException {
        super(title);
        this.backgroundAddress=backgroundAddress;
        SettlementCanvas settlementCanvas=new SettlementCanvas(this,backgroundAddress);

        this.getContentPane().add(settlementCanvas);


        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(Data.x,Data.y,Data.width,Data.height);
    }
}
