package gameProject;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/5/13
 * Time: 12:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class Bullet extends GameObject{
    private Point2D.Double src, target;
    private double deltaX, deltaY;
    private boolean removable;

    public Bullet(Point2D.Double src, Point2D.Double target){
        super(new Point2D.Double(src.getX(),src.getY()),new Dimension(1,1));
        this.target = new Point2D.Double(target.getX(), target.getY());
        this.src = new Point2D.Double(src.getX(), src.getY());

        deltaX = target.getX()-src.getX();
        deltaY = target.getY()-src.getY();
        removable = false;
    }
    public void setBulletSize(double width, double height){
        this.width = width;
        this.height = height;
    }
    @Override
    public boolean isRemovable(){
        return removable;
    }

    @Override
    public void setRemovable() {
        removable = true;
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
            x -= changeX;
            y -= changeY;
        }
        else{
            x += changeX;
            y += changeY;
        }
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
}
