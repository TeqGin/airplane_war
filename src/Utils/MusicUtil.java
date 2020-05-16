package Utils;

import com.sun.scenario.effect.impl.prism.PrImage;

import javax.sound.midi.Sequence;
import javax.sound.sampled.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MusicUtil {
    private AudioFormat audioFormat;
    private SourceDataLine sourceDataLine;
    private DataLine.Info info;
    private AudioInputStream audioInputStream;
    private File file;
    private Clip clip;


    //only can load .wav file
    public void loadMusic(String address){
        file=new File(address);
        try {
            audioInputStream=AudioSystem.getAudioInputStream(file);
            audioFormat=audioInputStream.getFormat();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void play(){
        info=new DataLine.Info(Clip.class,audioFormat);
        try {
            clip=(Clip)AudioSystem.getLine(info);
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Integer.MAX_VALUE);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
    public void stop(){

        if (clip!=null) {
            clip.stop();
        }
    }
    public boolean isActive(){
        return clip.isActive();
    }
    public void changeMusic(String address){
        clip.stop();
        loadMusic(address);
        play();
    }

}
