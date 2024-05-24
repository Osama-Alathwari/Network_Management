/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Client.ClientMessages.BroadCastMessageClient;
import Client.ClientMessages.MultiCastMessageClient;
import Client.ClientMessages.UnicastMessageClient;
import Client.ClientPC;
import Client.Remote.ClientInitiator;
import data.Setting;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author root
 */
public class ClientMainAppSocket extends Thread {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private InetAddress address;
    private String ServerAddress;
    private String username;
    private static boolean connected = false;
    //private String ApplicationCommand;
    private Timer StartTime, ElapsedTime, ReminderTime;
    private Thread t;
    //project classes that will be used
    private ClientMainAppSocket clientSocket;
    private UnicastMessageClient unicastMessage;
    private MultiCastMessageClient multicastMessage;
    private BroadCastMessageClient broadcastMessage;

    public ClientMainAppSocket(String userName) {
        super("Client Remot");
        try {
            this.ServerAddress = Setting.APP_MAIN_SERVER_HOST_IP;
            this.username = userName;
            address = InetAddress.getByName(ServerAddress);
            socket = new Socket(ServerAddress, Setting.APP_MAIN_SOCKET_PORT);
            ProcessClientConnection();
        } catch (IOException ex) {
            Logger.getLogger(ClientMainAppSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void ProcessClientConnection() {
        try {
            input = new DataInputStream((socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());
            //output.write("start");
            Thread thread;
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        output.writeUTF("start#computer PC-1#192.168.1.10#17:10:00#" + username);
                        System.out.println("Connection success");
                        //output.write("Name:" + username);
                        output.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(ClientMainAppSocket.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            thread.start();

        } catch (UnknownHostException ex) {
            //System.out.println(ex.getMessage());
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
    }

    @Override
    public void run() {
        super.run();
        try {
            System.out.println("Connection success");
            while (true) {
                System.out.println("Start Listining");
                String ApplicationCommand = input.readUTF();
                if (ApplicationCommand == null) {
                    //do nothing
                }
                System.out.println("Recieved From server " + ApplicationCommand);
                if (ApplicationCommand.equals("end")) {

                } else if (ApplicationCommand.equals("connection_published")) {
                    //if server replay start then do nothing
                    System.out.println("Client Connected Successfully");
                    //output.write("connection_published");
                    //output.flush();
                    continue;
                } else if (ApplicationCommand.contains("unicast")) {
                    String[] data = ApplicationCommand.split("#");
                    System.out.println("Data sent [ " + data[0] + " , " + data[1] + " ] ");
                    UnicastMessageClient um = new UnicastMessageClient(data[1]);
                    //um.dispatchEvent(new WindowEvent(um, WindowEvent.WINDOW_CLOSING));
                    Thread t = new Thread(um);
                    t.start();
                    System.out.println("The server request Unicast Message");
                } /**
                 * **************Multicast requests********************
                 */
                else if (ApplicationCommand.contains("multicast")) {
                    String[] data = ApplicationCommand.split("#");
                    System.out.println("Data sent [ " + data[0] + " , " + data[1] + " ] ");
                    MultiCastMessageClient mulyiCastMessageClient = new MultiCastMessageClient(data[1]);
                    Thread t = new Thread(mulyiCastMessageClient);
                    t.start();
                    System.out.println("The server request MultiCast Message");
                } /**
                 * **************BroadCast requests********************
                 */
                else if (ApplicationCommand.contains("broadcast")) {
                    String[] data = ApplicationCommand.split("#");
                    System.out.println("Data sent [ " + data[0] + " , " + data[1] + " ] ");
                    //here all client PC will receive the message
                    BroadCastMessageClient broadCastMessageClient = new BroadCastMessageClient(data[1]);
                    Thread t = new Thread(broadCastMessageClient);
                    t.start();
                    System.out.println("The server request BroadCast Message");
                } /**
                 * **************Remote Desktop requests********************
                 */
                else if (ApplicationCommand.equals(Setting.MESSAGE_REMOTE_DESKTOP)) {
                    System.out.println("Receive Remote Desktop");
                    System.out.println("The server request remote desktop");
                    ClientInitiator ci = new ClientInitiator();
                    ci.start();

                }
            }
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
    }

    public void EndClient() {

        try {
            output.writeUTF("end#" + "computer PC-1#192.168.1.10#17:10:00#"); //Logger.getLogger(IMClient.class.getName()).log(Level.SEVERE, null, ex);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientMainAppSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendDataToServer(String data) {
        try {
            output.writeUTF(data + "#" + "computer PC-1#192.168.1.10#17:10:00#");
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientMainAppSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
