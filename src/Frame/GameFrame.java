package Frame;

import Domain.Data;
import Canvas.MapCanvas;
import Dialog.TripsDialog;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;

/**
 * @author 许达峰
 * @time 2020.5.17
 * */

public class GameFrame extends JFrame {
    private MapCanvas mapCanvas;
    private String mapAddress;
    private String userPlaneAddress;
    private String userPlaneBulletAddress;
    private String boosPlaneAddress;
    private String boosPlaneBulletAddress;

    public GameFrame(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(Data.x,Data.y,Data.width,Data.height);
        //set unable to change the size
        this.setResizable(false);
        //change music
        Data.backgroundMusic.changeMusic("static/music/bgm_music_2.wav");
        this.setVisible(true);
    }

    //to show the Canvas
    private void drawCanvas(){
        //add an instance of mapCanvas
        mapCanvas =new MapCanvas(mapAddress,this);
        mapCanvas.setUserPlaneAddress(userPlaneAddress);
        mapCanvas.setUserPlaneBulletsAddress(userPlaneBulletAddress);
        mapCanvas.setBoosPlaneAddress(boosPlaneAddress);
        mapCanvas.setBoosPlaneBulletAddress(boosPlaneBulletAddress);
        mapCanvas.reset();
        add(mapCanvas);
        new Thread(mapCanvas).start();
    }
    public void initCanvas(){
        //draw  all images
        drawCanvas();
    }
    public void setMapAddress(String mapAddress) {
        this.mapAddress = mapAddress;
    }

    public void setUserPlaneAddress(String userPlaneAddress) {
        this.userPlaneAddress = userPlaneAddress;
    }

    public void setUserPlaneBulletAddress(String userPlaneBulletAddress) {
        this.userPlaneBulletAddress = userPlaneBulletAddress;
    }
    public void setBoosPlaneAddress(String boosPlaneAddress) {
        this.boosPlaneAddress = boosPlaneAddress;
    }

    public void setBoosPlaneBulletAddress(String boosPlaneBulletAddress) {
        this.boosPlaneBulletAddress = boosPlaneBulletAddress;
    }
}
