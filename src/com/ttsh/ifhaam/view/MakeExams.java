/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.view;

import com.ttsh.ifhaam.controller.TimeTableManager;
import com.ttsh.ifhaam.models.Exam;

import com.ttsh.ifhaam.models.Subject;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Ahmed
 */
public class MakeExams extends javax.swing.JFrame {
    private ArrayList<Exam> exams;
    private ArrayList<Subject> selectedSubjects;
    String[] columns={"Code","Repeat","Year"}; 
    class SubjectTableModel extends DefaultTableModel{
        
        
        @Override
        public int getRowCount(){
            return selectedSubjects.size();
        }

        public void removeItem(Subject subject){
            selectedSubjects.remove(subject);
            this.fireTableDataChanged();
        }

        @Override
            public int getColumnCount() {
                return columns.length;
            }

        @Override
        public String getColumnName(int columnIndex) {
            return columns[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Subject sbj = selectedSubjects.get(rowIndex);
            switch(columnIndex){
                case 0:
                    return sbj.getSubjectCode();

                case 1:
                    return sbj.isIsRepeat()?"Yes":"No";

                case 2:
                    return sbj.getYear();

                default:
                    return "";
            }
        }

    }
    
    /**
     * Creates new form MakeExams
     */
    public MakeExams(ArrayList selectedSubject) {
        initComponents();
        this.selectedSubjects = selectedSubject;
        exams = new ArrayList<>();
        lstExams.setModel(new ExamListModel());
        tblSubjects.setModel(new SubjectTableModel());
        //tblSubjectsInExam.setModel(new SubjectTableModel());
    }
    
    
    
    class ExamListModel extends DefaultListModel{
        @Override
        public int getSize(){
            return exams.size();
        }
        
        @Override
        public Exam getElementAt(int index){
            return exams.get(index);
        }
        
        @Override
        public Exam remove(int index){
            Exam exam =exams.remove(index);
            this.fireIntervalRemoved(this, index, index);
            return exam;
        }
        
        public void add(Exam exam){
            exams.add(exam);
            this.fireContentsChanged(this, exams.size(), exams.size());
        }
        
        public void addElement(Exam exam){
            add(exam);
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

        jDesktopPane1 = new javax.swing.JDesktopPane();
        frmSelectedSubjects = new javax.swing.JInternalFrame();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSubjects = new javax.swing.JTable();
        btnCreateExamForSelected = new javax.swing.JButton();
        btnCreateExamEach = new javax.swing.JButton();
        frmSubjectsInExam = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSubjectsInExam = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        frmExams = new javax.swing.JInternalFrame();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstExams = new javax.swing.JList<>();
        btnRemoveExam = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        frmSelectedSubjects.setIconifiable(true);
        frmSelectedSubjects.setMaximizable(false);
        frmSelectedSubjects.setResizable(false);
        frmSelectedSubjects.setTitle("Selected Subjects");
        frmSelectedSubjects.setVisible(true);

        tblSubjects.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblSubjects);

