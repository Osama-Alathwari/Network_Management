/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.nio.file.Paths;
import javax.swing.ImageIcon;

public class Configuration {
    
    public static Dimension getScreenDimension()
    {
        // getScreenSize() returns the size
        // of the screen in pixels
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        
        // width will store the width of the screen
        int width = (int)size.getWidth();
        
        // height will store the height of the screen
        int height = (int)size.getHeight();
        
        System.out.println("Current Screen resolution : "
                           + "width : " + width
                           + " height : " + height);
        return size;
    }
    public static String getCurrentPath(){
       return Paths.get("")
                .toAbsolutePath()
                .toString(); 
    }
    public static ImageIcon getImageIcon(String imageName){
        return (new ImageIcon(getCurrentPath()+"/src/main/java/Icon/"+imageName));
    }
    public static String getImageDirectoryPath(){
        return getCurrentPath()+"/src/main/java/Icon/";
    }
}
