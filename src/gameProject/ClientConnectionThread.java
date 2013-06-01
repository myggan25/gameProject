package gameProject;

import java.io.IOException;
import java.net.*;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 5/30/13
 * Time: 10:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientConnectionThread implements Runnable{
    DatagramSocket socket;
    DatagramPacket receivedPacket;
    DatagramPacket responsePacket;
    InetAddress clientAddress;
    int clientPort;
    byte[] responseData;

    public ClientConnectionThread(DatagramSocket socket, DatagramPacket packet){
        this.socket = socket;
        this.receivedPacket = packet;
        clientAddress = packet.getAddress();
        clientPort = packet.getPort();

    }

    @Override
    public void run() {
        //System.out.println("Inne i clientConnThread");
        responseData = "IS".getBytes();
        responsePacket = new DatagramPacket(responseData, responseData.length,
                clientAddress, clientPort);
        try {
            socket.send(responsePacket);
            //socket.receive(receivedPacket);
            System.out.println(new String(receivedPacket.getData(),0,receivedPacket.getLength()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("klar");


    }
}

