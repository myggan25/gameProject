package gameProject;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/4/13
 * Time: 6:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Gravel extends MapBlock{

    public Gravel(Point p, Dimension d) {
        super(p, d);
        stopBehavior = new StopAndRemoveBehavior(this,20);
    }


}
