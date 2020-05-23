package Domain;

import java.util.ArrayList;

/**
 * @author 许达峰
 * @time 2020.5.17
 * */

public class BoosPlane extends Plane {
    public int beat=30;
    private boolean right=true;

    public ArrayList<ArrayList<Bullet>> bullets=new ArrayList<ArrayList<Bullet>>();

    public BoosPlane(int x, int y, String address, String bulletAddress) {
        super(x, y, address, bulletAddress);
    }

    public void bulletMove(){
        for (int i=0;i<bullets.size();i++){
            if (bullets.get(i).get(0).getY()>=Data.height||isAlive()){
                for (int j = 0; j < bullets.get(i).size(); j++) {
                    bullets.get(i).get(j).move(-5);
                }
            }else {
                bullets.remove(bullets.get(i));
            }
        }
        long now_time = System.currentTimeMillis();
        //if there is no any  bullet in the arrayList or every few milliseconds(after last bullet shoot)
        long shootInterval=1000;
        if (System.currentTimeMillis()-Data.enterTime>=Data.duration && (bullets.size() == 0 || now_time - bullets.get(bullets.size() - 1).get(0).getShootTime() >= shootInterval)) {
            ArrayList<Bullet> bulletArrayList=new ArrayList<>(5);
            for (int j = 0; j < 3; j++) {
                bulletArrayList.add(new Bullet(getBulletAddress(),getX()+j*150,getY()));
            }
            bullets.add(bulletArrayList);
        }
    }

    public void planeMove(){
        if (right){
            setX(getX()+getStep());
        }else {
            setX(getX()-getStep());
        }
        if (getX()>=Data.width-getWidth()||getX()<=0){
            right=!right;
        }
    }
}
