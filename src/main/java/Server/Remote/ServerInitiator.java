package Server.Remote;

import Components.AllComputerPanel;
import data.ComputerRemoteHandler;
import data.Setting;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;

/**
 * This is the entry class of the server
 */
public class ServerInitiator extends Thread {

    private static ArrayList<ComputerRemoteHandler> clientRemotes;
    //connected clients' screens
    private ServerSocket remoteServerSocket;
    private AllComputerPanel centerPanel;
    private static int index=1;
    public ServerInitiator(AllComputerPanel centerPanel) {
        super("Remote-Server-Socket");
        try {
            this.centerPanel=centerPanel;
            clientRemotes = new ArrayList<>();
            remoteServerSocket = new ServerSocket(Setting.APP_REMOTE_DESKTOP_PORT);
            System.out.println("Remote Server Started successfully ...");
        } catch (IOException ex) {
            System.out.println("Error at Remote Server class :- "+ex.getMessage());
            Logger.getLogger(ServerInitiator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ArrayList<ComputerRemoteHandler> getAllClientRemotes() {
        return clientRemotes;
    }
    
    public void drawGUI() {
        centerPanel.setVisible(true);
        centerPanel.show();
    }

    @Override
    public void run() {
        super.run();
        try {
            //Show Server GUI
            drawGUI();
            //Listen to server port and accept clients connections
            while (true) {
                //here process all the current clients connected to server
                Socket client = remoteServerSocket.accept();
                System.out.println("New client Connected to the server");
                ComputerRemoteHandler view = new ComputerRemoteHandler(client, this);
                clientRemotes.add(view);
                centerPanel.addItemToPanel(view.getRemoteDisplayPanel(),index++);
                view.start();
            }
        } catch (IOException ex) {
            System.out.println("Error at Remote Server Thread->run() Method  :- "+ex.getMessage());
        }
    }


}
