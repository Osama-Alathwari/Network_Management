/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author root
 */
public class BroadCastServer extends Thread {

    public boolean closed = false;
    public String ip = "255.255.255.0";//Broadcast address
    public int port = 6787;//Specify broadcast receiving data port
    public int MessageIndex = 0;//Data transmission times

    /*public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("UdpBroadcastServer start ");
                runServer();
            }
        }).start();
    }*/
    private void runServer() {
        try {
            while (!closed) {
                //Thread.sleep(2000);
                send("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UdpTestServer run Exception: " + e.toString());
        }
    }

    public void send(String msg) {
        try {
            String sendMessage = "hello ,message from server," + MessageIndex++;
            InetAddress adds = InetAddress.getByName(ip);
            DatagramSocket ds = new DatagramSocket();
            DatagramPacket dp = new DatagramPacket(sendMessage.getBytes(), sendMessage.length(), adds, port);
            ds.send(dp);
            ds.close();
        } catch (Exception e) {
            System.out.println("UdpTestServer send Exception: " + e.toString());
        }

        if (MessageIndex >= 50) {
            closed = true;
            System.out.println("closed  ");
        }
    }

    /*
    public static void main(String[] args) {
        BroadCastServer server = new BroadCastServer();
        server.start();
    }*/

    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        System.out.println("UdpBroadcastServer start ");
        runServer();
    }

}
