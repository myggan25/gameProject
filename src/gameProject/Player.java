package gameProject;

import sun.audio.*;

import javax.imageio.ImageIO;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/1/13
 * Time: 9:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player extends GameObject{
    final int startVelocity = 3;
    private String sprite = "LuffySpriteSheet.png";
    private BufferedImage image;
    private int imageCols = 5;
    private int imageRows = 6;
    private BufferedImage[][] sprites;
    public enum StatusX {
        LEFTHOLD, LEFTRELEASED, RIGHTHOLD, RIGHTRELEASED, STILL;
    }
    public enum StatusY{
        UPHOLD, UPRELEASED, DOWNHOLD, DOWNRELEASED, STILL;
    }
    protected Velocity velocity;
    protected Acceleration acceleration;
    //protected StatusY statusY;
    protected StatusX statusX;
    protected StatusY statusY;

    public Player(){
        this(0,0,50,50);//these are the values that fit LuffySpriteSheet.png
    }

    public Player(Point p, Dimension d) {
        super(p,d);
        velocity = new Velocity(0.0, 0.0);
        acceleration = new Acceleration(0.0, 0.0, 0.01);
        statusX = StatusX.STILL;
        statusY = StatusY.STILL;
        sprites = new BufferedImage[imageRows][imageCols];

        try {
            image = ImageIO.read(new File(sprite));
        } catch (IOException ex) {
            System.out.println("FAIL");
        }
        for(int i=0; i<imageRows; i++){
            for(int j=0; j<imageCols; j++){
                sprites[i][j] = image.getSubimage(j*width,i*height,width,height);
                //sprites[i] = image.getSubimage(i*width,0,width,height);
            }

        }
    }
    public Player(int x, int y, int width, int height) {
        this(new Point(x,y),new Dimension(width,height));
    }


    public Image getImage(){
        if(statusX==StatusX.STILL && statusY==StatusY.STILL){
            return sprites[0][1];
        }
        else if(statusY == StatusY.UPHOLD){
            return sprites[2][0].getSubimage(0,0,width,height-3);
        }
        else if(statusY == StatusY.UPRELEASED){
            return sprites[2][2].getSubimage(0,0,width,height-3);
        }
        else if(statusX == StatusX.LEFTHOLD){
            return sprites[1][3];
        }
        else if(statusX == StatusX.LEFTRELEASED){
            return sprites[1][1];
        }
        else if(statusX == StatusX.RIGHTHOLD){
            return sprites[3][0];//.getSubimage(0,0,width,height-3);
        }
        else if(statusX == StatusX.RIGHTRELEASED){
            return sprites[3][1];//.getSubimage(0,0,width,height-3);
        }
        else if(statusY == StatusY.DOWNHOLD){
            return sprites[0][0];
        }
        else if(statusY == StatusY.DOWNRELEASED){
            return sprites[0][2];
        }
        else{
            return sprites[4][1];
        }
    }
    public boolean turnedLeft(){
        if(statusX == StatusX.LEFTHOLD || statusX == StatusX.LEFTRELEASED ){
            return true;
        }
        else
            return false;
    }
    public void moveLeft(){
        statusX = StatusX.LEFTHOLD;
        acceleration.x=acceleration.start;
        velocity.x=-startVelocity;
    }
    public void stopLeft(){
        if (statusX != StatusX.RIGHTHOLD){
            statusX = StatusX.LEFTRELEASED;
        }
    }
    public void moveRight(){
        statusX = StatusX.RIGHTHOLD;
        acceleration.x=acceleration.start;
        velocity.x=startVelocity;
    }
    public void stopRight(){
        if(statusX != StatusX.LEFTHOLD){
            statusX = StatusX.RIGHTRELEASED;
        }
    }
    public void moveUp(){
        statusY = StatusY.UPHOLD;
        acceleration.y=acceleration.start;
        velocity.y=-startVelocity;
    }
    public void stopUp(){
        if (statusY != StatusY.DOWNHOLD){
            statusY = StatusY.UPRELEASED;
        }
    }
    public void moveDown(){
        statusY = StatusY.DOWNHOLD;
        acceleration.y=acceleration.start;
        velocity.y=startVelocity;
    }
    public void stopDown(){
        if(statusY != StatusY.UPHOLD){
            statusY = StatusY.DOWNRELEASED;
        }
    }

    public void handleY(){
        if(velocity.y > 1 && statusY == StatusY.DOWNRELEASED){
            acceleration.y += acceleration.start;
            velocity.y-= acceleration.y;
        }
        else if(velocity.y < -1 && statusY == StatusY.UPRELEASED){
            acceleration.y += acceleration.start;
            velocity.y+= acceleration.y;
        }
        else if(statusY != StatusY.UPHOLD && statusY != StatusY.DOWNHOLD){
            statusY = StatusY.STILL;
            acceleration.y=0;
            velocity.y=0;
        }
        this.setLocation((int)this.getX(),(int)this.getY()+(int)velocity.getY());
    }
    public void handleX(){
        if(velocity.x > 1 && statusX == StatusX.RIGHTRELEASED){
            acceleration.x += acceleration.start;
            velocity.x-= acceleration.x;
        }
        else if(velocity.x < -1 && statusX == StatusX.LEFTRELEASED){
            acceleration.x += acceleration.start;
            velocity.x+= acceleration.x;
        }
        else if(statusX != StatusX.LEFTHOLD && statusX != StatusX.RIGHTHOLD){
            statusX = StatusX.STILL;
            acceleration.x=0;
            velocity.x=0;
        }
        this.setLocation((int)this.getX()+(int)velocity.getX(),(int)this.getY());
    }

}
