package Domain;

/**
 * @time 2020.5.21
 * @author  许达峰
 * this class is useless,due to i forget the system library already exist a Rectange class ,and it has the impact checking function,
 * but i do not want to reconstruct it,so i keep it,and remind myself to lean more about the basic knowledge of java
 * */

public class Rectangle {
    //record the position of the plane
    private int x;
    private int y;

    private int height;
    private int width;

    public Rectangle() {
    }

    public Rectangle(int x, int y) {
        this.x = x;
        this.y = y;
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
}
