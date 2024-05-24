package Server;

import data.ComputerPC;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Received data has the format
 * [ACTION#COMPUTER_NAME#CONNECTION_TIME#CLIENT_NAME] The specification of data
 * are the following : [ 1 ] - ACTION :- is the communication commands between
 * the master server application and the client application The following are
 * the actions that have meaning to the system: [ a ] - start : This action is
 * the main event between the admin program and the client pc that mean the
 * computer is already started with the server., [ b ] - unicast : This action
 * will allow the client PC to receive a unicast message from the admin., [ c ]
 * - multicast : This action will allow the client PC to receive a multicast
 * message from the admin., [ d ] - broadcast : This action will allow the
 * client PC to receive a broadcast message from the admin., [ e ] -
 * remote_desktop : This action will allow the admin to display the client
 * desktop with action., [ f ] - remote_app : This action will display the
 * desktop of the client and have the chance to open and use program., [ g ] -
 * share_file : This action will allow the client to receive a shared file with
 * the admin., [ h ] - network_monitor : This action will get the whole
 * information of the client pc over the network., [ i ] - network_time : This
 * action will show the timing of the current client pc., [ j ] - end : This
 * action mean end the session of the client pc over network., [ k ] - close_pc
 * : This action will make the client pc closed., [ l ] - shutdown : This action
 * will make the client pc shut down., [ m ] - restart : This action will
 * restart the client pc., [ n ] - cancel : This action will cancel the
 * operation by the admin app., [ o ] - end_session : This action will make the
 * client to manual end the current session.
 */
public class ServerConnectionThread extends Thread {

    private Socket clientSocket;
    private Server server;
    private DataInputStream input;
    private DataOutputStream output;
    private String username;
    private ComputerPC clientPC;

    //public static Map<String,ServerConnectionThread> allPC=new HashMap<String, ServerConnectionThread>();
    public ServerConnectionThread(Socket clientSocket, Server server) {
        super("user Thread");
        this.clientSocket = clientSocket;
        this.server = server;
        SaveComputerData();

    }

    public void run() {
        try {
            input = new DataInputStream((clientSocket.getInputStream()));
            output = new DataOutputStream(clientSocket.getOutputStream());
            while (true) {
                String recievedData = input.readUTF();
                //System.out.println("Client Connected : ");
                if (recievedData == null) {
                    //if null do nothing
                } else if (recievedData.contains("start")) {
                    //if command end make computer on suspend
                    System.out.println("Client Connected : " + recievedData);
                } else if (recievedData.contains("end")) {
                    System.out.println("Client send Finish : " + recievedData);
                    EndConnection();
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void sendData(String data) {
        try {
            output.writeUTF(data);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerConnectionThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendForAllClients(String message) {
        for (int i = 0; i < server.usersConnection.size(); i++) {
            ServerConnectionThread serverConnTemp = server.usersConnection.get(i);
            //serverConnTemp.sendMsg("pubmsg#" + username + "#" + message);
            System.out.println("public chat From " + this.username + " To " + serverConnTemp.username + " message:" + message);
        }
    }

    private void sendForAClient(String message, String reciverName) {
        for (int i = 0; i < server.usersConnection.size(); i++) {
            ServerConnectionThread serverConnTemp = server.usersConnection.get(i);
            if (serverConnTemp.username.equals(reciverName)) {
                //serverConnTemp.sendMsg("primsg#" + username + "#" + message);
                System.out.println("private chat From " + this.username + " To " + serverConnTemp.username + " message:" + message);
                break;
            }
        }
    }

    private void sendForAGroup(String message, String reciverName) {
        for (int i = 0; i < server.usersConnection.size(); i++) {
            ServerConnectionThread serverConnTemp = server.usersConnection.get(i);
            if (serverConnTemp.username.equals(reciverName)) {
                //serverConnTemp.sendMsg("groupmsg#" + username + "#" + message);
                System.out.println("group chat From " + this.username + " To " + serverConnTemp.username + " message:" + message);
                break;
            }
        }
    }

    public void sendMsg(String message, String target) {

        for (int i = 0; i < server.usersConnection.size(); i++) {
            ServerConnectionThread serverConnTemp = server.usersConnection.get(i);
            serverConnTemp.openRemoteDesktop();

        }

    }

    public void openRemoteDesktop() {
        try {
            output.writeUTF("remote_desktop");
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerConnectionThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendUnicast(String message) {
        try {
            output.writeUTF(message);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerConnectionThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendBroadCast(String message) {
        try {
            output.writeUTF(message);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerConnectionThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendMultiCast(String message) {
        try {
            output.writeUTF(message);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerConnectionThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getAllClients() {
        String clients = "clients#";
        for (int i = 0; i < server.usersConnection.size(); i++) {

            ServerConnectionThread serverConnTemp = server.usersConnection.get(i);
            clients += serverConnTemp.username;
            if (i != server.usersConnection.size() - 1) {
                clients += "#";
            }
        }
        //this.sendMsg(clients);
        for (int i = 0; i < server.usersConnection.size(); i++) {

            ServerConnectionThread serverConnTemp = server.usersConnection.get(i);
            //serverConnTemp.sendMsg(clients);
        }

    }
    private void EndConnection(){
        try {
            input.close();
            output.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ServerConnectionThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ComputerPC getClientPC() {
        return clientPC;
    }

    public void setClientPC(ComputerPC clientPC) {
        this.clientPC = clientPC;
    }

    private void SaveComputerData() {
        clientPC = new ComputerPC();
        String ip = this.clientSocket.getInetAddress().toString().replace("/", "");
        clientPC.setIP(ip);
        System.out.println("Client IP : " + ip);
        clientPC.setPCStatus("Connected");
        //clientPC=new ComputerPC();
        int first = ip.lastIndexOf('.');
        int last = ip.length();
        String pcNumber = ip.substring(first + 1, last);
        System.out.println("Last IP Index = [ " + pcNumber + " ] ");
        System.out.println("The whole IP is : " + ip.substring(0, ip.lastIndexOf('.') + 1));
        /*Integer.parseInt(String.valueOf(pcNumber));
        System.out.println("IP Last Digit  [ "+pcNumber+" ] ");*/
        //get the computer number as the last IP number ex. 192.168.1.(1)
        clientPC.setPCID(pcNumber);
        System.out.println(clientPC);
    }

}
