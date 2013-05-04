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


    //Cool stuff that should be made to a new object
    /*public void jumpThrough(GameObject obj){
        if(this.intersects(obj)){
            if(this.contains(obj.getMinX(),obj.getY()+obj.getHeight()/2)){
                obj.setLocation((int)this.getMinX()-(int)obj.getWidth(),(int)obj.getY());
            }
            else if(this.contains(obj.getMaxX(),obj.getY()+obj.getHeight()/2)){
                obj.setLocation((int)this.getMaxX(),(int)obj.getY());
            }
            else if(this.contains(obj.getX()+obj.getWidth()/2 ,obj.getMinY())){
                obj.setLocation((int)obj.getX(),(int)this.getMinY()-(int)obj.getHeight());
            }
            else if(this.contains(obj.getX()+obj.getWidth()/2 ,obj.getMaxY())){
                obj.setLocation((int)obj.getX(), (int)this.getMaxY());
            }
        }
    }*/
}
