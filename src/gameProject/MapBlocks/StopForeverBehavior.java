package gameProject.MapBlocks;

import gameProject.GameObject;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/4/13
 * Time: 8:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class StopForeverBehavior implements StopBehavior{
    GameObject currentObj;

    public StopForeverBehavior(GameObject obj){
        this.currentObj = obj;
    }
    @Override
    public void stop(GameObject obj){
        if(currentObj.intersects(obj)){
            if(currentObj.contains(obj.getMinX(),obj.getY()+obj.getHeight()/2)){
                obj.setLocation((int)currentObj.getMaxX(),(int)obj.getY());
            }
            else if(currentObj.contains(obj.getMaxX(),obj.getY()+obj.getHeight()/2)){
                obj.setLocation((int)currentObj.getMinX()-(int)obj.getWidth(),(int)obj.getY());
            }
            else if(currentObj.contains(obj.getX()+obj.getWidth()/2 ,obj.getMinY())){
                obj.setLocation((int)obj.getX(),(int)currentObj.getMaxY());
            }
            else if(currentObj.contains(obj.getX()+obj.getWidth()/2 ,obj.getMaxY())){
                obj.setLocation((int)obj.getX(), (int)currentObj.getMinY()-(int)obj.getHeight());
            }
        }
    }
}
