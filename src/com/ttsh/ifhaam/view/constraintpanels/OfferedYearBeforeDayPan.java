/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.view.constraintpanels;

import com.ttsh.ifhaam.controller.TimeTableManager;
import com.ttsh.ifhaam.models.Constraints.Constraint;
import com.ttsh.ifhaam.models.Constraints.OfferedYearSubjectBeforeDay;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Ahmed
 */
public class OfferedYearBeforeDayPan extends javax.swing.JPanel implements ConstraintValueater{
    OfferedYearSubjectBeforeDay con;
    /**
     * Creates new form OfferedYearBeforeSubjectPan
     */
    public OfferedYearBeforeDayPan() {
        initComponents();
        spnDate.setValue(1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spnFitness = new javax.swing.JSpinner();
        spnDate = new javax.swing.JSpinner();
        spnYear = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dpDate = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();

        spnFitness.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));

        spnDate.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnDateStateChanged(evt);
            }
        });

        jLabel1.setText("Fitness");

        jLabel2.setText("Day Before");

        dpDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dpDateActionPerformed(evt);
            }
        });

        jLabel3.setText("OfferedYear");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spnYear, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                    .addComponent(spnDate))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(29, 29, 29)
                        .addComponent(spnFitness, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dpDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnFitness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(spnYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(dpDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void spnDateStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnDateStateChanged
        // TODO add your handling code here:
        if(evt.getSource().equals(spnDate)){
            try{
                dpDate.setDate(TimeTableManager.getInstance().getDates()[(int)spnDate.getValue()-1] );

            }catch(Exception ex){

                JOptionPane.showMessageDialog(null,"Invalid Input"+ex.getMessage(),"Error",JOptionPane.INFORMATION_MESSAGE);
                spnDate.setValue(1);

            }
        }
    }//GEN-LAST:event_spnDateStateChanged

    private void dpDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dpDateActionPerformed
        // TODO add your handling code here:
        Date[] dates =TimeTableManager.getInstance().getDates();

        if(!(
            (dpDate.equals(dates[0])) ||
            (dpDate.getDate().after(dates[0] ) && dpDate.getDate().before(dates[dates.length-1])) ||
            (dpDate.equals(dates[dates.length-1]))

        )){

            dpDate.setDate(dates[0]);
            JOptionPane.showMessageDialog(this,"Date Selected is out of the date limit selecting the first day", "Invaliid"
                ,JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_dpDateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXDatePicker dpDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSpinner spnDate;
    private javax.swing.JSpinner spnFitness;
    private javax.swing.JSpinner spnYear;
    // End of variables declaration//GEN-END:variables

    @Override
    public Constraint getConstraint() throws ConstraintException {
        int fitnes = (int)spnFitness.getValue();
        int date    = (int)spnDate.getValue();
        int year =(int)spnYear.getValue();
        con = new OfferedYearSubjectBeforeDay(fitnes,date,year);
        return con;
    }
}
