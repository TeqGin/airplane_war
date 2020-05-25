package Domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author 许达峰
 * @time 2020.5.17
 * */

public class EnemyPlane extends Plane {
    private long appearTime;
    private ArrayList<Bullet> bullets=new ArrayList<Bullet>();
    public EnemyPlane(String address,String bulletAddress) {
        super(address,bulletAddress);
        setX(new Random().nextInt(Data.width-getWidth()));
        setY(0);
        setStep(2);
        appearTime=System.currentTimeMillis();
    }

    public void draw(Graphics gBuffer,Canvas c){
        gBuffer.drawImage(getPlanePicture(),getX(),getY(),c);
    }
    public void drawBullet(Graphics gBuffer,Canvas c){
        for (int i = 0; i < bullets.size(); i++) {
            gBuffer.drawImage(bullets.get(i).getBulletImage(),bullets.get(i).getX(),bullets.get(i).getY(),c);
        }
    }
    public void bulletMove(){
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).getY()<=Data.height&&bullets.get(i).isExist()){
                bullets.get(i).move(-5);
            }else {
                bullets.remove(bullets.get(i));
            }
        }
        long now_time = System.currentTimeMillis();
        //if there is no any  bullet in the arrayList or every few milliseconds(after last bullet shoot)
        long shootInterval=1000;
        if (bullets.size() == 0
                || now_time - bullets.get(bullets.size() - 1).getShootTime() >= shootInterval) {
            bullets.add(new Bullet(getBulletAddress(), getX() + 20, getY()));
        }
    }

    public void move(){
        setY(getY()+getStep());
    }

    public long getAppearTime() {
        return appearTime;
    }

    public void setAppearTime(long appearTime) {
        this.appearTime = appearTime;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }
}