        btnCreateExamForSelected.setText("Create Exam with selected Subjects");
        btnCreateExamForSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateExamForSelectedActionPerformed(evt);
            }
        });

        btnCreateExamEach.setText("Create an exam for Subjects");
        btnCreateExamEach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateExamEachActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frmSelectedSubjectsLayout = new javax.swing.GroupLayout(frmSelectedSubjects.getContentPane());
        frmSelectedSubjects.getContentPane().setLayout(frmSelectedSubjectsLayout);
        frmSelectedSubjectsLayout.setHorizontalGroup(
            frmSelectedSubjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmSelectedSubjectsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmSelectedSubjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(frmSelectedSubjectsLayout.createSequentialGroup()
                        .addGroup(frmSelectedSubjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCreateExamForSelected, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCreateExamEach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 7, Short.MAX_VALUE)))
                .addContainerGap())
        );
        frmSelectedSubjectsLayout.setVerticalGroup(
            frmSelectedSubjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmSelectedSubjectsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCreateExamForSelected)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCreateExamEach)
                .addContainerGap())
        );

        frmSubjectsInExam.setTitle("Subjects in the Exam");
        frmSubjectsInExam.setVisible(true);

        tblSubjectsInExam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblSubjectsInExam);

        jButton3.setText("Remove Selected");

        javax.swing.GroupLayout frmSubjectsInExamLayout = new javax.swing.GroupLayout(frmSubjectsInExam.getContentPane());
        frmSubjectsInExam.getContentPane().setLayout(frmSubjectsInExamLayout);
        frmSubjectsInExamLayout.setHorizontalGroup(
            frmSubjectsInExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmSubjectsInExamLayout.createSequentialGroup()
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        frmSubjectsInExamLayout.setVerticalGroup(
            frmSubjectsInExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmSubjectsInExamLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3))
        );

        frmExams.setTitle("Exams");
        frmExams.setVisible(true);

        lstExams.setModel(new DefaultListModel());
        lstExams.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstExamsValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(lstExams);

        btnRemoveExam.setText("Remove");
        btnRemoveExam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveExamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frmExamsLayout = new javax.swing.GroupLayout(frmExams.getContentPane());
        frmExams.getContentPane().setLayout(frmExamsLayout);
        frmExamsLayout.setHorizontalGroup(
            frmExamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
            .addGroup(frmExamsLayout.createSequentialGroup()
                .addComponent(btnRemoveExam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        frmExamsLayout.setVerticalGroup(
            frmExamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmExamsLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRemoveExam)
                .addContainerGap())
        );

        jButton4.setText("Select More Subjects");

        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        jDesktopPane1.setLayer(frmSelectedSubjects, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(frmSubjectsInExam, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(frmExams, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jButton4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(btnNext, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(frmSelectedSubjects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(frmExams)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(frmSubjectsInExam)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(frmSelectedSubjects)
                    .addComponent(frmExams)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(frmSubjectsInExam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateExamForSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateExamForSelectedActionPerformed
        // TODO add your handling code here:
        int[] indices = tblSubjects.getSelectedRows();
        ArrayList<Subject> subjectsForExam = new ArrayList<>();
        for(int index :indices){
            subjectsForExam.add(selectedSubjects.get(index));
        }
        for(Subject subject:subjectsForExam)
            ((SubjectTableModel)tblSubjects.getModel()).removeItem(subject);
        
        
        ((ExamListModel)lstExams.getModel()).addElement(new Exam(subjectsForExam));
    }//GEN-LAST:event_btnCreateExamForSelectedActionPerformed

    private void btnRemoveExamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveExamActionPerformed
        // TODO add your handling code here:
        int index = lstExams.getSelectedIndex();
        if(index!=-1){
            Exam ex = lstExams.getModel().getElementAt(index);
            for(Subject sbj :ex.getSubjects())
                selectedSubjects.add(sbj);
        
            ((DefaultTableModel)tblSubjects.getModel()).fireTableDataChanged();
            ((DefaultListModel)lstExams.getModel()).remove(index);
        }
    }//GEN-LAST:event_btnRemoveExamActionPerformed

    private void btnCreateExamEachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateExamEachActionPerformed
        // TODO add your handling code here:
        for(Subject sbj :selectedSubjects){
            Exam e = new Exam();
            e.addSubject(sbj);
            
            ((ExamListModel)lstExams.getModel()).addElement(e);
        }
        selectedSubjects.clear();
        ((DefaultTableModel)tblSubjects.getModel()).fireTableDataChanged();
    }//GEN-LAST:event_btnCreateExamEachActionPerformed

    private void lstExamsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstExamsValueChanged
        // TODO add your handling code here:
        int index = lstExams.getSelectedIndex();
        tblSubjectsInExam.setModel(new DefaultTableModel());
        if(index!=-1 && !evt.getValueIsAdjusting()){
            Exam exam = lstExams.getModel().getElementAt(index);
            tblSubjectsInExam.setModel(new TableModel() {
                @Override
                public int getRowCount() {
                    return exam.getSubjects().size();
                }

                @Override
                public int getColumnCount() {
                    return columns.length;
                }

                @Override
                public String getColumnName(int columnIndex) {
                    return columns[columnIndex];
                }

                @Override
                public Class<?> getColumnClass(int columnIndex) {
                   return String.class;
                }

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }

                @Override
                public Object getValueAt(int rowIndex, int columnIndex) {
                    Subject sbj = exam.getSubject(rowIndex);
                    switch(columnIndex){
                        case 0:
                            return sbj.getSubjectCode();
                        case 1:
                            return sbj.isIsRepeat()?"Yes":"No";

                        case 2:
                            return sbj.getYear();

                        default:
                            return "";
                            
                    }
                    
                }

                @Override
                public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void addTableModelListener(TableModelListener l) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void removeTableModelListener(TableModelListener l) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                
                
            });
            tblSubjectsInExam.getColumnModel().getColumn(0).setPreferredWidth(180);
            
        }
    }//GEN-LAST:event_lstExamsValueChanged

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        TimeTableManager ttMan = TimeTableManager.getInstance();
        ttMan.setExams(exams);
        System.out.println(exams.size()+ " size of exam");
        this.dispose();
        new Initializing().setVisible(true);
    }//GEN-LAST:event_btnNextActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MakeExams.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MakeExams.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MakeExams.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MakeExams.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MakeExams(new ArrayList<Subject>()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateExamEach;
    private javax.swing.JButton btnCreateExamForSelected;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnRemoveExam;
    private javax.swing.JInternalFrame frmExams;
    private javax.swing.JInternalFrame frmSelectedSubjects;
    private javax.swing.JInternalFrame frmSubjectsInExam;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<Exam> lstExams;
    private javax.swing.JTable tblSubjects;
    private javax.swing.JTable tblSubjectsInExam;
    // End of variables declaration//GEN-END:variables
}