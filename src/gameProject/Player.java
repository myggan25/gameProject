package gameProject;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/1/13
 * Time: 9:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player extends GameObject{
    public Player(Point p, Dimension d) {
        super(p, d);
    }

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

}
