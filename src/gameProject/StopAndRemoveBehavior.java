package gameProject;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/4/13
 * Time: 8:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class StopAndRemoveBehavior implements StopBehavior {
    private GameObject currentObj;
    private int counterToDelete;
    private static boolean countedOne = false;
    private AudioStream audioStream;

    public StopAndRemoveBehavior(GameObject obj, int waitTimeForRemove, AudioStream audioStream){
        this.currentObj = obj;
        counterToDelete = waitTimeForRemove;
        this.audioStream = audioStream;
    }
    @Override
    public void stop(GameObject obj){
        if(currentObj.intersects(obj) && counterToDelete!=0){
            if(countedOne == false){
                counterToDelete -=1;
                countedOne = true;
            }
            if(currentObj.contains(obj.getMinX(), obj.getY() + obj.getHeight() / 2)){
                obj.setLocation((int)currentObj.getMaxX(),(int)obj.getY());
            }
            else if(currentObj.contains(obj.getMaxX(), obj.getY() + obj.getHeight() / 2)){
                obj.setLocation((int)currentObj.getMinX()-(int)obj.getWidth(),(int)obj.getY());
            }
            else if(currentObj.contains(obj.getX() + obj.getWidth() / 2, obj.getMinY())){
                obj.setLocation((int)obj.getX(),(int)currentObj.getMaxY());
            }
            else if(currentObj.contains(obj.getX() + obj.getWidth() / 2, obj.getMaxY())){
                obj.setLocation((int)obj.getX(), (int)currentObj.getMinY()-(int)obj.getHeight());
            }
        }
        else if( counterToDelete == 0){
            AudioPlayer.player.start(audioStream);
            currentObj.setBounds(0, 0, 0, 0);
        }
    }
    public static void resetCounted(){
        countedOne = false;
    }
}
