package gameProject;

import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JFrame {     // main class for the game as a Swing application

    // Define constants for the game
    static final int CANVAS_WIDTH = 800;    // width and height of the game screen
    static final int CANVAS_HEIGHT = 600;
    static final int UPDATE_RATE = 60;    // number of game update per second
    static final long UPDATE_PERIOD = 1000000000L / UPDATE_RATE;  // nanoseconds
    // ......
    private GameStartMenu startMenu;
    private Player player;
    private PlayerBox playerBox;


    // Enumeration for the states of the game.
    static enum GameState {
        INITIALIZED, MAINMENU, PLAYING, PAUSED, GAMEOVER, DESTROYED
    }
    static GameState gameState;   // current State of the game

    // Define instance variables for the game objects
    // ......
    // ......

    // Handle for the custom drawing panel
    private GameCanvas canvas;

    // Constructor to initialize the UI components and game objects
    public GameMain() {
        // Initialize the game objects
        gameInit();

        // UI components
        canvas = new GameCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.setContentPane(canvas);

        // Other UI components such as button, score board, if any.
        // ......
        drawMainMenu();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setTitle("MY GAME");
        this.setVisible(true);

    }

    // All the game related codes here

    // Initialize all the game objects, run only once in the constructor of the main class.
    public void gameInit() {
        // ......
        player = new Player(0,0,50,50);
        playerBox = new PlayerBox(100,100,50,50);
        gameState = GameState.INITIALIZED;

    }

    // Shutdown the game, clean up code that runs only once.
    public void gameShutdown() {
        System.out.println("shut down");
    }

    // To start and re-start the game.
    public void gameStart() {
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
        player = new Player(0,0,50,50);
        playerBox = new PlayerBox(100,100,50,50);
        gameState = GameState.PLAYING;

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
        //System.out.println("Update");
    }

    // Refresh the display. Called back via rapaint(), which invoke the paintComponent().
    private void gameDraw(Graphics2D g2d) {
        g2d.setColor(Color.CYAN);
        switch (gameState) {
            case INITIALIZED:
                // ......
                break;
            case MAINMENU:
                g2d.setFont(new Font("Tahoma", Font.BOLD, 24));
                for (StartMenuItem item : startMenu.getMenuItems()){
                    g2d.setColor(Color.CYAN);
                    g2d.fill(item);
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(item.toString(),(float)item.getCenterX()-25,(float)item.getCenterY()+5);
                }
                break;
            case PLAYING:
                g2d.fill(player);
                g2d.fill(playerBox);
                break;
            case PAUSED:
                // ......
                break;
            case GAMEOVER:
                // ......
                break;
        }
        // ......
    }

    // Process a key-pressed event. Update the current gameState.
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
        if(gameState == GameState.MAINMENU){
            for ( StartMenuItem item : startMenu.getMenuItems()){
                System.out.println(e.getX()+" "+e.getY());
                if(item.contains(e.getPoint()) && item.toString().equals("start")){
                    gameStart();
                }
                else if(item.contains(e.getPoint()) && item.toString().equals("level")){
                    System.out.println("välj level här");
                }
                else if(item.contains(e.getPoint()) && item.toString().equals("exit")){
                    System.exit(0);
                }
            }
        }
        else if(gameState == GameState.PLAYING){
            playerBox.setLocation(e.getX()-(int)player.getWidth()/2,e.getY()-(int)player.getHeight()/2);
        }
    }
    // Other methods
    // ......
    private void drawMainMenu(){
        startMenu = new GameStartMenu();
        startMenu.addMenuItem(new StartMenuItem(CANVAS_WIDTH/2,CANVAS_HEIGHT/4,100,50,"start"));
        startMenu.addMenuItem(new StartMenuItem(CANVAS_WIDTH/2,2*CANVAS_HEIGHT/4,100,50,"level"));
        startMenu.addMenuItem(new StartMenuItem(CANVAS_WIDTH/2,3*CANVAS_HEIGHT/4,100,50,"exit"));
        gameState = GameState.MAINMENU;

    }

    // Custom drawing panel, written as an inner class.
    class GameCanvas extends JPanel implements KeyListener, MouseListener {
        // Constructor
        public GameCanvas() {
            setFocusable(true);  // so that can receive key-events
            requestFocus();
            addKeyListener(this);
            addMouseListener(this);
        }

        // Override paintComponent to do custom drawing.
        // Called back by repaint().
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            super.paintComponent(g2d);   // paint background
            setBackground(Color.BLACK);  // may use an image for background

            // Draw the game objects
            gameDraw(g2d);
        }

        // KeyEvent handlers
        @Override
        public void keyPressed(KeyEvent e) {
            gameKeyPressed(e.getKeyCode());
        }
        @Override
        public void keyReleased(KeyEvent e) {
            gameKeyReleased(e.getKeyCode());
        }
        @Override
        public void keyTyped(KeyEvent e) { //gameKeyTyped(e.getKeyChar());
        }
        //MouseEvent handlers
        @Override
        public void mouseClicked(MouseEvent e) { }
        @Override
        public void mousePressed(MouseEvent e) {
            gameMousePressed(e);
        }
        @Override
        public void mouseReleased(MouseEvent e) { }
        @Override
        public void mouseEntered(MouseEvent e) { }
        @Override
        public void mouseExited(MouseEvent e) { }
    }

    // main
    public static void main(String[] args) {
        // Use the event dispatch thread to build the UI for thread-safety.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameMain();
            }
        });
    }
}
