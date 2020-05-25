package Canvas;

import Domain.*;
import Utils.MusicUtil;
import Dialog.TripsDialog;
import Frame.SettlementFrame;

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
 *  This class is to draw the dynamic game body include score ,enemy ,user plane ,bullets and the rolling background
 *  this class also bind the press on space to the keyboard event which to stop and start the game
 * */

public class MapCanvas extends Canvas implements Runnable {

    //some address of pictures
    private String MapAddress;
    private String userPlaneAddress;
    private String userPlaneBulletsAddress;
    private String boosPlaneAddress;
    private String boosPlaneBulletAddress;

    private boolean running=true;

    private MyMap map1,map2;

    private UserPlane userPlane;
    private BoosPlane boosPlane;

    private JFrame jFrame;
    private ArrayList<EnemyPlane> enemyPlanes=new ArrayList<EnemyPlane>();


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
        enemyPlanes.add(new EnemyPlane("static/image/plane/enemy_0.png",
                                  "static/image/bullet/enemy_bullet_1.png"));
        //initialize numbers
        for (int i = 0; i < 10 ; i++) {
            NumberIcon numberIcon=new NumberIcon("static/image/number/number_"+i+".png");
            if (i==1){
                numberIcon.setHeight(numberIcons.get(0).getHeight());
                numberIcon.setWidth(numberIcons.get(0).getWidth());
            }
            numberIcons.add(numberIcon);
        }
        //reset the score
        Data.score=0;
        //add keyboard event to start and stop the game
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

    //use this function to reset some parameters
    public void reset(){

        userPlane=new UserPlane(225,500,userPlaneAddress,Data.userPlaneBulletAddress);
        userPlane.adapt(this);
        //boos plane
        boosPlane=new BoosPlane(0,0,boosPlaneAddress,boosPlaneBulletAddress);
        Data.enterTime=System.currentTimeMillis();
    }

    //to reset element moving speed
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

        //if current stage will be appear boos,after user enter the game a few seconds,stop generate new enemy and generate boos
        if (    Data.hasBoos
                &&System.currentTimeMillis()-Data.enterTime>=Data.duration
                &&boosPlane.isAlive()) {
            Data.enemyNumber=1;
            //draw boos
            gBuffer.drawImage(boosPlane.getPlanePicture(),boosPlane.getX(),boosPlane.getY(),this);
            //draw boos's bullets
            for (int i = 0; i <boosPlane.bullets.size() ; i++) {
                for (int j = 0; j < boosPlane.bullets.get(i).size(); j++) {
                    Bullet bullet=boosPlane.bullets.get(i).get(j);
                    gBuffer.drawImage(bullet.getBulletImage(),bullet.getX(),bullet.getY(),this);
                }
            }
        }

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

    //move the screen and  use the `synchronized` to carry out codes  at the same time
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

        boosPlane.bulletMove();
        boosPlane.planeMove();

