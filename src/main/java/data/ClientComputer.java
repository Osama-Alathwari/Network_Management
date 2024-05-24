/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import Components.ClientRemotePCView;
import Client.ClientMessages.BroadCastMessageClient;
import Client.ClientMessages.MultiCastMessageClient;
import Client.ClientMessages.UnicastMessageClient;
import Server.Remote.ServerInitiator;

/**
 *
 * @author root
 */
public class ClientComputer {
    private String Name;
    private String Status;
    private ComputerPC ClientComputer;
    private UnicastMessageClient unicastMessageClient;
    private BroadCastMessageClient broadCastMessageClient;
    private MultiCastMessageClient multiCastMessageClient;
    private ClientRemotePCView clientComputerPCViewPanel;
    private ServerInitiator serverRemoteInitiator;

    public ClientComputer(String Name, String Status) {
        this.Name = Name;
        this.Status = Status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public ComputerPC getClientComputer() {
        return ClientComputer;
    }

    public void createClientComputer(String PCID,String  PCIP,String  PCName,String PCStatus,String  PCDescription) {
        this.ClientComputer = new ComputerPC(PCID, PCIP, PCName,PCStatus, PCDescription);
    }

    public void sendUnicastMessageClient() {
        //unicastMessageClient.;
    }


    public MultiCastMessageClient getMultiCastMessageClient() {
        return multiCastMessageClient;
    }


    public ClientRemotePCView getClientComputerPCViewPanel() {
        return clientComputerPCViewPanel;
    }

    public void createClientComputerPCViewPanel() {
        this.clientComputerPCViewPanel = new ClientRemotePCView();
        
    }
/*
 *This function for the RemoteDesktop object Operations 
 *
 */
    public ServerInitiator getServerRemoteInitiator() {
        return serverRemoteInitiator;
    }

    public void createServerRemoteInitiator() {
        //this.serverRemoteInitiator = new ServerInitiator();
    }
    public void startServerRemotePreview() {
        this.serverRemoteInitiator.start();
    }
    public void getServerRemotePreviewPane() {
        //this.serverRemoteInitiator.getDesktopPreviewPanel();
    }
    
    
    
    
}
