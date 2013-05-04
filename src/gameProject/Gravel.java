package gameProject;

import sun.audio.AudioStream;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/4/13
 * Time: 6:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Gravel extends MapBlock{
    AudioStream as;

    public Gravel(Point p, Dimension d) {
        super(p, d);
        try{
            InputStream in = new FileInputStream("dig.wav");
            as = new AudioStream(in);
        }catch (IOException ex){
            System.out.println("FAIL");
        }
        stopBehavior = new StopAndRemoveBehavior(this,20,as);

    }


}
