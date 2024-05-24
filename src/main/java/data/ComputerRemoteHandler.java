/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import Components.ClientRemotePCView;
import Server.Remote.ClientHandler;
import Server.Remote.ServerInitiator;
import java.net.Socket;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

/**
 *
 * @author root
 */
public class ComputerRemoteHandler extends Thread{
    private Socket client;
    private ClientHandler clientHandler;
    private ClientRemotePCView viewPanel;
    private ServerInitiator remoteInitiator;
    private static int clientPCIndex=1;
    private int index;
    private boolean isAdded=false;
    
    public ComputerRemoteHandler(Socket client, ServerInitiator remoteInitiator) {
        super("HandleAllComputerRemote-Class");
        this.client = client;
        this.remoteInitiator = remoteInitiator;
        viewPanel=new ClientRemotePCView();
    }
    
    public JPanel getRemoteDisplayPanel(){
        return viewPanel;
    }

    public boolean isIsAdded() {
        return isAdded;
    }

    public void setIsAdded(boolean isAdded) {
        this.isAdded = isAdded;
    }
    
    @Override
    public void run() {
        super.run(); 
        /**
         * Now start displaying the content inside the ClientRemotePCView class
         */
        clientHandler=new ClientHandler(client, viewPanel);
        while(true){
            //loop untile the end
        }
    }
    
    
}
