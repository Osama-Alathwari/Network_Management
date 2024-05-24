/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.File;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author root
 */
public class SharedFolders implements Serializable{
    private int SharedFolderID;
    private String FolderName;
    private String FolderPath;
    private String FolderDescription;
    private String   FolderAdditionDate;
    private File[] files;
    public SharedFolders() {
    }

    public SharedFolders(int SharedFolderID, String FolderName, String FolderPath, String FolderDescription, String FolderAdditionDate) {
        this.SharedFolderID = SharedFolderID;
        this.FolderName = FolderName;
        this.FolderPath = FolderPath;
        this.FolderDescription = FolderDescription;
        this.FolderAdditionDate = FolderAdditionDate;
        fetchAllFoldersFiles();
    }

    public SharedFolders(String FolderName, String FolderPath, String FolderDescription) {
        this.FolderName = FolderName;
        this.FolderPath = FolderPath;
        this.FolderDescription = FolderDescription;
        FolderAdditionDate=new Date(System.currentTimeMillis()).toString();
        
    }


    public void fetchAllFoldersFiles() {
        System.out.println("Folder Name : "+FolderName);
        File f=new File(FolderPath);
        File[]fil=f.listFiles();
        files=fil;
        System.out.println("The whole files are : "+files.length);
        System.out.println("=====================================================================");
        ArrayList<String> list=new ArrayList<>(listFilesUsingJavaIO(FolderPath));
        Collections.sort(list);
        System.out.println("All Files are "+list.toString());
        System.out.println("=====================================================================");
    }
    public boolean isDirectory(){
        return (!FolderName.contains("."));
    }
    public SharedFolders(int SharedFolderID, String FolderName, String FolderPath, String FolderDescription) {
        this.SharedFolderID = SharedFolderID;
        this.FolderName = FolderName;
        this.FolderPath = FolderPath;
        this.FolderDescription = FolderDescription;
        this.FolderAdditionDate=new Date(System.currentTimeMillis()).toString();
    }

    public SharedFolders(String FolderName, String FolderPath, String FolderDescription, String FolderAdditionDate) {
        this.FolderName = FolderName;
        this.FolderPath = FolderPath;
        this.FolderDescription = FolderDescription;
        this.FolderAdditionDate = FolderAdditionDate;
    }
    
    public void setFolderFiles(File[] files){
        this.files=files;
    }

    public File[] getFiles() {
        return files;
    }

    
    public int getSharedFolderID() {
        return SharedFolderID;
    }

    public void setSharedFolderID(int SharedFolderID) {
        this.SharedFolderID = SharedFolderID;
    }

    public String getFolderName() {
        return FolderName;
    }

    public void setFolderName(String FolderName) {
        this.FolderName = FolderName;
    }

    public String getFolderPath() {
        return FolderPath;
    }

    public void setFolderPath(String FolderPath) {
        this.FolderPath = FolderPath;
    }

    public String getFolderDescription() {
        return FolderDescription;
    }

    public void setFolderDescription(String FolderDescription) {
        this.FolderDescription = FolderDescription;
    }

    public String getFolderAdditionDate() {
        return FolderAdditionDate;
    }

    public void setFolderAdditionDate(String FolderAdditionDate) {
        this.FolderAdditionDate = FolderAdditionDate;
    }
    
    public Set<String> listFilesUsingJavaIO(String dir) {
    return Stream.of(new File(dir).listFiles())
      .filter(file -> !file.isDirectory())
      .map(File::getName)
      .collect(Collectors.toSet());
}
    
}
