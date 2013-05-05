package gameProject;


import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 4/13/13
 * Time: 9:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerBox extends GameObject{
    final private int powerRestoretime = 100;
    private int powerRestoreCounter;
    public PlayerBox(Point2D.Double p, Dimension d) {
        super(p, d);
        powerRestoreCounter = 0;
    }

    public PlayerBox(double x, double y, double width, double height) {
        this(new Point2D.Double(x,y),new Dimension((int)width,(int)height));
    }
    public int getPowerRestoreCounter(){
        return powerRestoreCounter;
    }
    public void incrPowerRestoreCounter(){
        powerRestoreCounter++;
    }
    public void resetPowerRestoreCounter(){
        powerRestoreCounter=0;
    }
    public boolean maxPower(){
        if(powerRestoreCounter == powerRestoretime){
            return true;
        }
        else{
            return false;
        }
    }
}