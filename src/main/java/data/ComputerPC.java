/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author root
 */
public class ComputerPC implements Serializable{
    private int ID;
    private String PCID;
    private String IP;
    private String PCName;
    private String PCStatus;
    private String PCDescriptions;
    private String AddedDate;

    public ComputerPC(int ID, String PCID, String IP, String PCName, String PCDescriptions, String AddedDate) {
        this.ID = ID;
        this.PCID = PCID;
        this.IP = IP;
        this.PCName = PCName;
        this.PCStatus = "Running";
        this.PCDescriptions = PCDescriptions;
        this.AddedDate = AddedDate;
    }
    
    public ComputerPC(String PCID, String IP, String PCName, String PCStatus, String PCDescriptions) {
        this.PCID = PCID;
        this.IP = IP;
        this.PCName = PCName;
        this.PCStatus = PCStatus;
        this.PCDescriptions = PCDescriptions;
        this.AddedDate =new Date(System.currentTimeMillis()).toString();
    }

    public ComputerPC(String IP, String PCName, String PCStatus, String PCDescriptions) {
        this.IP = IP;
        this.PCName = PCName;
        this.PCStatus = PCStatus;
        this.PCDescriptions = PCDescriptions;
        this.AddedDate =new Date(System.currentTimeMillis()).toString();
    }

    public ComputerPC() {
        
    }

    public String getPCID() {
        return PCID;
    }

    public void setPCID(String PCID) {
        this.PCID = PCID;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPCName() {
        return PCName;
    }

    public void setPCName(String PCName) {
        this.PCName = PCName;
    }

    public String getPCStatus() {
        return PCStatus;
    }

    public void setPCStatus(String PCStatus) {
        this.PCStatus = PCStatus;
    }

    public String getPCDescriptions() {
        return PCDescriptions;
    }

    public void setPCDescriptions(String PCDescriptions) {
        this.PCDescriptions = PCDescriptions;
    }

    public String getAdditionDate() {
        return AddedDate;
    }

    @Override
    public String toString() {
        return "ComputerPC{" + "ID=" + ID + ", PCID=" + PCID + ", IP=" + IP + ", PCName=" + PCName + ", PCStatus=" + PCStatus + ", PCDescriptions=" + PCDescriptions + ", AddedDate=" + AddedDate + '}';
    }
    
    
}
