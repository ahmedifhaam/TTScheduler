/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.view.constraintpanels;

import com.ttsh.ifhaam.controller.TimeTableManager;
import com.ttsh.ifhaam.models.Constraints.Constraint;
import com.ttsh.ifhaam.models.Constraints.SubjectBeforeSubjectConstraint;
import com.ttsh.ifhaam.models.Exam;
import com.ttsh.ifhaam.models.Subject;
import java.util.ArrayList;

/**
 *
 * @author Ahmed
 */
public class SubjectBeforeSubject extends javax.swing.JPanel implements ConstraintValueater {
    ArrayList<Exam> exams;
    Constraint con;
    /**
     * Creates new form SubjectBeforeSubject
     */
    public SubjectBeforeSubject() {
        initComponents();
        exams = TimeTableManager.getInstance().getExams();
        for(Exam exam :exams){
            for(Subject sbj: exam.getSubjects()){
                cmbSubjctBefore.addItem(sbj);
                cmbSubjectAfter.addItem(sbj);
            }
            
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbSubjctBefore = new javax.swing.JComboBox<>();
        cmbSubjectAfter = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        spnFitness = new javax.swing.JSpinner();

        jLabel1.setText("Subject before");

        jLabel2.setText("Subject after");

        jLabel3.setText("Fitness");

        spnFitness.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cmbSubjctBefore, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbSubjectAfter, 0, 211, Short.MAX_VALUE))
                    .addComponent(spnFitness, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(cmbSubjctBefore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbSubjectAfter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(spnFitness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Subject> cmbSubjctBefore;
    private javax.swing.JComboBox<Subject> cmbSubjectAfter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSpinner spnFitness;
    // End of variables declaration//GEN-END:variables

    @Override
    public Constraint getConstraint() throws ConstraintException {
        Subject sbjBefore = (Subject)cmbSubjctBefore.getSelectedItem();
        Subject sbjAfter = (Subject )cmbSubjectAfter.getSelectedItem();
        int fitness = (int)spnFitness.getValue();
        con = new SubjectBeforeSubjectConstraint(fitness,sbjAfter,sbjBefore);
        return con;
    }
}
