package Domain;

import java.awt.*;
/**
 * @author 许达峰
 * @// TODO: 2020/5/19
 *    this is a map class
 *    when i use the class to draw the background ,it will make the background dislocation,
 *    the reason maybe when i use the thread operation,two map's y coordinate cannot increase at the same time,
 *    so use the `synchronized` to carry out codes  at the same time
 * */

public class MyMap {
    private String address;
    private Image background;
    private int x;
    private int y;

    public MyMap(String address, int x, int y) {
        this.address = address;
        this.background=Toolkit.getDefaultToolkit().getImage(address);
        this.x = x;
        this.y = y;
    }
    public void  mapMove(int step){
        y+=step;
        if (y>=Data.height){
            y=-Data.height;
        }
    }

    public Image getBackground() {
        return background;
    }

    public void setBackground(Image background) {
        this.background = background;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        if (this.y>=Data.height){
            this.y=-Data.height;
        }
    }
}
