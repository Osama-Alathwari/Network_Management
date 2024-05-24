/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.ServerMessages;

import Server.MasterComputerServer;
import data.BroadCastServer;
import data.ComputerPC;
import data.Configuration;
import data.Setting;
import database.SQLiteJDBC;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author root
 */
public class BroadCastServerMessage extends javax.swing.JFrame implements Runnable {

    /**
     * Creates new form BroadCastServerMessage
     */
    private SQLiteJDBC db;
    BroadCastServer broadCastServer;
    private ArrayList<ComputerPC> pc;

    public BroadCastServerMessage() {
        initComponents();
        broadCastServer = new BroadCastServer();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BroadCastIconLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        BroadCastMessageTextArea = new javax.swing.JTextArea();
        SendBroadCastButton = new javax.swing.JButton();
        CancelSendBroadCastButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(400, 245));
        setMinimumSize(new java.awt.Dimension(400, 245));
        setName("Broadcast Message"); // NOI18N
        setPreferredSize(new java.awt.Dimension(400, 245));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BroadCastIconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BroadCastIconLabel.setIcon(new ImageIcon((Configuration.getCurrentPath()+"/src/main/java/Icon/broadcast_ic.png")));
        BroadCastIconLabel.setMaximumSize(new java.awt.Dimension(40, 40));
        BroadCastIconLabel.setMinimumSize(new java.awt.Dimension(40, 40));
        BroadCastIconLabel.setPreferredSize(new java.awt.Dimension(40, 40));
        getContentPane().add(BroadCastIconLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel2.setText("Send BroadCast Message");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 12, -1, -1));

        jLabel4.setText("Message");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 128, -1, -1));

        BroadCastMessageTextArea.setColumns(20);
        BroadCastMessageTextArea.setRows(5);
        jScrollPane1.setViewportView(BroadCastMessageTextArea);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 100, 260, -1));

        SendBroadCastButton.setText("Send");
        SendBroadCastButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendBroadCastButtonActionPerformed(evt);
            }
        });
        getContentPane().add(SendBroadCastButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(318, 190, -1, -1));

        CancelSendBroadCastButton.setText("Cancel");
        CancelSendBroadCastButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelSendBroadCastButtonActionPerformed(evt);
            }
        });
        getContentPane().add(CancelSendBroadCastButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(219, 190, -1, -1));
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 221, 58, -1));

        jLabel6.setText("               ");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 220, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelSendBroadCastButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelSendBroadCastButtonActionPerformed
        dispose();
    }//GEN-LAST:event_CancelSendBroadCastButtonActionPerformed

    private void SendBroadCastButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendBroadCastButtonActionPerformed
        String msg = BroadCastMessageTextArea.getText();
        if (!msg.equals("")) {
            MasterComputerServer.serverSocket.sendData(Setting.BROADCAST_MESSAGE_TYPE+"#"+msg);
            //broadCastServer.send(msg); //Logger.getLogger(UnicastServerMessage.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("The server Sent Unicast data");
            BroadCastMessageTextArea.setText("");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error you shoul fill a message", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("The field should contain data");
        }
    }//GEN-LAST:event_SendBroadCastButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BroadCastIconLabel;
    private javax.swing.JTextArea BroadCastMessageTextArea;
    private javax.swing.JButton CancelSendBroadCastButton;
    private javax.swing.JButton SendBroadCastButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        BroadCastIconLabel.setIcon(Configuration.getImageIcon("broadcast_ic.png"));
        System.out.println("Server BroadCast connected");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        show();
        broadCastServer.start();
    }

    
}