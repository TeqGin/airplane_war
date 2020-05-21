package Domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author 许达峰
 * @time 2020.5.17
 * */

public class Bullet  extends Rectangle{
    private Image bulletImage;
    private String address;
    private int step=7;
    private long shootTime;
    private boolean isExist=true;

    public Bullet(String address, int x, int y) {
        super(x,y);
        this.address = address;
        bulletImage=Toolkit.getDefaultToolkit().getImage(address);

        setHeight(new ImageIcon(address).getIconHeight());
        setWidth(new ImageIcon(address).getIconWidth());

        shootTime=System.currentTimeMillis();
    }
    public void explosion(ArrayList<Rectangle> rectangles){

    }


    public void move(int step){
        setY(getY()-step);
    }

    public long getShootTime() {
        return shootTime;
    }

    public void setShootTime(long shootTime) {
        this.shootTime = shootTime;
    }

    public Image getBulletImage() {
        return bulletImage;
    }

    public void setBulletImage(Image bulletImage) {
        this.bulletImage = bulletImage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }
}
