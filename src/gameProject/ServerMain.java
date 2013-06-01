package gameProject;

import com.sun.xml.internal.fastinfoset.algorithm.IntegerEncodingAlgorithm;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/24/13
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerMain {
    //protected static ArrayList<Player> players;
    //protected static ArrayList<ServerConnection> serverConnections;
    protected static Map<Integer,Player> players;
    protected static Map<Integer,ServerConnection> serverConnections;

    //protected String receiveMessage;
    //protected InetAddress address;
    protected static DatagramPacket packet;
    protected static int connectionPortnr;
    protected static DatagramSocket socket;
    //protected int maxClients;
    //protected int connectedClients;

    public ServerMain(){
        connectionPortnr = 10999;
        try{
        socket = new DatagramSocket(connectionPortnr);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new ServerMain();
        byte[] buf;
        serverConnections = new ConcurrentHashMap<Integer, ServerConnection>();
        players = new ConcurrentHashMap<Integer, Player>();
        Thread connectionThread = new Thread(
                        new ServerThreadConnect((ConcurrentHashMap<Integer,ServerConnection>)serverConnections,
                                (ConcurrentHashMap<Integer,Player>)players,
                                socket));
        connectionThread.start();

        //Väntar på att alla spelare ska ansluta till servern
        while (connectionThread.isAlive()){
            try {
                connectionThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        for( Map.Entry<Integer,ServerConnection> serverConnectionEntry : serverConnections.entrySet()){
            buf = ("You are player: " + players.get(serverConnectionEntry.getKey()).getName()).getBytes();
            packet = new DatagramPacket(buf, buf.length,
                    serverConnectionEntry.getValue().getAddress(),
                    serverConnectionEntry.getValue().getPort());
            socket.send(packet);
        }
        buf = new byte[1028];
        socket.receive(packet);
        //socket.close();
        System.out.println(new String(packet.getData(), 0, packet.getLength()));
    }
}
