package gameProject;

import gameProject.MapBlocks.Gravel;
import gameProject.MapBlocks.MapBlock;
import gameProject.MapBlocks.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/6/13
 * Time: 7:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientMain extends JFrame {
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
    private ArrayList<MapBlock> mapBlocks;

    // Handle for the custom drawing panel
    private GameCanvas canvas;

    //Network variables
    DatagramSocket socket;
    DatagramPacket packet;
    byte[] serverReceiveBuf;
    byte[] serverSendBuf;
    InetAddress address;
    /**
     * Constructor to initialize the UI components and game objects
     */
    public ClientMain(){
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
        this.setTitle("Luffy gejmar på nätet");
        this.setVisible(true);


    }

    // All the game related codes here

    // Initialize all the game objects, run only once in the constructor of the main class.
    public void gameInit(){
        player = new Player("player",0);
        bullets = new ArrayList<Bullet>();
        gameState = GameState.INITIALIZED;

    }

    // Shutdown the game, clean up code that runs only once.
    public void gameShutdown() {
        //TODO: Disconnect from server
        socket.close();
        System.out.println("shut down");
    }

    // To start and re-start the game.
    public  void gameStart() {
        gameJoinServer();

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
        player = new Player("player",0);
        bullets = new ArrayList<Bullet>();
        mapBlocks = new ArrayList<MapBlock>();
        //TODO:Ska hämtas från servern egentligen
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
    public void gameJoinServer(){
        //set connectionvalues
        /*String s = (String)JOptionPane.showInputDialog(
                this,
                "Insert name and server address: ",
                "Name and Server",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "player");*/
        /*JTextField username = new JTextField();
        JTextField serveraddress = new JTextField();
        Object[] message = {
                "Username:", username,
                "Server IP:", serveraddress
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Insert name and server address", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            System.out.println("done");
        } else {
            System.out.println("canceled");
        }
        player.setName(username.getText().toString());
        */
        //System.out.println(s);
        //TODO: Connect to server
        serverReceiveBuf = new byte[1024];
        serverSendBuf = ("I" + player.getName()).getBytes();
        String received;
        String send;

        try {
            //address = InetAddress.getByName(serveraddress.getText().toString());
            address = InetAddress.getByName("127.0.0.1");
            socket = new DatagramSocket();
            while(true){
                packet = new DatagramPacket(serverSendBuf, serverSendBuf.length, address, 10999);
                //System.out.println(new String(packet.getData(), 0, packet.getLength()));
                socket.send(packet);
                socket.receive(packet);
                received = new String(packet.getData(), 0, packet.getLength());
                //System.out.println(received);
                if(received.equals("IF")){
                    player.setName("player"+ new Random().nextInt(100));
                    serverSendBuf = ("I" + player.getName()).getBytes();
                }
                else if(received.equals("IS")){
                    break;
                }
            }
        }catch (SocketException e){
            System.out.println(e.getMessage());
        }catch (UnknownHostException e){
            System.out.println(e.getMessage());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        //System.out.println("You are connected to server: " + address.getHostAddress());
        System.out.println("Your name on the server is: " + player.getName());
        try {
            packet = new DatagramPacket(serverReceiveBuf, serverReceiveBuf.length);
            socket.receive(packet);
            received = new String(packet.getData(), 0, packet.getLength());
            System.out.println(received);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        //get response
        /*packet = new DatagramPacket(serverReceiveBuf, serverReceiveBuf.length);
        try {
            socket.receive(packet);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Mottaget: " + received);*/

    }

    // Update the gameState and position of all the game objects,
    // detect collisions and provide responses.
    public void gameUpdate() {
        //TODO: Update and get server info
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
                //TODO: Get info from server to draw
                g2d.drawImage(player.getImage(), (int) player.getX(), (int) player.getY(), null);
                for(MapBlock mapBlock : mapBlocks){
                    if(mapBlock instanceof Wall){
                        g2d.setColor(Color.BLUE);
                        g2d.fill(new RoundRectangle2D.Float((float) mapBlock.getX(), (float) mapBlock.getY(), (float) mapBlock.getWidth(), (float) mapBlock.getHeight(), 50, 50));
                    }
                    else if(mapBlock instanceof Gravel){
                        g2d.setColor(Color.DARK_GRAY);
                        g2d.fill(mapBlock);
                    }
                }
                g2d.setColor(Color.YELLOW);
                g2d.setStroke(new BasicStroke(5));
                if(!bullets.isEmpty()){
                    for(Bullet bullet : bullets){
                        g2d.draw(bullet);
                    }
                }
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
    public void gameKeyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                //player.setLocation((int)player.getX(), (int)player.getY()-5);
                //TODO: call server player.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                //player.setLocation((int)player.getX(), (int)player.getY()+5);
                //TODO: call server player.moveDown();
                break;
            case KeyEvent.VK_LEFT:
                //player.setLocation((int)player.getX()-5, (int)player.getY());
                //TODO: call server player.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                //TODO: call server player.moveRight();
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
                //TODO: call server player.stopLeft();
                break;
            case KeyEvent.VK_RIGHT:
                //TODO: call server player.stopRight();
                break;
            case KeyEvent.VK_UP:
                //TODO: call server player.stopUp();
                break;
            case KeyEvent.VK_DOWN:
                //TODO: call server player.stopDown();
                break;
        }
    }
    // Process a key-typed event.
    public void gameKeyTyped(char keyChar) {  }

    public void gameMousePressed(MouseEvent e){
        if(gameState == GameState.MAINMENU){
            for ( StartMenuItem item : startMenu.getMenuItems()){
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
            //TODO:: Make the bullets dissapear from arraylist when they hit object. Can be the reason to thrown: ConcurrentModificationException
            //TODO: call server bullets.add(new Bullet(new Point2D.Double(player.getX()+player.getWidth()/2,player.getY()+player.getHeight()/2),
            //        new Point2D.Double((double)e.getX(),(double)e.getY())));
        }

    }

    // Other methods
    // ......
    private void drawMainMenu(){
        startMenu = new GameStartMenu();
        startMenu.addMenuItem(new StartMenuItem(CANVAS_WIDTH/6,10,400,100,"start"));
        startMenu.addMenuItem(new StartMenuItem(CANVAS_WIDTH/6,2*CANVAS_HEIGHT/6,400,100,"level"));
        startMenu.addMenuItem(new StartMenuItem(CANVAS_WIDTH/6,4*CANVAS_HEIGHT/6,400,100,"exit"));
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
    public static void main(String[] args){
        // Use the event dispatch thread to build the UI for thread-safety.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientMain();
            }
        });
    }
}
