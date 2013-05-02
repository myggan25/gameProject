package gameProject;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/1/13
 * Time: 9:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player extends GameObject{
    final int startVelocity = 20;

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

    public Player(Point p, Dimension d) {
        super(p, d);
        velocity = new Velocity(0.0, 0.0);
        acceleration = new Acceleration(0.0, 0.0, 0.01);
        statusX = StatusX.STILL;
        statusY = StatusY.STILL;
    }

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
        velocity = new Velocity(0.0, 0.0);
        acceleration = new Acceleration(0.0, 0.0, 0.01);
        statusX = StatusX.STILL;
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
