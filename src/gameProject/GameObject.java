package gameProject;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 4/13/13
 * Time: 9:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameObject extends Rectangle{
    public GameObject(Point p, Dimension d){
        super(p, d);
    }
    public GameObject(int x, int y, int width, int height){
        super(x, y, width, height);
    }
}
