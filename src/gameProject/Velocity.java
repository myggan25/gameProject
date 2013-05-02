package gameProject;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/2/13
 * Time: 9:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class Velocity {
    protected double x;
    protected double y;

    public Velocity(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
}