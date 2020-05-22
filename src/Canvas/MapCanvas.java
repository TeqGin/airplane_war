package Canvas;

import Domain.*;
import Utils.MusicUtil;
import Dialog.TripsDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private JFrame jFrame;
    private String userPlaneAddress;
    private String userPlaneBulletsAddress;

    //instance double buffer 实现双缓冲
    private Image iBuffer;
    private Graphics gBuffer;
    private ArrayList<NumberIcon> numberIcons=new ArrayList<NumberIcon>();

    public MapCanvas(String MapAddress,JFrame jFrame) {
        this.MapAddress = MapAddress;
        this.jFrame=jFrame;
        //background
        map1=new MyMap(this.MapAddress,0,0);
        map2=new MyMap(this.MapAddress,0,-Data.height);
        //user plane
        enemyPlanes.add(new EnemyPlane("static/image/plane/plane_level1_enemy_1.png","static/image/bullet/bullet_enemy_1_blue.png"));

        //initialize numbers
        for (int i = 0; i < 10 ; i++) {
            NumberIcon numberIcon=new NumberIcon("static/image/number/number_"+i+".png");
            if (i==1){
                numberIcon.setHeight(numberIcons.get(0).getHeight());
                numberIcon.setWidth(numberIcons.get(0).getWidth());
            }
            numberIcons.add(numberIcon);
        }

        Data.score=0;
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode()==KeyEvent.VK_SPACE){
                    if (Data.gameRunning==true){
                        stop();
                        Data.gameRunning=false;
                    }else {
                        go();
                        Data.gameRunning=true;
                    }
                }
            }
        });

        this.setBounds(0,0,Data.width,Data.height);
    }
    public void init(){
        initUserPlane();
    }
    public void initUserPlane(){
        userPlane=new UserPlane(225,500,userPlaneAddress,userPlaneBulletsAddress);
        userPlane.adapt(this);
    }
    public void setSpeed(int speed){
        Data.speed=speed;
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
        //draw enemy bullets
        for (int i = 0; i < enemyPlanes.size(); i++) {
            enemyPlanes.get(i).drawBullet(gBuffer,this);
        }

        //drawScore
        drawScore(gBuffer);

        g.drawImage(iBuffer,0,0,null);
    }

    //draw score
    private void drawScore(Graphics gBuffer){
        int score=Data.score;
        int first_x=Data.width;
        int count=0;
        if (score==0){
            gBuffer.drawImage(numberIcons.get(0).getNumberImage(),numberIcons.get(0).getX()-(numberIcons.get(0).getWidth()+5)*count,numberIcons.get(0).getY(),this);
        }else {
            while (score > 0) {
                int right=score%10;
                NumberIcon numberIcon=numberIcons.get(right);
                gBuffer.drawImage(numberIcon.getNumberImage(),numberIcon.getX()-(numberIcon.getWidth()+5)*count,numberIcon.getY(),this);
                count++;
                score/=10;
            }
        }
    }

    //move the screen
    private synchronized void move(){
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
        //use bullet and enemy impact checking
        ArrayList<Bullet> userBullets=userPlane.getUserBullets();
        for (int i = 0; i < userBullets.size(); i++) {
            for (int j = 0; j < enemyPlanes.size(); j++) {
                //System class Rectangle has the impact checking function
                if (new Rectangle(userBullets.get(i).getX(),userBullets.get(i).getY(),userBullets.get(i).getWidth(),userBullets.get(i).getHeight()).intersects(
                        new Rectangle(enemyPlanes.get(j).getX(),enemyPlanes.get(j).getY(),enemyPlanes.get(j).getWidth(),enemyPlanes.get(j).getHeight())
                    )
                ){
                    playMusic("static/music/bloom.wav");
                    userBullets.get(i).setExist(false);
                    enemyPlanes.get(j).setAlive(false);
                    Data.score+=100;
                    if (Data.score==5000){
                        Data.speed/=2;
                    }
                    break;
                }
            }
        }
        //reset user bullets
        userPlane.setUserBullets(userBullets);

        //enemy plane and user plane impact checking
        for (int i=0;i<enemyPlanes.size();i++){
            if (new Rectangle(enemyPlanes.get(i).getX(),enemyPlanes.get(i).getY(),enemyPlanes.get(i).getWidth(),enemyPlanes.get(i).getHeight()).intersects(
                    new Rectangle(userPlane.getX(),userPlane.getY(),userPlane.getWidth(),userPlane.getHeight())
            )
            ){
                playMusic("static/music/bloom.wav");
                userPlane.setAlive(false);
                running=false;
                TripsDialog tripsDialog=new TripsDialog(jFrame,"提示","游戏结束！");
                tripsDialog.setVisible(true);
            }
        }

        //enemy bullets and user plane impact checking
        for (int i = 0; i < enemyPlanes.size(); i++) {
            ArrayList<Bullet> enemyBullets=enemyPlanes.get(i).getBullets();

            for (int j = 0; j < enemyBullets.size(); j++) {
                if (new Rectangle(userPlane.getX(),userPlane.getY(),userPlane.getWidth(),userPlane.getHeight()).intersects(
                        new Rectangle(enemyBullets.get(j).getX(),enemyBullets.get(j).getY(),enemyBullets.get(j).getWidth(),enemyBullets.get(j).getHeight())
                )){
                    playMusic("static/music/bloom.wav");
                    userPlane.setAlive(false);
                    running=false;
                    TripsDialog tripsDialog=new TripsDialog(jFrame,"提示","游戏结束！");
                    tripsDialog.setVisible(true);
                }
            }
        }
    }

    private void playMusic(String address){
        Data.backgroundMusic2=new MusicUtil();
        Data.backgroundMusic2.loadMusic(address);
        Data.backgroundMusic2.playOnce();
    }

    @Override
    public void run() {
        while(running){
            move();
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

    public String getUserPlaneAddress() {
        return userPlaneAddress;
    }

    public void setUserPlaneAddress(String userPlaneAddress) {
        this.userPlaneAddress = userPlaneAddress;
    }

    public String getUserPlaneBulletsAddress() {
        return userPlaneBulletsAddress;
    }

    public void setUserPlaneBulletsAddress(String userPlaneBulletsAddress) {
        this.userPlaneBulletsAddress = userPlaneBulletsAddress;
    }
}
