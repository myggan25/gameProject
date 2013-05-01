package gameProject;


import java.awt.*;

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
    public PlayerBox(Point p, Dimension d) {
        super(p, d);
        powerRestoreCounter = 0;
    }

    public PlayerBox(int x, int y, int width, int height) {
        super(x, y, width, height);
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