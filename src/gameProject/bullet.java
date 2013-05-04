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
        private double srcX, srcY, targetX, targetY;
    public Bullet(double srcX, double srcY, double targetX, double targetY){
        this.srcX = srcX;
        this.srcY = srcY;
        this.targetX = targetX;
        this.targetY = targetY;
    }
    public void closeInToTarget(){

    }
    public double getMovingX(){
        return srcX;
    }
    public double getMovingY(){
        return srcY;
    }
    public boolean onTarget(){
        if(Math.ceil(targetX)>srcX && Math.floor(targetX)<srcX
                && Math.ceil(targetY)>srcY && Math.floor(targetY)<srcY){
            return true;
        }
        else{
            return false;
        }
    }
}
