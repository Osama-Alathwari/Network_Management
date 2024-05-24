/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import database.SQLiteJDBC;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class ServerSharedFolder extends Thread {

    private ServerSocket sharedFolderSocket;
    private String SharedFolder;
    private ArrayList<ServerSharedFolderThread> clients;

    public ServerSharedFolder() {
        try {
            sharedFolderSocket = new ServerSocket(Setting.APP_SHARED_FOLDER_PORT);
            clients = new ArrayList<>();
            System.out.println("Shared Folder Server is Running : ");

        } catch (IOException ex) {
            System.out.println("Error creating Shared Folder Server : " + ex.getMessage());
            Logger.getLogger(ServerSharedFolder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        System.out.println("Waiting for Clients to connect .........");
        while (true) {
            try {
                Socket connectionSocket = sharedFolderSocket.accept();
                System.out.println("One Client connect to shared folders ........");
                ServerSharedFolderThread cl = new ServerSharedFolderThread(connectionSocket);
                cl.start();
                clients.add(cl);
            } catch (IOException ex) {
                Logger.getLogger(ServerSharedFolder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

class ServerSharedFolderThread extends Thread {

    int n;
    int m;
    private String name, f, ch, fileData;
    private String filename;
    private Socket connectionSocket;
    private int counter;
    private String dirName;

    public ServerSharedFolderThread(Socket s) {
        connectionSocket = s;
    }
    private ArrayList<SharedFolders> serverFolders;

    private void LoadServerSharedFolders() {
        //load all the shared folders from the database
        System.out.println("Server fetch shared folders ");
        SQLiteJDBC db = new SQLiteJDBC();
        serverFolders = db.getAllSharedFolder();
        System.out.println("Number of Shared Folders are  [ " + serverFolders.size() + " ] ");
        
    }
    private BufferedReader in;
    private InputStream inFromClient;
    private PrintWriter outPw;
    private OutputStream output;
    private ObjectOutputStream oout;

    public void run() {
        try {
            while (true) {
                in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                inFromClient = connectionSocket.getInputStream();
                outPw = new PrintWriter(connectionSocket.getOutputStream());
                output = connectionSocket.getOutputStream();
                oout = new ObjectOutputStream(output);
                oout.writeObject("Success");
                //oout.flush();
                ///*****************************************************
                //load all shared folders from server database
                LoadServerSharedFolders();
                //get all content of each folders into an array of string
                System.out.println("Server Start sending object to client");
                //write the object into client
                oout.writeObject(serverFolders);
                System.out.println("The object written to client successfully");
                ///*****************************************************
                readClientResponse();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void sendBytes(BufferedInputStream in, OutputStream out) throws Exception {
        int size = 9022386;
        byte[] data = new byte[size];
        int bytes = 0;
        int c = in.read(data, 0, data.length);
        out.write(data, 0, c);
        out.flush();
    }

    private void writeData(String file) throws Exception {
        OutputStream o = null;
        InputStream in = null;
        try {
            o = new FileOutputStream(file);
            in = connectionSocket.getInputStream();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. ");
        }

        byte[] bytes = new byte[16 * 1024];

        int count;
        while ((count = in.read(bytes)) > 0) {
            o.write(bytes, 0, count);
        }

        o.close();
        in.close();
        //connectionSocket.close();
        //serverSocket.close();
    }

    private void readClientResponse() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    name = in.readLine();
                    ch = name.substring(0, 1);

                    if (ch.equals("*")) {
                        n = name.lastIndexOf("*");
                        filename = name.substring(1, n);
                        FileInputStream file = null;
                        BufferedInputStream bis = null;
                        boolean fileExists = true;
                        System.out.println("Request to download file" + filename + " recieved from " + connectionSocket.getInetAddress().getHostName() + "...");
                        //filename = filename.trim();
                        try {
                            //String getProperty = System.getProperty("user.home");
                            String getProperty = "/home/focal";
                            //File defaultDir = new File(getProperty + "/"+filename);
                            //file = new FileInputStream(filename);
                            file = new FileInputStream(getProperty + "/" + filename);
                            System.out.println("File full path is : " + getProperty + "/" + filename);
                            bis = new BufferedInputStream(file);
                        } catch (FileNotFoundException excep) {
                            fileExists = false;
                            System.out.println("FileNotFoundException:" + excep.getMessage());
                        }
                        if (fileExists) {
                            try {
                                System.out.println("File is Exist");
                                oout = new ObjectOutputStream(output);
                                oout.writeObject("Success");
                                System.out.println("Download begins");
                                sendBytes(bis, output);
                                System.out.println("Completed");
                                bis.close();
                                file.close();
                                oout.close();
                                output.close();
                            } catch (IOException ex) {
                                System.out.println("Error : " + ex.getMessage());
                                Logger.getLogger(ServerSharedFolderThread.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (Exception ex) {
                                System.out.println("Error : " + ex.getMessage());
                                Logger.getLogger(ServerSharedFolderThread.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            try {
                                oout = new ObjectOutputStream(output);
                                oout.writeObject("FileNotFound");
                                //oout.flush();
                                bis.close();
                                file.close();
                                oout.close();
                                output.close();
                            } catch (IOException ex) {
                                System.out.println("Error : " + ex.getMessage());
                                Logger.getLogger(ServerSharedFolderThread.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    /*else {
                        try {
                            boolean complete = true;
                            System.out.println("2-Request to upload file " + name + " recieved from " + connectionSocket.getInetAddress().getHostName() + "...");
                            File directory = new File(dirName);
                            if (!directory.exists()) {
                                System.out.println("Dir made");
                                directory.mkdir();
                            }

                            int size = 9022386;
                            byte[] data = new byte[size];
                            File fc = new File(directory, name);
                            FileOutputStream fileOut = new FileOutputStream(fc);
                            DataOutputStream dataOut = new DataOutputStream(fileOut);

                            while (complete) {
                                m = inFromClient.read(data, 0, data.length);
                                if (m == -1) {
                                    complete = false;
                                    System.out.println("Completed");
                                } else {
                                    dataOut.write(data, 0, m);
                                    dataOut.flush();
                                }
                            }
                            fileOut.close();
                        } catch (Exception exc) {
                            System.out.println("Error : " + exc.getMessage());
                            System.out.println(exc.getMessage());
                        }
                    }*/
                } catch (IOException ex) {
                    System.out.println("Error : " + ex.getMessage());
                    Logger.getLogger(ServerSharedFolderThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
        System.out.println("Read client response thread started .... ");
    }

}
