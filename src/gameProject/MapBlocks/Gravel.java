package gameProject.MapBlocks;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.AudioStream;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/4/13
 * Time: 6:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Gravel extends MapBlock{
    private AudioStream audioStream;
    private boolean removable;
    public Gravel(Point2D.Double p, Dimension d) {
        super(p, d);
        removable = false;
        try{
            InputStream in = new FileInputStream("dig.wav");
            audioStream = new AudioStream(in);
        }catch (IOException ex){
            System.out.println("FAIL");
        }
        stopBehavior = new StopAndRemoveBehavior(this,20,audioStream);
    }
    public Gravel(Point2D.Double p){
        this(p,new Dimension(50,50));
    }
    @Override
    public void setRemovable(){
        removable = true;
    }
    @Override
    public boolean isRemovable(){
        return removable;
    }


}
