/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.client.gui;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import room.client.Connector;
import room.ddl.Room;
import room.ddl.exception.InvalidDataException;

/**
 *
 * @author Sylvain
 */
public class Lounge_GUI extends javax.swing.JFrame {

    ArrayList<Room> listOfRooms;
    Connector connector;

    public Lounge_GUI(Connector _connector) throws ClassNotFoundException {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        connector = _connector;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        roomListBox = new javax.swing.JList();
        resfreshLoungeButton = new javax.swing.JButton();
        newRoomButton = new javax.swing.JButton();
        openRoomButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        roomListBox.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(roomListBox);

        resfreshLoungeButton.setText("Rafraichir");
        resfreshLoungeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resfreshLoungeButtonActionPerformed(evt);
            }
        });

        newRoomButton.setText("Nouvelle Salle");
        newRoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newRoomButtonActionPerformed(evt);
            }
        });

        openRoomButton.setText("Ouvrir Salle");
        openRoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openRoomButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resfreshLoungeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newRoomButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(openRoomButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(resfreshLoungeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newRoomButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(openRoomButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void resfreshLoungeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resfreshLoungeButtonActionPerformed
        try {
            listOfRooms = connector.RefreshLounge();
        } catch (Exception ex) {
            Logger.getLogger(Lounge_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        DefaultListModel model = new DefaultListModel();

        model.clear();
        for (Room _room : listOfRooms) {
            model.addElement(_room.getName());
        }

        roomListBox.setModel(model);
        roomListBox.doLayout();
    }//GEN-LAST:event_resfreshLoungeButtonActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        resfreshLoungeButtonActionPerformed(null);

    }//GEN-LAST:event_formWindowOpened

    private void newRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newRoomButtonActionPerformed

        try {
            new RoomName_GUI(connector).show();

        } catch (Exception ex) {
            Logger.getLogger(Lounge_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_newRoomButtonActionPerformed

    private void openRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openRoomButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_openRoomButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton newRoomButton;
    private javax.swing.JButton openRoomButton;
    private javax.swing.JButton resfreshLoungeButton;
    private javax.swing.JList roomListBox;
    // End of variables declaration//GEN-END:variables
}