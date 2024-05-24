/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import data.ComputerPC;
import data.Configuration;
import data.SharedFolders;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sqlite.SQLiteDataSource;

public class SQLiteJDBC {

    private Connection connection;
    private Statement stmt;
    private ResultSet rs;
    PreparedStatement pstmt;
    private static final String shared_folder_sql = "CREATE TABLE shared_folder "
            + "(ID INTEGER PRIMARY KEY autoincrement,"
            + " Folder_Name           CHAR(50)    NOT NULL, "
            + " Folder_Path           CHAR(50)    NOT NULL, "
            + " Descriptions        TEXT     NOT NULL, "
            + " date_added     TEXT NOT NULL)";
    private static String all_folder_sql = "SELECT * FROM shared_folder";
    private static String add_folder_sql = "INSERT INTO shared_folder (Folder_Name, Folder_Path, Descriptions, date_added) VALUES (?, ?, ?, ?)";
    private static String update_folder_sql = "UPDATE shared_folder set Folder_Name=? , Folder_Path=? , Descriptions=?, date_added=?  WHERE ID= ? ";
    private static String delete_folder_sql = "DELETE FROM shared_folder WHERE ID=? ";

    private static final String network_pc_sql = "CREATE TABLE network_pc "
            + "(ID INTEGER PRIMARY KEY autoincrement,"
            + " PC_NAME           CHAR(50)    NOT NULL, "
            + " PC_IP           CHAR(50)    NOT NULL, "
            + " PC_Number           CHAR(50)    NOT NULL, "
            + " Descriptions        TEXT     NOT NULL, "
            + " date_added     TEXT NOT NULL)";
    private static String all_pc_sql = "SELECT * FROM network_pc";
    private static String add_pc_sql = "INSERT INTO network_pc (PC_NAME, PC_IP, PC_Number, Descriptions, date_added) VALUES (?, ?, ?, ?, ?)";
    private static String update_pc_sql = "UPDATE network_pc set PC_NAME=? , PC_IP=? , PC_Number=? , Descriptions=? , date_added=?  WHERE ID= ? ";
    private static String delete_pc_sql = "DELETE FROM network_pc WHERE ID=? ";
    private ArrayList<ComputerPC> allpc;
    private String url = "jdbc:sqlite://home/focal/NetBeansProjects/NetworkManagement/network_management.sqlite";
    private String db_url = "jdbc:sqlite:" + Configuration.getCurrentPath() + "/network_management.sqlite";
    
    //SQLiteDataSource ds=new SQLiteDataSource();
    public SQLiteJDBC() {
        Connection c = null;
        try {
            //String myDriver = "org.gjt.mm.mysql.Driver";
            //String myUrl = "jdbc:mysql://localhost:3306/network_data";
            //Class.forName(myDriver);
            //connection = DriverManager.getConnection(myUrl, "root", "");
            Class.forName("org.sqlite.JDBC");
            //DriverManager.registerDriver(DriverManager.getDriver("org.sqlite.JDBC"));
            connection = DriverManager.getConnection(url);
            stmt = connection.createStatement();
            System.out.println("Connection Published successfully");

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Opened database successfully");
    }

    /**
     * Shared Folder operation functions.
     */
    public void createSharedFolderTable() {
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(shared_folder_sql);
            System.out.println("The Shared Folder PC table created successfully");
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }

    public boolean addSharedFolder(SharedFolders folder) {
        boolean isAdded = false;
        try {
            pstmt = connection.prepareStatement(add_folder_sql);

            pstmt.setString(1, folder.getFolderName());
            pstmt.setString(2, folder.getFolderPath());
            pstmt.setString(3, folder.getFolderDescription());
            pstmt.setString(4, folder.getFolderAdditionDate().toString());
            pstmt.executeUpdate();
            System.out.println("Data Update Success");
            isAdded = true;
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isAdded;
    }

    public boolean updateSharedFolder(int id, SharedFolders folder) {
        boolean isUpdated = false;
        try {
            pstmt = connection.prepareStatement(update_folder_sql);

            pstmt.setString(1, folder.getFolderName());
            pstmt.setString(2, folder.getFolderPath());
            pstmt.setString(3, folder.getFolderDescription());
            pstmt.setString(4, folder.getFolderAdditionDate().toString());
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
            System.out.println("Data Update Success");
            isUpdated = true;
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isUpdated;
    }

    public boolean deleteSharedFolder(int id) {
        boolean isDeleted = false;
        try {
            pstmt = connection.prepareStatement(delete_folder_sql);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
            System.out.println("Data Deleted Success");
            isDeleted = true;
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isDeleted;
    }

    public ArrayList<SharedFolders> getAllSharedFolder() {
        ArrayList<SharedFolders> allFolder = new ArrayList<>();
        try {
            rs = stmt.executeQuery(all_folder_sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String path = rs.getString(3);
                String desc = rs.getString(4);
                String date = rs.getString(5);
                allFolder.add(new SharedFolders(id, name, path, desc, date));
            }
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allFolder;
    }

    public void createNEtworkPCTable() {
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(network_pc_sql);
            System.out.println("The Network PC table created successfully");
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }

    public boolean addNetworkPC(ComputerPC pc) {
        boolean isAdded = false;
        try {
            pstmt = connection.prepareStatement(add_pc_sql);

            pstmt.setString(1, pc.getPCName());
            pstmt.setString(2, pc.getIP());
            pstmt.setString(3, pc.getPCID());
            pstmt.setString(4, pc.getPCDescriptions());
            pstmt.setString(5, pc.getAdditionDate());
            pstmt.executeUpdate();
            System.out.println("Data Update Success");
            isAdded = true;
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isAdded;
    }

    public boolean updateNetworkPC(int id, ComputerPC pc) {
        boolean isUpdated = false;
        try {
            pstmt = connection.prepareStatement(update_pc_sql);

            pstmt.setString(1, pc.getPCName());
            pstmt.setString(2, pc.getIP());
            pstmt.setString(3, pc.getPCID());
            pstmt.setString(4, pc.getPCDescriptions());
            pstmt.setString(5, pc.getAdditionDate());
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            System.out.println("Data Update Success");
            isUpdated = true;
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isUpdated;
    }

    public boolean deleteNetworkPC(int id) {
        boolean isDeleted = false;
        try {
            pstmt = connection.prepareStatement(delete_pc_sql);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
            System.out.println("Data Deleted Success");
            isDeleted = true;
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isDeleted;
    }

    ArrayList<ComputerPC> allPC;

    public ArrayList<ComputerPC> getAllNetworkPC() {
        allPC = new ArrayList<>();
        try {
            rs = stmt.executeQuery(all_pc_sql);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("PC_NAME");
                String ip = rs.getString("PC_IP");
                String pcid = rs.getString("PC_Number");
                String desc = rs.getString("Descriptions");
                String date = rs.getString("date_added");
                allPC.add(new ComputerPC(id, name, ip, pcid, desc, date));
            }
        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
            Logger.getLogger(SQLiteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allpc;
    }/*
    public static void main(String[] args) {
        SQLiteJDBC db=new SQLiteJDBC();
        ArrayList<SharedFolders> f=db.getAllSharedFolder();
        System.out.println("Number of shared folders Are : "+f.size());
    }*/

}
