package gameProject;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/2/13
 * Time: 9:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class Acceleration {
    protected double x;
    protected double y;
    protected double start;

    public Acceleration(double x, double y, double start){
        this.x = x;
        this.y = y;
        this.start = start;
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getStart(){
        return start;
    }
}
