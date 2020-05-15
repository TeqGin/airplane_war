package Utils;

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

    public void loadMusic(String address){
        try {
            audioInputStream=AudioSystem.getAudioInputStream(new URL(address));
            audioFormat=audioInputStream.getFormat();
            info=new DataLine.Info(SourceDataLine.class,audioFormat);
            sourceDataLine=(SourceDataLine)AudioSystem.getLine(info);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
