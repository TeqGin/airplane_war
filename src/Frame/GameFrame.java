package Frame;

import Domain.Data;
import Utils.MusicUtil;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public GameFrame(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(Data.x,Data.y,Data.width,Data.height);

        //set unable to change the size
        this.setResizable(false);
        this.setVisible(true);

        //change music
        Data.backgroundMusic.changeMusic("static/music/bgm_music_2.wav");
    }
}
