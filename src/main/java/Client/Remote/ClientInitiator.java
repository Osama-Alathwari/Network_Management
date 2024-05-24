

package Client.Remote;

import data.Configuration;
import data.Setting;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * This class is responsible for connecting to the server
 * and starting ScreenSpyer and ServerDelegate classes
 */
public class ClientInitiator extends Thread{

    Socket socket = null;
    /*
    public static void main(String[] args){
        String ip = JOptionPane.showInputDialog("Please enter server IP");
        //String port = JOptionPane.showInputDialog("Please enter server port");
        new ClientInitiator().initialize(ip, (Setting.APP_REMOTE_DESKTOP_PORT));
    }*/
    private String RemoteIP;
    
    public ClientInitiator() {
        //this.RemoteIP=remoteIP;
    }

    public void initialize( ){
        System.out.println("Initialize Client Remote");
        //IP = JOptionPane.showInputDialog("Please enter server IP");

        Robot robot = null; //Used to capture the screen
        Rectangle rectangle = null; //Used to represent screen dimensions

        try {
            System.out.println("Connecting to server ..........");
            socket = new Socket(Setting.APP_MAIN_SERVER_HOST_IP, Setting.APP_REMOTE_DESKTOP_PORT);
            System.out.println("Connection Established.");

            //Get default screen device
            GraphicsEnvironment gEnv=GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev=gEnv.getDefaultScreenDevice();

            //Get screen dimensions
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            rectangle = new Rectangle(dim);

            //Prepare Robot object
            robot = new Robot(gDev);

            //draw client gui
            //drawGUI();
            //ScreenSpyer sends screenshots of the client screen
            ScreenSpyer sc=new ScreenSpyer(socket,robot,rectangle);
            sc.start();
            //ServerDelegate recieves server commands and execute them
            ServerDelegate sd=new ServerDelegate(socket,robot);
            sd.start();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (AWTException ex) {
                ex.printStackTrace();
        }
    }

    private void drawGUI() {
        JFrame frame = new JFrame("Remote Admin");
        JButton button= new JButton("Terminate");
        
        frame.setBounds(100,100,150,150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(button);
        button.addActionListener( new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        }
      );
      //frame.setVisible(true);
    }

    @Override
    public void run() {
        super.run();
        initialize();
    }
    
}
