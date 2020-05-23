package Domain;

import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;

public class BasicIcon extends Rectangle {
    private String address;
    private Image image;

    public BasicIcon(String address,int x,int y) {
        this.address = address;
        width=new ImageIcon(address).getIconWidth();
        height=new ImageIcon(address).getIconHeight();
        this.image=Toolkit.getDefaultToolkit().getImage(address);
        this.x=x;
        this.y=y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
