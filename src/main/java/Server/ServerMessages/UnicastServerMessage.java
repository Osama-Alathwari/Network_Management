/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.ServerMessages;

import Server.MasterComputerServer;
import static Server.MasterComputerServer.serverSocket;
import Server.ServerConnectionThread;
import data.ComputerPC;
import data.Configuration;
import data.Setting;
import database.SQLiteJDBC;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author root
 */
public class UnicastServerMessage extends javax.swing.JFrame implements Runnable {


    public UnicastServerMessage() {
            initComponents();
            PublishClientPC();
            

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TargetComputerComboBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        UnicastMessageTextArea = new javax.swing.JTextArea();
        CloseSendButton = new javax.swing.JButton();
        SendMessageButton = new javax.swing.JButton();
        UnicastServerIconLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(400, 300));
        setMinimumSize(new java.awt.Dimension(400, 300));
        setPreferredSize(new java.awt.Dimension(400, 300));

        jLabel1.setText("Send Unicast Message");

        jLabel2.setText("Computer PC");

        jLabel3.setText("Message");

        UnicastMessageTextArea.setColumns(20);
        UnicastMessageTextArea.setRows(5);
        jScrollPane1.setViewportView(UnicastMessageTextArea);

        CloseSendButton.setText("Close");
        CloseSendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseSendButtonActionPerformed(evt);
            }
        });

        SendMessageButton.setText("Send");
        SendMessageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendMessageButtonActionPerformed(evt);
            }
        });

        UnicastServerIconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        UnicastServerIconLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        UnicastServerIconLabel.setMaximumSize(new java.awt.Dimension(40, 40));
        UnicastServerIconLabel.setMinimumSize(new java.awt.Dimension(40, 40));
        UnicastServerIconLabel.setPreferredSize(new java.awt.Dimension(40, 40));

        jLabel5.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(UnicastServerIconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(TargetComputerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel3)
                        .addGap(45, 45, 45)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(219, 219, 219)
                        .addComponent(CloseSendButton)
                        .addGap(18, 18, 18)
                        .addComponent(SendMessageButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UnicastServerIconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1)))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel2))
                    .addComponent(TargetComputerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel3))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CloseSendButton)
                    .addComponent(SendMessageButton))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SendMessageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendMessageButtonActionPerformed
        String msg = UnicastMessageTextArea.getText();
        String selected = TargetComputerComboBox.getSelectedItem().toString();
        String[] data = selected.split("-");
        if (!msg.equals("")) {
            MasterComputerServer.serverSocket.sendData(Setting.MESSAGE_UNICAST_MESSAGE+"#"+
                    msg,Integer.parseInt(data[1])); //Logger.getLogger(UnicastServerMessage.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("The server Sent Unicast data");
            UnicastMessageTextArea.setText("");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error you shoul fill a message", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("The field should contain data");
        }
    }//GEN-LAST:event_SendMessageButtonActionPerformed

    private void CloseSendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseSendButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_CloseSendButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseSendButton;
    private javax.swing.JButton SendMessageButton;
    private javax.swing.JComboBox<String> TargetComputerComboBox;
    private javax.swing.JTextArea UnicastMessageTextArea;
    private javax.swing.JLabel UnicastServerIconLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        UnicastServerIconLabel.setIcon(Configuration.getImageIcon("unicast_ic.png"));
        System.out.println("Server Unicast connected");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        show();
    }

    private void PublishClientPC() {
        SQLiteJDBC db;
        ArrayList<ComputerPC> pc;
        db = new SQLiteJDBC();
        pc = db.getAllNetworkPC();
        /*ArrayList<String> client = new ArrayList<>();
        for (ComputerPC allPC : pc) {
            client.add(allPC.getPCName() + "-" + allPC.getPCID());
        }*/
        ArrayList<String> client = new ArrayList<>();
        ArrayList<Server.ServerConnectionThread> clients = Server.Server.getAllClients();
        int counter = 1;
        for (ServerConnectionThread client1 : clients) {
            client.add("Computer-" + counter++);
        }
        //new DefaultComboBoxModel<String>(ls.toArray(new String[0]))
        TargetComputerComboBox.setModel(new DefaultComboBoxModel<String>(client.toArray(new String[0])));
    }
}
