package gameProject;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/24/13
 * Time: 2:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerThreadConnect implements Runnable{//extends Thread{
    protected DatagramSocket socket;
    protected DatagramSocket connectionSocket;
    protected boolean running = true;
    protected Map<Integer, Player> players;
    protected String receiveMessage;
    protected InetAddress address;
    protected byte[] buf;
    protected DatagramPacket packet;
    protected int port;
    protected int maxClients;
    protected int connectedClients;
    protected Map<Integer, ServerConnection> serverConnections;

    /*public ServerThreadConnect(ArrayList<ServerConnection> serverConnections, DatagramSocket socket) throws IOException {
        this("ServerThread", serverConnections, socket);
    }*/

    public ServerThreadConnect(ConcurrentHashMap<Integer, ServerConnection> serverConnections,
                               ConcurrentHashMap<Integer, Player> players,
                               DatagramSocket socket) throws IOException {
        this.serverConnections = serverConnections;
        //players = new HashMap<Integer, Player>();
        this.players = players;
        this.socket = socket;
        //socket = new DatagramSocket(10999);
        //sockets = new ArrayList<DatagramSocket>();
        maxClients = 4;
        connectedClients = 0;
    }

    public void run() {
        //let clients connect to server
        outer:
        while (running) {
            if(connectedClients==2){
                break;
            }
            try {
                buf = new byte[1028];
                // receive request
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                address = packet.getAddress();
                port = packet.getPort();
                receiveMessage = new String(packet.getData(), 0, packet.getLength());
                if(receiveMessage.substring(0,1).equals("I")){
                    for ( Map.Entry<Integer,Player> playerEntry : players.entrySet()){
                        if(playerEntry.getValue().getName().equals(receiveMessage.substring(1, receiveMessage.length()))){
                            buf = "IF".getBytes();
                            packet = new DatagramPacket(buf, buf.length, address, port);
                            socket.send(packet);
                            continue outer;
                        }
                    }
                    //players.add(new Player(receiveMessage.substring(1,receiveMessage.length()), connectedClients));
                    //serverConnections.add(new ServerConnection(socket,packet.getAddress(),packet.getPort(),connectedClients));
                    players.put(connectedClients,new Player(receiveMessage.substring(1,receiveMessage.length()),connectedClients));
                    serverConnections.put(connectedClients,new ServerConnection(socket,packet.getAddress(),packet.getPort(),connectedClients));
                    System.out.println("Player " + players.get(connectedClients).getName() + " connected to server.");
                    connectedClients+=1;
                    buf = "IS".getBytes();
                    packet = new DatagramPacket(buf, buf.length, address, port);
                    socket.send(packet);
                }
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            }
        }
        System.out.println("4 connections is made.");
    }
}
