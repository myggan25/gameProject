package gameProject;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/4/13
 * Time: 7:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapBlock extends GameObject{
    protected StopBehavior stopBehavior;

    public MapBlock(Point p, Dimension d){
        super(p,d);
    }
    public MapBlock(Point p){
        this(p,new Dimension(50,50));
    }
    public void performStop(GameObject obj){
        stopBehavior.stop(obj);
    }
}
