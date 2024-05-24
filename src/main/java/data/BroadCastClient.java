/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author root
 */
public class BroadCastClient extends Thread {

    private int MessageIndex = 0;
    private int port = 6787;//Data listening binding port
    private boolean closed = false;

    /*public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("BroadcastClient start ");
                runClient();
            }
        }).start();
    }*/

    public BroadCastClient() {
    }
    private JLabel targetMessage;
    public BroadCastClient(JLabel target) {
        targetMessage=target;
    }

    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        System.out.println("BroadcastClient start ");
        runClient();
    }

    DatagramSocket socket = null;

    private void runClient() {
        try {
            byte[] receiveBuffer = new byte[1024];//Data buffer
            socket = new DatagramSocket(port);//Bind ports for data listening
            System.out.println("Client BroadCast Connected ...");
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);//Data receiving capsule
            while (!closed) {
                System.out.println("Loop inside receive....");
                count();//Count the number and close the connection more than 50 times
                socket.receive(receivePacket);//receive data 
                System.out.println("received packet from " + receivePacket.getAddress().getHostAddress() + " : " + receivePacket.getPort());
                String msg = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
                System.out.println("received " + msg);
                Thread.sleep(2000);
            }
            System.out.println("socket close ");
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void count() {
        MessageIndex++;
        if (MessageIndex >= 50) {
            closed = true;
        }
    }
/*
    public static void main(String[] s) {
        BroadCastClient client = new BroadCastClient();
        client.start();
    }
*/
    public boolean isMessageReceived() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
