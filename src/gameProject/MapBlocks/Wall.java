package gameProject.MapBlocks;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/4/13
 * Time: 1:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class Wall extends MapBlock{
    private boolean removable;

    public Wall(Point2D.Double p, Dimension d) {
        super(p, d);
        stopBehavior = new StopForeverBehavior(this);
        removable = false;
    }

    @Override
    public void setRemovable() {
        removable = true;
    }
    @Override
    public boolean isRemovable(){
        return removable;
    }
}
