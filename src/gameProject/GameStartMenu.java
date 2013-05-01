package gameProject;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 4/13/13
 * Time: 11:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameStartMenu {
    private ArrayList<StartMenuItem> menuItems;
    private StartMenuItem menuItem;
    public GameStartMenu(){
        menuItems = new ArrayList<StartMenuItem>();
    }
    public void addMenuItem(StartMenuItem item){
        menuItems.add(item);
    }
    public ArrayList<StartMenuItem> getMenuItems(){
        return menuItems;
    }
    public String intersectionGetEventName(Point p){
        for(StartMenuItem menuItem : menuItems){
            if(menuItem.contains(p)){
                return menuItem.toString();
            }
        }
        return "";
    }
}