        repaint();
    }

    //move the enemy plane and generate new plane
    private void enemyMove(){
        for (int i = 0; i < enemyPlanes.size(); i++) {
            enemyPlanes.get(i).move();
            //if enemy plane is death and enemy is out of the screen,remove the plane
            if (enemyPlanes.get(i).isAlive()==false
                    ||enemyPlanes.get(i).getY()>Data.height){
                enemyPlanes.remove(enemyPlanes.get(i));
            }
        }
        //generate new plane for enemy every a few seconds later
        long now_time = System.currentTimeMillis();
        //if there is no any  bullet in the arrayList or every few milliseconds(after last bullet shoot)
        long appearInterval=400;
        if (enemyPlanes.size() == 0
                || now_time - enemyPlanes.get(enemyPlanes.size() - 1).getAppearTime() >= appearInterval) {
            for (int i = 0; i <new Random().nextInt(Data.enemyNumber) ; i++) {
                int r=new Random().nextInt(Data.enemyBulletType);
                //generate new enemy by random type
                enemyPlanes.add(new EnemyPlane("static/image/plane/enemy_"+new Random().nextInt(Data.enemyType)+".png","static/image/bullet/enemy_bullet_"+r+".png"));
            }
        }
    }

    //the impact check algorithm by using rectangle
    private void collision(){
        //use bullet and enemy impact checking
        ArrayList<Bullet> userBullets=userPlane.getUserBullets();
        for (int i = 0; i < userBullets.size(); i++) {
            for (int j = 0; j < enemyPlanes.size(); j++) {
                //System class Rectangle has the impact checking function
                if (new Rectangle(userBullets.get(i).getX(),userBullets.get(i).getY(),userBullets.get(i).getWidth(),userBullets.get(i).getHeight()).intersects(
                        new Rectangle(enemyPlanes.get(j).getX(),enemyPlanes.get(j).getY(),enemyPlanes.get(j).getWidth(),enemyPlanes.get(j).getHeight())
                    )){
                    //play music
                    playMusic("static/music/bloom.wav");
                    //set the status
                    userBullets.get(i).setExist(false);
                    enemyPlanes.get(j).setAlive(false);
                    //get more score
                    Data.score+=100;
                    if (Data.score==5000){
                        //speed up and generate more enemy
                        Data.speed/=2;
                        Data.enemyNumber+=1;
                    }
                    //after you get enough score stop generate new plane,actually the enemy number is zero
                    if(Data.score==Data.targetScore){
                        Data.enemyNumber=1;
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
                gameFailure();
            }
        }

        //enemy bullets and user plane impact checking
        for (int i = 0; i < enemyPlanes.size(); i++) {
            ArrayList<Bullet> enemyBullets=enemyPlanes.get(i).getBullets();
            for (int j = 0; j < enemyBullets.size(); j++) {
                if (new Rectangle(userPlane.getX(),userPlane.getY(),userPlane.getWidth(),userPlane.getHeight()).intersects(
                        new Rectangle(enemyBullets.get(j).getX(),enemyBullets.get(j).getY(),enemyBullets.get(j).getWidth(),enemyBullets.get(j).getHeight())
                )){
                    gameFailure();
                }
            }
        }

        //if the current stage has a boos ,and after the boos appear and the boos is alive,the do the impact check
        if (Data.hasBoos
                &&System.currentTimeMillis()-Data.enterTime>=Data.duration
                &&boosPlane.isAlive()){
            if (new Rectangle(boosPlane.getX(),boosPlane.getY(),boosPlane.getWidth(),boosPlane.getHeight()).intersects(
                    new Rectangle(userPlane.getX(),userPlane.getY(),userPlane.getWidth(),userPlane.getHeight()
            ))){
                gameFailure();
            }
            //do the impact check between boos's bullets and user's plane
            for (int i = 0; i < boosPlane.bullets.size(); i++) {
                for (int j = 0; j <boosPlane.bullets.get(i).size() ; j++) {
                    Bullet bullet=boosPlane.bullets.get(i).get(j);
                    if (new Rectangle(userPlane.getX(),userPlane.getY(),userPlane.getWidth(),userPlane.getHeight()).intersects(
                            new Rectangle(bullet.getX(),bullet.getY(),bullet.getWidth(),bullet.getHeight())
                    )){
                        gameFailure();
                    }
                }
            }
            //do the impact check between user's bullets and boos plane ,and only user's bullets hit the boos enough time,the boos will dead
            userBullets=userPlane.getUserBullets();
            for (int i = 0; i <userBullets.size() ; i++) {
                if (new Rectangle(userBullets.get(i).getX(),userBullets.get(i).getY(),userBullets.get(i).getWidth(),userBullets.get(i).getHeight()).intersects(
                        new Rectangle(boosPlane.getX(),boosPlane.getY(),boosPlane.getWidth(),boosPlane.getHeight())
                )
                ){
                    playMusic("static/music/bloom.wav");
                    userBullets.get(i).setExist(false);
                    //every time user's bullet hit the boos ,the boos's live become less
                    boosPlane.beat-=1;
                    if (boosPlane.beat<=0){
                        boosPlane.setAlive(false);
                        Data.hasBoos=false;
                        Data.score+=3000;
                    }
                }
            }
        }
    }

    private void gameFailure(){
        playMusic("static/music/bloom.wav");
        userPlane.setAlive(false);
        //stop the thread
        stop();
        playMusic("static/music/game_over.wav");
        JOptionPane.showMessageDialog(null,"游戏结束!","提示",JOptionPane.INFORMATION_MESSAGE);
        //destroy the frame
        jFrame.dispose();
        //into settlement frame
        SettlementFrame settlementFrame =new SettlementFrame("结算面板","static/image/map/settlement_failure.png");
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
            //if the current stage has no boos ,the after you get enough score and the screen has no any enemy,you pass the stage
            //if the current stage has a boos ,as long as you kill boss ,you pass the stage
            if (enemyPlanes.size()==0&&(Data.score>=Data.targetScore
                    ||(Data.hasBoos==false&&boosPlane.isAlive()==false))){
                playMusic("static/music/victory.wav");
                JOptionPane.showMessageDialog(null,"恭喜通关!","提示",JOptionPane.INFORMATION_MESSAGE);
                stop();
                jFrame.dispose();
                SettlementFrame settlementFrame =new SettlementFrame("结算面板","static/image/map/settlement_success.png");
            }
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

    public void setTargetScore(int targetScore){
        Data.targetScore=targetScore;
    }

    public void setBoosPlaneAddress(String boosPlaneAddress) {
        this.boosPlaneAddress = boosPlaneAddress;
    }

    public void setBoosPlaneBulletAddress(String boosPlaneBulletAddress) {
        this.boosPlaneBulletAddress = boosPlaneBulletAddress;
    }
}
