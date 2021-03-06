/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.view.constraintpanels;

import com.ttsh.ifhaam.controller.DataBaseController;
import com.ttsh.ifhaam.models.ClassRoom;
import com.ttsh.ifhaam.models.Constraints.ClassRoomWhileClassRoomConstraint;
import com.ttsh.ifhaam.models.Constraints.Constraint;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Ahmed
 */
public class TwoClassRoom extends javax.swing.JPanel implements ConstraintValueater {
    ClassRoomWhileClassRoomConstraint constraint;
    ArrayList<ClassRoom> classRooms;

    /**
     * Creates new form TwoClassRoom
     */
    public TwoClassRoom() {
        initComponents();
        lblDescription.setText(ClassRoomWhileClassRoomConstraint.DESCRIPTION );
        classRooms = getClassRooms();
        System.out.println(classRooms.size() + "cr size");
        if(classRooms!=null){
            for(ClassRoom cr :classRooms){
                cmbRoom1.addItem(cr);
                cmbRoom2.addItem(cr);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Error Retrieving ClassRooms From Database", "Not Found", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    public Constraint getConstraint() throws ConstraintException{
        if(cmbRoom1.getModel().getSelectedItem().equals(cmbRoom2.getModel().getSelectedItem())){
            throw new ConstraintException("Class Rooms are Same ");
        }else{
            constraint = new ClassRoomWhileClassRoomConstraint((ClassRoom)cmbRoom1.getModel().getSelectedItem(),
            (ClassRoom)cmbRoom2.getModel().getSelectedItem());
        }
        return constraint;
    }
    
    private ArrayList<ClassRoom> getClassRooms(){
        
        DataBaseController dbc = DataBaseController.newInstance();
        return dbc.getClassRooms();
        
    }
    
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbRoom1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbRoom2 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        lblDescription = new javax.swing.JTextArea();

        jLabel1.setText("ClassRoom 1");

        jLabel2.setText("ClassRoom 2");

        lblDescription.setEditable(false);
        lblDescription.setColumns(10);
        lblDescription.setLineWrap(true);
        lblDescription.setRows(5);
        lblDescription.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        lblDescription.setFocusable(false);
        jScrollPane1.setViewportView(lblDescription);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(cmbRoom2, 0, 250, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(cmbRoom1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbRoom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbRoom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<ClassRoom> cmbRoom1;
    private javax.swing.JComboBox<ClassRoom> cmbRoom2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea lblDescription;
    // End of variables declaration//GEN-END:variables
}
