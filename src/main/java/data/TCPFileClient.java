package data;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPFileClient extends JFrame implements ActionListener, MouseListener, Runnable {

    private JPanel panel;
    private JLabel title, subT, msg, error, servFiles;
    private Font font, labelfont;
    private JTextField txt;
    private JButton up, down;
    private String dirName;
    private Socket clientSocket;
    private InputStream inFromServer;
    private OutputStream outToServer;
    private BufferedInputStream bis;
    private ObjectInputStream oin;
    private PrintWriter pw;
    private BufferedReader bisr;
    private String name, file, path;
    private String hostAddr;
    //int portNumber = Setting.APP_SHARED_FOLDER_PORT;
    private int c;
    private int size = 9022386;
    private JList<String> filelist;
    private String[] names = new String[10000];
    private int len; // number of files on the server retrieved
    private static final String downloadFolder = System.getProperty("user.home") + "/Documents";

    public TCPFileClient() {
        super("TCP CLIENT");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // set dirName to the one that's entered by the user
        //dirName = downloadFolder;
        dirName = "/home/focal/Desktop/";
        panel = new JPanel(null);
        font = new Font("Roboto", Font.BOLD, 40);
        title = new JLabel("Server Shared Folder ");
        title.setFont(font);
        title.setBounds(300, 50, 400, 50);
        panel.add(title);
        labelfont = new Font("Roboto", Font.PLAIN, 20);
        subT = new JLabel("Enter File Name :");
        subT.setFont(labelfont);
        subT.setBounds(100, 450, 200, 50);
        panel.add(subT);
        txt = new JTextField();
        txt.setBounds(400, 450, 500, 50);
        panel.add(txt);
        up = new JButton("Save To .");
        up.setBounds(250, 550, 200, 50);
        panel.add(up);
        down = new JButton("Download");
        down.setBounds(550, 550, 200, 50);
        panel.add(down);
        error = new JLabel("");
        error.setFont(labelfont);
        error.setBounds(200, 650, 600, 50);
        panel.add(error);

        // creating socket
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                SendEndToServer();
            }
        });
    }

    private void SendEndToServer() {
        try {
            pw.println("END");
            pw.flush();
            finished = true;
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("Exception in client shred sleep " + ex.getMessage());
            Logger.getLogger(TCPFileClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mouseClicked(MouseEvent click) {
        if (click.getClickCount() == 1 || click.getClickCount() == 2) {
            String selectedItem = (String) filelist.getSelectedValue();
            txt.setText(selectedItem);
            panel.revalidate();
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == up) {
            dirName = selectTargetFolder();
        } else if (event.getSource() == down) {
            File directory = new File(dirName);
            if (!directory.exists()) {
                directory.mkdir();
            }
            name = txt.getText();
            file = new String("*" + name + "*");
            pw.println(file); //lets the server know which file is to be downloaded
            DownloadFile(directory, name);

        }
    }

    private static void sendBytes(BufferedInputStream in, OutputStream out) throws Exception {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int size = 9022386;
                    byte[] data = new byte[size];
                    int bytes = 0;
                    int c = in.read(data, 0, data.length);
                    out.write(data, 0, c);
                    out.flush();
                    System.out.println("Finish Writing to server .....");
                } catch (IOException ex) {
                    Logger.getLogger(TCPFileClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
    }

    private String selectTargetFolder() {
        //String getProperty = System.getProperty("user.home");
        String getProperty = System.getProperty("user.home");
        File defaultDir = new File(getProperty);
        JFileChooser chooser = new JFileChooser(defaultDir);
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select Target Download folder : ");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //    
        if (chooser.showOpenDialog(this) == JFileChooser.OPEN_DIALOG) {
            System.out.println("getCurrentDirectory(): "
                    + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    + chooser.getSelectedFile());
            //TargetSharedFolderPathTextField.setText(chooser.getSelectedFile().toString());
        } else {
            System.out.println("No Selection ");
            //InitializeDefaultTargetFolder();
        }
        return chooser.getSelectedFile().toString();
    }
    private static boolean finished = false;

    private void DownloadFile(File directory, String name) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        String s=bisr.readLine();
                        //String s = (String) oin.readObject();
                        if (s.contains("Success")) {
                            size = getFileSize(s);
                            System.out.println("File Size from server is : [ " + size + " ]");
                            byte[] data = new byte[size];
                            File f = new File(directory, name);
                            FileOutputStream fileOut = new FileOutputStream(f);
                            DataOutputStream dataOut = new DataOutputStream(fileOut);
                            boolean complete = true;
                            int length = 0;
                            while (length < size) {
                                System.out.println("Reading the file content from server");
                                c = inFromServer.read(data, 0, data.length);
                                length = length + c;
                                System.out.println("Data are ..... [ " + c + " ] ");
                                dataOut.write(data, 0, c);
                                dataOut.flush();
                                if (length >= size) {
                                    complete = false;
                                    System.out.println("Completed");
                                    error.setText("Completed");
                                    break;
                                }
                            }
                            fileOut.close();
                            System.err.println("Read file finished ...");
                            System.out.println("Data Read are ..... [ " + length + " ] ---- [ " + size + " ] ");
                        } else {
                            System.out.println("Requested file not found on the server.");
                            error.setText("Requested file not found on the server.");
                            //panel.revalidate();
                        }
                    //}
                } catch (Exception exc) {
                    System.out.println("Exception: " + exc.getMessage());
                    exc.printStackTrace();
                    System.out.println("Exception: In reading file object ");
                    error.setText("Exception:" + exc.getMessage());
                }
            }
        });
        t.start();

    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket(Setting.APP_MAIN_SERVER_HOST_IP, Setting.APP_SHARED_FOLDER_PORT);
            inFromServer = clientSocket.getInputStream();
            pw = new PrintWriter(clientSocket.getOutputStream(), true);
            bisr=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToServer = clientSocket.getOutputStream();
            oin = new ObjectInputStream(inFromServer);
            up.addActionListener(this);
            down.addActionListener(this);
            System.out.println("Client Sharing connected");
            System.out.println("Client Sharing Thread started");
            try {

                String s = (String) oin.readObject();
                System.out.println(s);

                len = Integer.parseInt((String) oin.readObject());
                System.out.println(len);

                String[] temp_names = new String[len];

                for (int i = 0; i < len; i++) {
                    String filename = (String) oin.readObject();
                    //System.out.println(filename);
                    names[i] = filename;
                    temp_names[i] = filename;
                }

                // sort the array of strings that's going to get displayed in the scrollpane
                Arrays.sort(temp_names);

                servFiles = new JLabel("Server Shared Files :");
                servFiles.setBounds(350, 125, 400, 50);
                panel.add(servFiles);

                filelist = new JList<>(temp_names);
                JScrollPane scroll = new JScrollPane(filelist);
                scroll.setBounds(300, 200, 400, 200);

                panel.add(scroll);
                filelist.addMouseListener(this);

            } catch (Exception exc) {
                System.out.println("Exception: " + exc.getMessage());
                error.setText("Exception:" + exc.getMessage());
                error.setBounds(300, 125, 600, 50);
                panel.revalidate();
            }

            getContentPane().add(panel);
            setSize(1000, 900);
            setVisible(true);
            show();
            //panel.revalidate();
        } catch (IOException ex) {
            Logger.getLogger(TCPFileClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int getFileSize(String s) {
        String[] data = s.split(":");
        return Integer.parseInt(data[1]);
    }
}
