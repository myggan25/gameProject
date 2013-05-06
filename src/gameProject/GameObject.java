package gameProject;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 4/13/13
 * Time: 9:29 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class GameObject extends Rectangle2D.Double{

    public GameObject(Point2D.Double p, Dimension d){
        super(p.getX(),p.getY(), d.getWidth(),d.getHeight());
    }
    public void setLocation(double x, double y){
        this.x = x;
        this.y = y;
    }
    public void setBounds(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract boolean isRemovable();
    public abstract void setRemovable();
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
