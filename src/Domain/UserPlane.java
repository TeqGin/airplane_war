package Domain;


import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * @author 许达峰
 * @time 2020.5.17
 * */

public class UserPlane extends Plane {
    private boolean isUpPress,isDownPress,isLeftPress,isRightPress;
    private ArrayList<Bullet> userBullets=new ArrayList<Bullet>();

    private long shootInterval=200;

    public UserPlane(int x, int y, String address,String bulletAddress) {
        super(x, y, address,bulletAddress);
        //bullets
        userBullets.add(new Bullet(this.getBulletAddress(),getX()+20,getY()));
    }

    public void drawBullet(Graphics gBuffer,Canvas c){
        for (int i = 0; i <userBullets.size(); i++) {
            gBuffer.drawImage(userBullets.get(i).getBulletImage(),userBullets.get(i).getX(),userBullets.get(i).getY(),c);
        }
    }

    /**
     * @time 2020/5/21
     *      at first i want to use the fix number of bullets to shoot ,and i meet a problem ,i reset the position of every bullet if
     *  the fly out the screen ,but if the plane fly to the top of the screen,every bullet will reset in the same position,the
     *  result is i can only see one bullet,means the bullets coincided.
     *      the reason why i use the fix number of bullets is i worryabout memory leak,but i realized that java has the
     *  garbage collection mechanism.
     *      so i add new bullet into the arrayList of the bullets every a few time,and remove them if them fly out of the screen
     * */
    public void bulletMove() {
        for (int i = 0; i < userBullets.size(); i++) {
            if (userBullets.get(i).getY() >= 0 && userBullets.get(i).isExist()) {
                userBullets.get(i).move(userBullets.get(i).getStep());
            } else {
                //remove the bullet if it fly out of the screen
                userBullets.remove(userBullets.get(i));
            }
        }
        long now_time = System.currentTimeMillis();
        //if there is no any  bullet in the arrayList or every few milliseconds(after last bullet shoot)
        if (userBullets.size() == 0 || now_time - userBullets.get(userBullets.size() - 1).getShootTime() >= this.shootInterval) {
            userBullets.add(new Bullet(getBulletAddress(), getX() + 20, getY()));
        }
    }


    //move the plane
    public void planeMove(){
        if(isLeftPress)
            if(getX() > 0)
                changeX(-getStep());

        if(isRightPress)
            if(getX() < Data.width-getWidth())
                changeX(+getStep());

        if(isUpPress)
            if(getY() > 0)
                changeY(-getStep());

        if(isDownPress)
            if(getY() < Data.height-getHeight()-20)
                changeY(+getStep());
    }

    //monitor the keyPress and keyRelease
    public void adapt(Canvas c){
        c.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()){
                    case KeyEvent.VK_UP:
                        isUpPress=true;
                        break;
                    case KeyEvent.VK_DOWN:
                        isDownPress=true;
                        break;
                    case KeyEvent.VK_LEFT:
                        isLeftPress=true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        isRightPress=true;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                switch (e.getKeyCode()){
                    case KeyEvent.VK_UP:
                        isUpPress=false;
                        break;
                    case KeyEvent.VK_DOWN:
                        isDownPress=false;
                        break;
                    case KeyEvent.VK_LEFT:
                        isLeftPress=false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        isRightPress=false;
                        break;
                }
            }
        });
    }


    public void changeX(int step){
        this.setX(getX()+step);
    }
    public void changeY(int step){
        this.setY(getY()+step);
    }

    public ArrayList<Bullet> getUserBullets() {
        return userBullets;
    }

    public void setUserBullets(ArrayList<Bullet> userBullets) {
        this.userBullets = userBullets;
    }
}
