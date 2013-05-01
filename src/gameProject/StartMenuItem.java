package gameProject;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 4/13/13
 * Time: 11:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class StartMenuItem extends Rectangle {
    private String event;

    public StartMenuItem(Point p, Dimension d, String event){
        super(p, d);
        this.event = new String(event);
    }
    public StartMenuItem(int x, int y, int width, int height, String event){
        super(x, y, width, height);
        this.event = new String(event);
    }
    public String toString(){
        return event;
    }
}
