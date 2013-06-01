package gameProject;

import gameProject.MapBlocks.Gravel;
import gameProject.MapBlocks.MapBlock;
import gameProject.MapBlocks.StopAndRemoveBehavior;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/6/13
 * Time: 7:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerMainBackup extends JFrame {     // main class for the game as a Swing application

    // Define constants for the game
    static final int CANVAS_WIDTH = 1200;    // width and height of the game screen
    static final int CANVAS_HEIGHT = 600;
    static final int UPDATE_RATE = 60;    // number of game update per second
    static final long UPDATE_PERIOD = 1000000000L / UPDATE_RATE;  // nanoseconds
    // ......

    static GameState gameState;   // current State of the game

    // Define instance variables for the game objects
    private GameStartMenu startMenu;
    private Player player;
    private ArrayList<Bullet> bullets;
    /*private ArrayList<Wall> walls;
    private ArrayList<Gravel> gravels;
    */
    private ArrayList<MapBlock> mapBlocks;

    // Handle for the custom drawing panel

    // Constructor to initialize the UI components and game objects
    public ServerMainBackup() {
        // Initialize the game objects
        gameInit();

    }

    // All the game related codes here

    // Initialize all the game objects, run only once in the constructor of the main class.
    public void gameInit() {
        //TODO: take incomming connections from clients
        //player = new Player();
        //playerBox = new PlayerBox(100,100,50,50);
        mapBlocks = new ArrayList<MapBlock>();
        gameState = GameState.INITIALIZED;


    }

    // Shutdown the game, clean up code that runs only once.
    public void gameShutdown() {
        System.out.println("shut down");
    }

    // To start and re-start the game.
    public  void gameStart() {
        // Create a new thread
        Thread gameThread =  new Thread() {
            // Override run() to provide the running behavior of this thread.
            @Override
            public void run() {
                gameLoop();
            }
        };
        // Start the thread. start() calls run(), which in turn calls gameLoop().
        gameThread.start();
    }

    // Run the game loop here.
    private void gameLoop() {
        // Regenerate the game objects for a new game
        // ......
        player = new Player("troll",0);
        bullets = new ArrayList<Bullet>();
        //bullet = new Bullet(200,200);
        //playerBox = new PlayerBox(100,100,100,100);

        //mapBlocks.add(new Wall(new Point(300, 300), new Dimension(200, 200)));
        //mapBlocks.add(new Gravel(new Point(100,100), new Dimension(50,50)));
        //TODO:: Add a map class and a mapmaker class.
        for(int i=0; i<CANVAS_WIDTH;i+=50){
            for(int j=0; j<CANVAS_HEIGHT;j+=50){
                if(!(i==0 && j==0)){
                    mapBlocks.add(new Gravel(new Point2D.Double(i,j)));
                }
            }
        }
        gameState = gameState.PLAYING;
        // Game loop
        long beginTime, timeTaken, timeLeft;
        while (true) {
            beginTime = System.nanoTime();
            if (gameState == GameState.GAMEOVER) break;  // break the loop to finish the current play
            if (gameState == GameState.PLAYING) {
                // Update the gameState and position of all the game objects,
                // detect collisions and provide responses.
                gameUpdate();
            }
            // Refresh the display
            repaint();
            // Delay timer to provide the necessary delay to meet the target rate
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (UPDATE_PERIOD - timeTaken) / 1000000L;  // in milliseconds
            if (timeLeft < 10) timeLeft = 10;   // set a minimum
            try {
                // Provides the necessary delay and also yields control so that other thread can do work.
                Thread.sleep(timeLeft);
            } catch (InterruptedException ex) { }
        }
    }

    // Update the gameState and position of all the game objects,
    // detect collisions and provide responses.
    public void gameUpdate() {
        player.handleX();
        player.handleY();
        ListIterator<MapBlock> iterBlocks = mapBlocks.listIterator();
        MapBlock mapBlock;
        while(iterBlocks.hasNext()){
            mapBlock = iterBlocks.next();
            mapBlock.performStop(player);
            if(!bullets.isEmpty()){
                for(Bullet bullet : bullets){
                    mapBlock.performStop(bullet);
                }
            }
            if(mapBlock.isRemovable()){
                iterBlocks.remove();
            }
        }
        StopAndRemoveBehavior.resetCounted();

        ListIterator<Bullet> iterBullets = bullets.listIterator();
        Bullet bullet;
        while(iterBullets.hasNext()){
            bullet = iterBullets.next();
            if(bullet.isRemovable()){
                iterBullets.remove();
            }
            bullet.closeInToTarget();
        }
    }

    public void gameKeyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                //player.setLocation((int)player.getX(), (int)player.getY()-5);
                player.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                //player.setLocation((int)player.getX(), (int)player.getY()+5);
                player.moveDown();
                break;
            case KeyEvent.VK_LEFT:
                //player.setLocation((int)player.getX()-5, (int)player.getY());
                player.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                player.moveRight();
                //player.setLocation((int)player.getX()+5, (int)player.getY());
                break;
            case KeyEvent.VK_ESCAPE:
                gameState = GameState.MAINMENU;
                break;
            case KeyEvent.VK_Q:
                gameStart();
                break;
        }
    }
    // Process a key-released event.
    public void gameKeyReleased(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                player.stopLeft();
                break;
            case KeyEvent.VK_RIGHT:
                player.stopRight();
                break;
            case KeyEvent.VK_UP:
                player.stopUp();
                break;
            case KeyEvent.VK_DOWN:
                player.stopDown();
                break;
        }
    }
    // Process a key-typed event.
    public void gameKeyTyped(char keyChar) {  }

    public void gameMousePressed(MouseEvent e){
        if(gameState == GameState.PLAYING){
            //TODO:: Make the bullets dissapear from arraylist when they hit object. Can be the reason to thrown: ConcurrentModificationException
            bullets.add(new Bullet(new Point2D.Double(player.getX()+player.getWidth()/2,player.getY()+player.getHeight()/2),
                    new Point2D.Double((double)e.getX(),(double)e.getY())));
            //bullets.add(new Bullet(player.getX()+player.getWidth()/2,player.getY()+player.getHeight()/2, (double)e.getX(),(double)e.getY()));
            //playerBox.setLocation(e.getX()-(int)playerBox.getWidth()/2,e.getY()-(int)playerBox.getHeight()/2);
        }
    }

    // Other methods
    // ......


    // main
    public static void main(String[] args) {
        ServerMainBackup serverMain = new ServerMainBackup();
    }
}
