/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import java.awt.BorderLayout;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

/**
 *
 * @author root
 */
public class AllNetworkComputersRemote extends javax.swing.JPanel {

    /**
     * Creates new form AllNetworkComputersRemote
     */
    public AllNetworkComputersRemote() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FirstPreviewPanel = new javax.swing.JPanel();
        SecondPreviewPanel = new javax.swing.JPanel();
        ThirdPreviewPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ForthPreviewPanel = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(1300, 220));
        setMinimumSize(new java.awt.Dimension(1300, 220));
        setPreferredSize(new java.awt.Dimension(1300, 220));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        FirstPreviewPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        FirstPreviewPanel.setMaximumSize(new java.awt.Dimension(270, 200));
        FirstPreviewPanel.setMinimumSize(new java.awt.Dimension(270, 200));
        FirstPreviewPanel.setPreferredSize(new java.awt.Dimension(270, 200));
        FirstPreviewPanel.setLayout(new java.awt.BorderLayout());
        add(FirstPreviewPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 10, -1, -1));

        SecondPreviewPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        SecondPreviewPanel.setMaximumSize(new java.awt.Dimension(270, 200));
        SecondPreviewPanel.setMinimumSize(new java.awt.Dimension(270, 200));
        SecondPreviewPanel.setPreferredSize(new java.awt.Dimension(270, 200));
        SecondPreviewPanel.setLayout(new java.awt.BorderLayout());
        add(SecondPreviewPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 10, -1, -1));

        ThirdPreviewPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ThirdPreviewPanel.setMaximumSize(new java.awt.Dimension(270, 200));
        ThirdPreviewPanel.setMinimumSize(new java.awt.Dimension(270, 200));
        ThirdPreviewPanel.setPreferredSize(new java.awt.Dimension(270, 200));
        ThirdPreviewPanel.setLayout(new java.awt.BorderLayout());
        add(ThirdPreviewPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(666, 10, -1, -1));
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1300, 238, -1, -1));
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 224, 71, 8));

        ForthPreviewPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ForthPreviewPanel.setMaximumSize(new java.awt.Dimension(270, 200));
        ForthPreviewPanel.setMinimumSize(new java.awt.Dimension(270, 200));
        ForthPreviewPanel.setPreferredSize(new java.awt.Dimension(270, 200));
        ForthPreviewPanel.setLayout(new java.awt.BorderLayout());
        add(ForthPreviewPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(987, 10, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    public void setFirstPreviewPanel(JDesktopPane FirstPreviewPanel) {
        this.FirstPreviewPanel.add(FirstPreviewPanel,BorderLayout.CENTER);
       
    }

    public void setSecondPreviewPanel(JDesktopPane SecondPreviewPanel) {
        this.SecondPreviewPanel.add( SecondPreviewPanel,BorderLayout.CENTER);
    }

    public void setThirdPreviewPanel(JDesktopPane ThirdPreviewPanel) {
        this.ThirdPreviewPanel.add(ThirdPreviewPanel,BorderLayout.CENTER);
    }

    public JPanel getFirstPreviewPanel() {
        return FirstPreviewPanel;
    }

    public JPanel getSecondPreviewPanel() {
        return SecondPreviewPanel;
    }

    public JPanel getThirdPreviewPanel() {
        return ThirdPreviewPanel;
    }

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel FirstPreviewPanel;
    private javax.swing.JPanel ForthPreviewPanel;
    private javax.swing.JPanel SecondPreviewPanel;
    private javax.swing.JPanel ThirdPreviewPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
