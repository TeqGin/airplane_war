package Frame;

import Domain.Data;

import javax.swing.*;
import java.awt.*;

public class CheckPointFrame extends JFrame {
    public CheckPointFrame(String title) throws HeadlessException {
        super(title);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(Data.x,Data.y,Data.width,Data.height);
        //set unable to change the size
        this.setResizable(false);

    }
}
