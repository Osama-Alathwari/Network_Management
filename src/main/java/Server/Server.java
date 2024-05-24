package Server;

import data.Setting;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread {

    ServerSocket serverSocket;
    static ArrayList<ServerConnectionThread> usersConnection = new ArrayList<ServerConnectionThread>();
    public static Map<String, ServerConnectionThread> allPC;
    static int index = 1;

    public ArrayList<ServerConnectionThread> getUsersConnection() {
        return usersConnection;
    }

    public void setUsersConnection(ArrayList<ServerConnectionThread> usersConnection) {
        this.usersConnection = usersConnection;
    }

    public Server() {
        super("Main server");
        allPC = new HashMap<String, ServerConnectionThread>();
        try {
            serverSocket = new ServerSocket(Setting.APP_MAIN_SOCKET_PORT);
            System.out.println("Starting Network Server....");
            System.out.println("####################LOGS######################");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void run() {
        super.run();
        System.out.println("The server main thread starts");
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                String ip = clientSocket.getInetAddress().toString();
                System.out.println("Client connected wirh IP [" + ip.replace("/", "") + " ]");
                ServerConnectionThread conn = new ServerConnectionThread(clientSocket, this);
                usersConnection.add(conn);
                allPC.put(ip + index++, conn);
                conn.start();
                //add the connected client to the hashmap
                //allPC.put(""+ip, conn);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public static ArrayList<ServerConnectionThread> getAllClients() {
        return usersConnection;
    }

    public boolean isEmpty() {
        return usersConnection.isEmpty();
    }

    public void sendDataToTargets(String data, int[] targetClient) {

        for (int i = 0; i < targetClient.length; i++) {
            ServerConnectionThread serverConnTemp = usersConnection.get((targetClient[i] - 1));
            serverConnTemp.sendData(data);
            System.out.println("Data Sent Successfully");
            //list.add(serverConnTemp.getUsername());
        }
    }

    public void sendData(String data, int number) {
        ServerConnectionThread serverConnTemp = usersConnection.get(number - 1);
        serverConnTemp.sendData(data);
        System.out.println("Data Sent Successfully");

    }

    public void sendData(String data) {
        for (int i = 0; i < usersConnection.size(); i++) {
            ServerConnectionThread serverConnTemp = usersConnection.get(i);
            serverConnTemp.sendData(data);
            System.out.println("Data Sent Successfully");
            //list.add(serverConnTemp.getUsername());
        }
        /*new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
*/
    }

    int getSize() {
        return usersConnection.size();
    }

    /**
     * *******************************************************************************************
     */
    /**
     * ***************** Operations based on maps for server
     * ************************************
     */
    /**
     * *******************************************************************************************
     */
    public void sendMessageData(String data, String targetClient) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerConnectionThread serverConnTemp = allPC.get(targetClient);
                serverConnTemp.sendData(data);
                System.out.println("Data Sent to Computer IP [ " + targetClient + " ] Successfully");
            }
        }).start();

    }

    public int getNumberOfPC() {
        return usersConnection.size();
    }

    public Map<String, ServerConnectionThread> getAllClientsMap() {
        return allPC;
    }

    public boolean isEmptyMap() {
        return allPC.isEmpty();
    }

    public int getNumberOfPCMap() {
        return allPC.size();
    }

    int getMapSize() {
        return allPC.size();
    }

}
