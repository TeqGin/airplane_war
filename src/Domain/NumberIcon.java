package Domain;

import javax.swing.*;
import java.awt.*;

/**
 * @author 许达峰
 * this clas and the BasicIcon is redundancy
 * */

public class NumberIcon {
    private String address;
    private Image numberImage;
    private int height;
    private int width;
    private int x,y;

    public NumberIcon(String address) {
        this.address = address;
        numberImage=Toolkit.getDefaultToolkit().getImage(address);
        height=new ImageIcon(address).getIconHeight();
        width=new ImageIcon(address).getIconWidth();

        this.x = Data.width-width-10;
        this.y = 5;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Image getNumberImage() {
        return numberImage;
    }

    public void setNumberImage(Image numberImage) {
        this.numberImage = numberImage;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
