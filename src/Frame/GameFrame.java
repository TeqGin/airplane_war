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

    public GameFrame(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(Data.x,Data.y,Data.width,Data.height);
        //set unable to change the size
        this.setResizable(false);
        //change music
        Data.backgroundMusic.changeMusic("static/music/bgm_music_2.wav");

        //draw  all images
        drawCanvas();


        this.setVisible(true);
    }
    //to show the Canvas
    private void drawCanvas(){
        //add an instance of mapCanvas
        mapCanvas =new MapCanvas("static/image/map/bg_plain.jpg",this);
        mapCanvas.setUserPlaneAddress("static/image/plane/user_plane_level1.png");
        mapCanvas.setUserPlaneBulletsAddress("static/image/bullet/bullet_02.png");
        mapCanvas.init();
        add(mapCanvas);
        new Thread(mapCanvas).start();
    }
}
