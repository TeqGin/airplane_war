package Canvas;

import Domain.*;
import Utils.MusicUtil;
import Dialog.TripsDialog;

import java.awt.*;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author 许达峰
 * @time 2020.5.17
 * */

public class MapCanvas extends Canvas implements Runnable {

    private String MapAddress;
    private boolean running=true;
    private MyMap map1,map2;
    private UserPlane userPlane;
    private ArrayList<EnemyPlane> enemyPlanes=new ArrayList<EnemyPlane>();

    //instance double buffer 实现双缓冲
    private Image iBuffer;
    private Graphics gBuffer;

    public MapCanvas(String MapAddress) {
        requestFocusInWindow();
        this.MapAddress = MapAddress;
        //background
        map1=new MyMap(this.MapAddress,0,0);
        map2=new MyMap(this.MapAddress,0,-Data.height);
        //user plane
        userPlane=new UserPlane(225,500,"static/image/plane/user_plane_level1.png","static/image/bullet/bullet_2_blue.png");
        userPlane.adapt(this);
        enemyPlanes.add(new EnemyPlane("static/image/plane/plane_level1_enemy_1.png","static/image/bullet/bullet_enemy_1_blue.png"));

        Data.score=0;
        this.setBounds(0,0,Data.width,Data.height);
    }

    //this is a callback function
    public  void paint(Graphics g){
        if (iBuffer==null){
            iBuffer= createImage(Data.width,Data.height);
            gBuffer=iBuffer.getGraphics();
        }

        //reset the pan's parameters
        gBuffer.fillRect(0,0,Data.width,Data.height);

        //draw background
        gBuffer.drawImage(map1.getBackground(),map1.getX(),map1.getY(),Data.width,Data.height,this);
        gBuffer.drawImage(map2.getBackground(),map2.getX(),map2.getY(),Data.width,Data.height,this);
        //draw user plane
        gBuffer.drawImage(userPlane.getPlanePicture(),userPlane.getX(),userPlane.getY(),this);

        //draw user bullets
        userPlane.drawBullet(gBuffer,this);
        //draw enemy plane
        for (int i=0;i<enemyPlanes.size();i++){
            enemyPlanes.get(i).draw(gBuffer,this);
        }
        gBuffer.drawString(String.valueOf(Data.score),400,300);
        //draw enemy bullets
        for (int i = 0; i < enemyPlanes.size(); i++) {
            enemyPlanes.get(i).drawBullet(gBuffer,this);
        }

        g.drawImage(iBuffer,0,0,null);
    }
    //draw the map
    private synchronized void draw(){
        mapMove();
        //judge if happened collision
        collision();
        userPlane.bulletMove();
        userPlane.planeMove();
        for (int i = 0; i < enemyPlanes.size(); i++) {
            enemyPlanes.get(i).bulletMove();
        }
        enemyMove();
        repaint();
    }

    private void enemyMove(){
        for (int i = 0; i < enemyPlanes.size(); i++) {
            enemyPlanes.get(i).move();
            if (enemyPlanes.get(i).isAlive()==false||enemyPlanes.get(i).getY()>Data.height){
                enemyPlanes.remove(enemyPlanes.get(i));
            }
        }
        long now_time = System.currentTimeMillis();
        //if there is no any  bullet in the arrayList or every few milliseconds(after last bullet shoot)
        long appearInterval=400;
        if (enemyPlanes.size() == 0 || now_time - enemyPlanes.get(enemyPlanes.size() - 1).getAppearTime() >= appearInterval) {
            for (int i = 0; i <new Random().nextInt(Data.enemyNumber) ; i++) {
                enemyPlanes.add(new EnemyPlane("static/image/plane/plane_level1_enemy_1.png","static/image/bullet/bullet_enemy_1_blue.png"));
            }
        }
    }

    private void collision(){
        ArrayList<Bullet> userBullets=userPlane.getUserBullets();
        for (int i = 0; i < userBullets.size(); i++) {
            for (int j = 0; j < enemyPlanes.size(); j++) {
                if (new Rectangle(userBullets.get(i).getX(),userBullets.get(i).getY(),userBullets.get(i).getWidth(),userBullets.get(i).getHeight()).intersects(
                        new Rectangle(enemyPlanes.get(j).getX(),enemyPlanes.get(j).getY(),enemyPlanes.get(j).getWidth(),enemyPlanes.get(j).getHeight())
                    )
                ){
                    Data.backgroundMusic2=new MusicUtil();
                    Data.backgroundMusic2.loadMusic("static/music/bloom.wav");
                    Data.backgroundMusic2.playOnce();
                    userBullets.get(i).setExist(false);
                    enemyPlanes.get(j).setAlive(false);
                    Data.score+=100;
                    break;
                }
            }
        }
        userPlane.setUserBullets(userBullets);

        for (int i=0;i<enemyPlanes.size();i++){
            if (new Rectangle(enemyPlanes.get(i).getX(),enemyPlanes.get(i).getY(),enemyPlanes.get(i).getWidth(),enemyPlanes.get(i).getHeight()).intersects(
                    new Rectangle(userPlane.getX(),userPlane.getY(),userPlane.getWidth(),userPlane.getHeight())
            )
            ){
                Data.backgroundMusic2=new MusicUtil();
                Data.backgroundMusic2.loadMusic("static/music/bloom.wav");
                Data.backgroundMusic2.playOnce();
                userPlane.setAlive(false);
                running=false;
            }
        }
        for (int i = 0; i < enemyPlanes.size(); i++) {
            ArrayList<Bullet> enemyBullets=enemyPlanes.get(i).getBullets();
            for (int j = 0; j < enemyBullets.size(); j++) {
                if (new Rectangle(userPlane.getX(),userPlane.getY(),userPlane.getWidth(),userPlane.getHeight()).intersects(
                        new Rectangle(enemyBullets.get(j).getX(),enemyBullets.get(j).getY(),enemyBullets.get(j).getWidth(),enemyBullets.get(j).getHeight())
                )){
                    Data.backgroundMusic2=new MusicUtil();
                    Data.backgroundMusic2.loadMusic("static/music/bloom.wav");
                    Data.backgroundMusic2.playOnce();
                    userPlane.setAlive(false);
                    running=false;
                }
            }
        }
    }

    @Override
    public void run() {
        while(running){
            draw();
            try {
                Thread.sleep(Data.speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //override this function to avoid screen flicker(or to achieve double buffer)
    @Override
    public void update(Graphics g) {
        //super.update(g) should't be used
        //super.update(g);
        paint(g);
    }

    //rolling the map
    private  void mapMove(){
        map1.mapMove(1);
        map2.mapMove(1);
    }

    public void stop(){
        this.running=false;

    }

    public void go(){
        this.running=true;
        new Thread(this).start();
    }
}
