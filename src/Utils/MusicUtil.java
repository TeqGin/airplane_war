package Utils;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

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
    public void playOnce(){
        info=new DataLine.Info(Clip.class,audioFormat);
        try {
            clip=(Clip)AudioSystem.getLine(info);
            clip.open(audioInputStream);
            clip.start();
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
    public void stop(){
        if (clip!=null) {
            clip.stop();
        }
    }
    public void changeMusic(String address){
        clip.stop();
        loadMusic(address);
        play();
    }

}
