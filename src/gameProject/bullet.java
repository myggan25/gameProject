package gameProject;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/5/13
 * Time: 12:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class Bullet{
    private double srcX, srcY, targetX, targetY, movingX, movingY;
    private double deltaX, deltaY;
    public Bullet(double srcX, double srcY, double targetX, double targetY){
        this.srcX = srcX;
        this.srcY = srcY;
        movingX = srcX;
        movingY = srcY;
        this.targetX = targetX;
        this.targetY = targetY;
        //speed = ((targetX-srcX)+(targetY-srcY))/10;
        //System.out.println(speed);
        deltaX = targetX-srcX;
        deltaY = targetY-srcY;
    }

    /**
     * This function makes the bullet move closer to the pressed
     * target or further away when it have passed.
     */
    public void closeInToTarget(){
        //the formulas to calculate changeX and changeY is made so
        //every step is equally long.
        double changeX = 5*Math.cos(Math.atan(deltaY / deltaX));
        double changeY = 5*Math.sin(Math.atan(deltaY / deltaX));
        if(deltaX<0){
            movingX -= changeX;
            movingY -= changeY;
        }
        else{
            movingX += changeX;
            movingY += changeY;
        }
    }
    public double getMovingX(){
        return movingX;
    }
    public double getMovingY(){
        return movingY;
    }
    public double getTargetX(){
        return targetX;
    }
    public double getTargetY(){
        return targetY;
    }
    /*public boolean onTarget(){
        if(Math.floor(targetX)<movingX && Math.floor(targetY)<movingY){
            return true;
        }
        else{
            return false;
        }
    }*/
}
