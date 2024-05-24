
import Client.ClientPC;
import Client.MainClient;
import Server.MasterComputerServer;
import java.awt.Frame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AppLuncher extends Thread {

    public AppLuncher() {
        Object[] possibilities = {"Server Main PC", "Client PC"};
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String type = (String) JOptionPane.showInputDialog(
                f,
                "Installation Target PC:\n",
                "Setup Network Manager App",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "");
        if (type.equals("Server Main PC")) {
            System.out.println("The app is server type");
            MasterComputerServer serverApp = new MasterComputerServer();
        } else if (type.equals("Client PC")) {
            System.out.println("The app is client type");
            ClientPC pc = new ClientPC();
            pc.setVisible(true);
        }
    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AppLuncher app = new AppLuncher();
                app.start();
            }
        });
    }
}
