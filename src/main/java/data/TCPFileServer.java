package data;

import database.SQLiteJDBC;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPFileServer extends Thread {

    private static ArrayList<ThreadedServer> connectedClients = new ArrayList<ThreadedServer>();
    ;
    ServerSocket welcomeSocket;
    static int index = 1;

    public TCPFileServer() throws Exception {

        /*if (args.length == 0) {
            System.out.println("Please enter the server directory address as first argument while running from command line.");
        } else {*/
        System.out.println("Server started...");
        System.out.println("Waiting for connections...");
        welcomeSocket = new ServerSocket(Setting.APP_SHARED_FOLDER_PORT);
//connectedClients = new ArrayList<>();

        //}
    }

    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        while (true) {
            try {
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("Client with ID " + index + " connected from " + connectionSocket.getInetAddress().getHostName() + "...");
                ThreadedServer server = new ThreadedServer(connectionSocket, index, this);
                index++;
                connectedClients.add(server);
                server.start();
            } catch (IOException ex) {
                Logger.getLogger(TCPFileServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

class ThreadedServer extends Thread {

    int n;
    int m;
    String name, f, ch, fileData;
    String filename;
    Socket connectionSocket;
    int counter;
    String dirName = "/home/focal/Documents";
    private ArrayList<SharedFolders> serverFolders;
    private BufferedReader in;
    private InputStream inFromClient;
    private PrintWriter outPw;
    private OutputStream output;
    private ObjectOutputStream oout;
    private int size = 9022386;//default size
    private TCPFileServer sharingServer;
    private DataOutputStream bosr;

    public ThreadedServer(Socket s, int c, TCPFileServer server) {
        this.sharingServer = server;
        connectionSocket = s;
        counter = c;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            inFromClient = connectionSocket.getInputStream();
            outPw = new PrintWriter(connectionSocket.getOutputStream());
            output = connectionSocket.getOutputStream();
            oout = new ObjectOutputStream(output);
            oout.writeObject("Server says Hi!");
            //start loading all files
            //load all shared folders from server database
            LoadServerSharedFolders();
            File ff = new File(serverFolders.get(0).getFolderPath());
            ArrayList<String> names = new ArrayList<String>(Arrays.asList(ff.list()));
            int len = names.size();
            oout.writeObject(String.valueOf(names.size()));

            for (String name : names) {
                oout.writeObject(name);
            }
            while (true) {
                name = in.readLine();
                ch = name.substring(0, 1);
                if (ch.equals("*")) {
                    n = name.lastIndexOf("*");
                    filename = name.substring(1, n);
                    FileInputStream file = null;
                    BufferedInputStream bis = null;
                    boolean fileExists = true;
                    System.out.println("Request to download file " + filename + " recieved from " + connectionSocket.getInetAddress().getHostName() + "...");
                    //filename = dirName+"\\" + filename;
                    filename = dirName + "/" + filename;
                    try {
                        file = new FileInputStream(filename);
                        bis = new BufferedInputStream(file);
                    } catch (FileNotFoundException excep) {
                        fileExists = false;
                        System.out.println("FileNotFoundException:" + excep.getMessage());
                    }
                    if (fileExists) {
                        size = bis.available();
                        outPw.println("Success#SIZE:" + size);
                        outPw.flush();
                        System.out.println("File Size on server is : [ " + size + " ]");
                        System.out.println("Download begins");
                        sendBytes(bis, output, size);
                        System.out.println("Completed");
                        bis.close();
                        file.close();
                        oout.reset();
                    } else {
                        //oout = new ObjectOutputStream(output);
                        oout.writeObject("FileNotFound");
                        bis.close();
                        file.close();
                    }
                } else if (name.contains("END")) {
                    System.out.println("Client sharing exited ");
                    break;
                }
                System.out.println("Server Loop Completed");
            }
            System.out.println("Server outside the while loop ");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error happened on server :" + ex.getMessage());
        }
    }

    private void LoadServerSharedFolders() {
        //load all the shared folders from the database
        System.out.println("Server fetch shared folders ");
        SQLiteJDBC db = new SQLiteJDBC();
        serverFolders = db.getAllSharedFolder();
        System.out.println("Number of Shared Folders are  [ " + serverFolders.size() + " ] ");

    }

    private static void sendBytes(BufferedInputStream in, OutputStream out, int size) throws Exception {
        byte[] data = new byte[size];
        int bytes = 0;
        int c = in.read(data, 0, data.length);
        System.out.println("Buffer data are : [ " + c + " ] ");
        out.write(data, 0, c);
        out.flush();

    }
}
