package gameProject.MapBlocks;

import gameProject.GameObject;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/4/13
 * Time: 7:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapBlock extends GameObject {
    protected StopBehavior stopBehavior;

    public MapBlock(Point2D.Double p, Dimension d){
        super(p,d);
    }
    public MapBlock(Point2D.Double p){
        this(p,new Dimension(50,50));
    }
    public void performStop(GameObject obj){
        stopBehavior.stop(obj);
    }
}
