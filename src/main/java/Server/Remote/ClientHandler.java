package Server.Remote;

import Components.ClientRemotePCView;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JPanel;

public class ClientHandler extends Thread {

    private Socket cSocket = null;
    private JPanel cPanel = new JPanel();
    private ClientRemotePCView remotePCView;

    public ClientHandler(Socket cSocket, ClientRemotePCView remotePCView) {
        this.remotePCView = remotePCView;
        this.cSocket = cSocket;
        this.cPanel = remotePCView.getRemoteDisplayPanel();
        start();
    }

    public void run() {

        //used to represent client screen size
        Rectangle clientScreenDim = null;
        //Used to read screenshots and client screen dimension
        ObjectInputStream ois = null;
        try {
            //Read client screen dimension
            ois = new ObjectInputStream(cSocket.getInputStream());
            clientScreenDim = (Rectangle) ois.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        //Start recieveing screenshots
        ClientScreenReciever cr=new ClientScreenReciever(ois, cPanel);
        //Start sending events to the client
        ClientCommandsSender ccs=new ClientCommandsSender(cSocket, cPanel, clientScreenDim);
    }

}
