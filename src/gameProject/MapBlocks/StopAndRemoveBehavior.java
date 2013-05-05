package gameProject.MapBlocks;

import gameProject.GameObject;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.*;

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
                obj.setLocation(currentObj.getMaxX(),obj.getY());
            }
            else if(currentObj.contains(obj.getMaxX(), obj.getY() + obj.getHeight() / 2)){
                obj.setLocation(currentObj.getMinX()-obj.getWidth(),(int)obj.getY());
            }
            else if(currentObj.contains(obj.getX() + obj.getWidth() / 2, obj.getMinY())){
                obj.setLocation(obj.getX(),currentObj.getMaxY());
            }
            else if(currentObj.contains(obj.getX() + obj.getWidth() / 2, obj.getMaxY())){
                obj.setLocation(obj.getX(), currentObj.getMinY()-(int)obj.getHeight());
            }
        }
        else if( counterToDelete == 0){
            currentObj.setBounds(0, 0, 0, 0);
            play();
        }
    }
    private void play(){
        AudioPlayer.player.start(audioStream);
    }

    public static void resetCounted(){
        countedOne = false;
    }
}
