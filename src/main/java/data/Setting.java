/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.net.InetAddress;

/**
 *
 * @author root
 */
public class Setting {
    public static final int APP_MAIN_SOCKET_PORT=5000;
    public static final int APP_REMOTE_DESKTOP_PORT=5100;
    public static final int APP_UNICAST_MESSAGE_PORT=5200;
    public static final int APP_MULTICAST_MEESSAGE_PORT=5300;
    public static final int APP_BROADCAST_MESSAGE_PORT=5400;
    public static final int APP_MAIN_BROADCAST_PORT=5500;
    public static final int APP_MAIN_MULTICAST_PORT=5600;
    public static final int APP_NETWORK_TIME_PORT=5700;
    public static final int APP_SHARED_FOLDER_PORT=5800;
    
    //string constants
    public static final String APP_MAIN_SERVER_HOST="localhost";
    public static final String APP_BROADCAST_SERVER_HOST="255.255.255.255";
    public static final String APP_MAIN_SERVER_HOST_IP="127.0.0.1";
    public static String APP_UNICAST_MESSAGE_HOST="127.0.0.1";
    static String APP_MULTICAST_MEESSAGE_HOST="224.0.0.1";
    public static final String APP_SETTING_FILE=".netmanagersetting";
    
    //Navigation Menu Item Names
    public static final String APP_NAVIGATION_MENU_TITLE_REMOTE_DESKTOP="Remote Desktop";
    public static final String APP_NAVIGATION_MENU_TITLE_REMOTE_APP="Remote App";
    public static final String APP_NAVIGATION_MENU_TITLE_MESSAGE="Message";
    public static final String APP_NAVIGATION_MENU_TITLE_UNICAST="UniCast";
    public static final String APP_NAVIGATION_MENU_TITLE_MULTICAST="MultiCast";
    public static final String APP_NAVIGATION_MENU_TITLE_BROADCAST="BroadCast";
    public static final String APP_NAVIGATION_MENU_TITLE_MAINTENANCE="Maintenance";
    public static final String APP_NAVIGATION_MENU_TITLE_NETWORK_MONITOR="Network Monitor";
    public static final String APP_NAVIGATION_MENU_TITLE_NETWORK_TIME="Network Time";
    public static final String APP_NAVIGATION_MENU_TITLE_NETWORK_SUPPORT="NEtwork SUpport";
    public static final String APP_NAVIGATION_MENU_TITLE_HELP="HElp";
    
    //create method that fetch the network data from database
    public static String APP_NAVIGATION_MENU_TITLE_SHARING="Sharing";
    public static String APP_NAVIGATION_MENU_TITLE_ALL_COMPUTERS="All Computers";
    
    //create a methods that fetch all the shared folders of the server
    
    //constant Message commands
    public static String MESSAGE_REMOTE_DESKTOP="remote_desktop";
    public static String MESSAGE_CLOSE_REMOTE_DESKTOP="close_remote_desktop";
    public static String MESSAGE_REMOTE_APP="remote_app";
    public static String MESSAGE_CLOSE_REMOTE_APP="close_remote_app";
    public static String MESSAGE_SHARE_FILE="share_file";
    public static String MESSAGE_NETWORK_MONITOR="network_monitor";
    public static String MESSAGE_MESSAGE="message";
    public static String MESSAGE_UNICAST_MESSAGE="unicast";
    public static String MESSAGE_MULTICAST_MESSAGE="multicast";
    public static String MESSAGE_BROADCAST_MESSAGE="broadcast";
    public static String MESSAGE_CLOSE="close";
    public static String MESSAGE_START="start";
    public static String MESSAGE_END="end";
    public static String MESSAGE_NETWORK_MAINTENANCE="network_maintenance";
    
    //message type choices
    public static final String UNICAST_MESSAGE_TYPE="unicast";
    public static final String MULTICAST_MESSAGE_TYPE="multicast";
    public static final String BROADCAST_MESSAGE_TYPE="broadcast";
    
    //shared folder ip and port
    
}
