package Domain;

import javax.swing.*;
import java.awt.*;

/**
 * @author 许达峰
 * @time 2020.5.17
 * */

public class Plane extends Rectangle{

    //judge the plane is alive or not
    private boolean isAlive=true;
    //draw the moving plane
    private Image planePicture;
    //for drawing the  plane bloom picture
    private Break bloom;
    private String address;
    private String bulletAddress;

    private int step=5;


    public Plane(int x, int y, String address,String bulletAddress) {
        super(x,y);
        this.address = address;
        this.bulletAddress=bulletAddress;

        planePicture=Toolkit.getDefaultToolkit().getImage(address);
        setHeight(new ImageIcon(address).getIconHeight());
        setWidth(new ImageIcon(address).getIconWidth());
    }

    public Plane(String address, String bulletAddress) {
        super();
        this.address = address;
        this.bulletAddress = bulletAddress;

        planePicture=Toolkit.getDefaultToolkit().getImage(address);
        setHeight(new ImageIcon(address).getIconHeight());
        setWidth(new ImageIcon(address).getIconWidth());
    }


    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Image getPlanePicture() {
        return planePicture;
    }

    public void setPlanePicture(Image planePicture) {
        this.planePicture = planePicture;
    }

    public Break getBloom() {
        return bloom;
    }

    public void setBloom(Break bloom) {
        this.bloom = bloom;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getBulletAddress() {
        return bulletAddress;
    }

    public void setBulletAddress(String bulletAddress) {
        this.bulletAddress = bulletAddress;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
