package gameProject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/31/13
 * Time: 11:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerConnection {
    protected DatagramSocket socket = null;
    protected InetAddress address;
    protected int port;
    protected int playerID;

    public ServerConnection(DatagramSocket socket, InetAddress address, int port, int playerID){
        this.socket = socket;
        this.address = address;
        this.port = port;
        this.playerID = playerID;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
}
