/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.view;

import com.ttsh.ifhaam.controller.Algorithm;
import com.ttsh.ifhaam.controller.TimeTableManager;
import com.ttsh.ifhaam.models.Population;
import com.ttsh.ifhaam.models.Subject;
import com.ttsh.ifhaam.models.TimeTable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ahmed
 */
public class Main extends javax.swing.JFrame {
    Population pop;
    private int requiredFitness = 0;
    private ProcessTask task;
    
    public static int COLUMNWIDTH = 600;
    

    /**
     * Creates new form Main
     */
    
    private void setTimeTable(TimeTable tt){
        jTable1.setModel(new CustomeTableModel(tt));
        int columnCount = jTable1.getModel().getColumnCount();
        for(int i=0;i<columnCount;i++){
            jTable1.getColumnModel().getColumn(i).setMinWidth(200);
            
        }
    }
    
    private class CustomeTableModel extends AbstractTableModel{
        TimeTable tt;
        
        

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return super.getColumnClass(columnIndex); //To change body of generated methods, choose Tools | Templates.
        }

        public CustomeTableModel(TimeTable timeTable){
            this.tt = timeTable;
        }
        
        @Override
        public int getRowCount() {
            //return ((tt.getDays().get(0).getTimeSlots().get(0).getClassRooms().size())*tt.getDays().get(0).getTimeSlots().size())+tt.getDays().get(0).getTimeSlots().size();
            return (tt.getDays().get(0).getTimeSlots().get(0).getClassRooms().size()+1)*tt.getDays().get(0).getTimeSlots().size();
        }

        @Override
        public int getColumnCount() {
            return tt.countDays();
        }
        
        

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            //int timeSlot = (int) rowIndex/(tt.getDay(columnIndex).getTimeSlots().size()+2);
            //int classRoom = (int) rowIndex%(tt.getDay(columnIndex).getTimeSlots().size()+2);
            int timeSlot = (int) rowIndex/(tt.getDay(columnIndex).getTimeSlots().get(0).getClassRooms().size()+1);
            int classRoom = (int) rowIndex%(tt.getDay(columnIndex).getTimeSlots().get(0).getClassRooms().size()+1);
            //System.out.println(""+timeSlot +"-"+classRoom);
            //return tt.getSubject(columnIndex, timeSlot, classRoom);
            
            //System.out.println("timeSlot "+timeSlot+" classRoom "+classRoom);
            if(classRoom==0){
                return tt.getDay(columnIndex).getTimeSlots().get(timeSlot).getIdentifier();
            }else{
                classRoom--;
                return tt.getDay(columnIndex).getTimeSlots().get(timeSlot).getClassRooms().get(classRoom);
            }
            
            /*if(((int)rowIndex/tt.getDay(0).getTimeSlots().size())==0){
                return "";
            }else{
                int timeSlot = rowIndex/tt.getDay(columnIndex).getTimeSlots().size();
                int classRoom = rowIndex%tt.getDay(columnIndex).getTimeSlots().size();
                
                return tt.getSubject(columnIndex, 0, 0);
            }*/
        }
        
        @Override
        public String getColumnName(int index){
            return tt.getDay(index).getDateString();
        }
    
    }
    
     private void setUpTablecolumns(){
        
         for(int i=0;i<jTable1.getColumnCount();i++){
             jTable1.getColumnModel().getColumn(i).setPreferredWidth(COLUMNWIDTH);
             jTable1.getColumnModel().getColumn(i).setMinWidth(COLUMNWIDTH);
             jTable1.getColumnModel().getColumn(i).setMaxWidth(jTable1.getWidth());
         }
        
            
    }
     
    
    
    public Main(Population population){
       
        initComponents();
        this.setUpTablecolumns();
        this.setTitle("Examination Time Table Scheduler for University of Kelaniya");
        this.pop = population;
        cmbTimeTableList.removeAllItems();
        int i=0;
        while(i<pop.size()){
            cmbTimeTableList.addItem("Time Table "+ (i+1));
            if(pop.getTimeTable(i)==null)System.out.println("Null time table"+i);
            i++;
        }
        cmbTimeTableList.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e){
                //jTable1.setModel(new CustomeTableModel(pop.getTimeTable(cmbTimeTableList.getSelectedIndex())));
                setTimeTable(pop.getTimeTable(cmbTimeTableList.getSelectedIndex()));
                lblCurFitVal.setText(Algorithm.calculateFitness(pop.getTimeTable(cmbTimeTableList.getSelectedIndex()))+"");
            }
        });
    }
    
    public Main() {
        initComponents();
        this.setTitle("Examination Time Table Scheduler for University of Kelaniya");
        //pop = new Population( 3,true);//initial population
        cmbTimeTableList.removeAllItems();
        
        int i=0;
        while(i<pop.size()){
            cmbTimeTableList.addItem("TimeTable "+(i+1));
            i++;
        }
        cmbTimeTableList.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                jTable1.setModel(new CustomeTableModel(pop.getTimeTable(cmbTimeTableList.getSelectedIndex())));
                lblCurFitVal.setText(Algorithm.calculateFitness(pop.getTimeTable(cmbTimeTableList.getSelectedIndex()))+"");
            }
            
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane(jTable1,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtRequiredFitnessVal = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblCurFitVal = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnStart = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnConfirm = new javax.swing.JButton();
        prgBar = new javax.swing.JProgressBar();
        cmbTimeTableList = new javax.swing.JComboBox<>();
        btnPre = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnGetFittest = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Required Fitness Value");

        txtRequiredFitnessVal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtRequiredFitnessVal.setForeground(new java.awt.Color(255, 0, 0));
        txtRequiredFitnessVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRequiredFitnessValActionPerformed(evt);
            }
        });

        jLabel2.setText("Current Best Value");

        lblCurFitVal.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnStart.setFont(new java.awt.Font("Calibri", 1, 26)); // NOI18N
        btnStart.setForeground(new java.awt.Color(0, 0, 255));
        btnStart.setText("Start");
        btnStart.setName("btnStart"); // NOI18N
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        btnStop.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        btnStop.setText("Stop");
        btnStop.setToolTipText("");
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        btnConfirm.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        btnConfirm.setText("Confirm");
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(prgBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnStop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(prgBar, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        cmbTimeTableList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbTimeTableList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTimeTableListActionPerformed(evt);
            }
        });

        btnPre.setText("Previous");
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnGetFittest.setText("Get Fittest");
        btnGetFittest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetFittestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCurFitVal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtRequiredFitnessVal, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbTimeTableList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnPre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnGetFittest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmbTimeTableList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnPre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(btnGetFittest, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(txtRequiredFitnessVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCurFitVal, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private int getRequiredFitness(){
        int val = 0;
        try{
            val = Integer.parseInt(txtRequiredFitnessVal.getText());
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            
        }
        txtRequiredFitnessVal.setText(val+"");
        requiredFitness=val;
        return val;
    }
    
    class ProcessTask extends SwingWorker<Population,Population>{
        JLabel curVal;
        int reqfit;
        Population currentpop;
        ProcessTask(JLabel lblField,int req,Population pop){
            this.curVal = lblField;
            reqfit = req;
            currentpop = pop;
        }
        
        @Override
        public Population doInBackground(){
            System.out.println("Backround processing started");
            int currFitVal= Algorithm.calculateFitness(currentpop.getFittest());
            //System.out.println("calculated "+currFitVal);
            System.out.println(reqfit);
            //while(currFitVal<reqfit){
            while(reqfit>currFitVal ){
                //System.out.println("loop started");
                //if(Algorithm.DEBUG)currentpop = Algorithm.testEvolutionParams(pop);
                //else
                currentpop = Algorithm.evolvePop(currentpop);
                currFitVal = Algorithm.calculateFitness(currentpop.getFittest());
                curVal.setText(currFitVal+"");
                
                int valToSend =(int) ((currFitVal/reqfit)*100);
                if(valToSend<0)valToSend=0;
                if(valToSend>100)valToSend = 100;
                setProgress(valToSend);
                //System.out.println("Req :"+reqfit+"  Cur :"+currFitVal);
                publish(currentpop);
                if(isCancelled())break;
                //out.println("++++++++++++++++++++++++++++++++++++++++++++");
                //System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
            }
            System.out.println("Req :"+reqfit+"  Cur :"+currFitVal);
            
            return currentpop;
        }
        
        @Override
        public void process(List<Population> chunks){
            for(Population popelement : chunks){
                Main.this.pop=popelement;
                curVal.setText(Algorithm.calculateFitness(popelement.getFittest())+"");
                setFittest();
            }
        }
        
        @Override
        public void done(){
            //System.out.println("came to setting");
            setFittest();
            System.out.println("done");
        }

        private void setProgress(double valToSend) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    private void start(){
        //setTarget();
        if(getRequiredFitness()>0){
            //prgBar.setMaximum(requiredFitness);
            double currFitVal= Algorithm.calculateFitness(pop.getFittest());
            while(currFitVal<requiredFitness){
                Algorithm.evolvePop(pop);
                currFitVal = Algorithm.calculateFitness(pop.getFittest());
                lblCurFitVal.setText(currFitVal+"");
                prgBar.setValue((int) currFitVal);//this was changed from int to double thats why casted 
                //but haven't tested since its no longer used
            }
            //System.out.println(pop.getFittest());
             lblCurFitVal.setText(currFitVal+"");
             prgBar.setValue((int) currFitVal);
                setFittest();
        }
    }
    
    private void setFittest(){
       ///System.out.println("here x");
        //System.out.println("pop size"+pop.size());
        cmbTimeTableList.setSelectedIndex(0);
        
        if(pop.getFittest()==null)System.out.println("pop returns null here");
        //jTable1.setModel(new CustomeTableModel(pop.getFittest()));
        setTimeTable(pop.getFittest());
        lblCurFitVal.setText(Algorithm.calculateFitness(pop.getFittest())+"");
        //System.out.println("Done getting fittest");
    }
    
    /*
    private void setTarget(){
        TimeTableManager ttMan = new TimeTableManager();
        ttMan = new TimeTableManager();
        ttMan.setTimeSlotHeadings(new String[]{"Morning","Evening"});
        ttMan.loadClasses(3);
        ttMan.setDates(Calendar.getInstance().getTime(), 4);
        
        TimeTable tt = ttMan.getTimeTable();
        String name = "abcdefghijklmnopqrstuvwx";
        int r=0;
        for(int i=0;i<tt.countDays();i++){
            for(int j=0;j<tt.getDay(i).getTimeSlots().size();j++){
                for(int k=0;k<tt.getDay(i).getTimeSlots().get(j).getClassRooms().size();k++){
                    tt.setExam(i, j, k,new Exam(name.charAt(r)+""));
                    r++;
                }
            }
        }
        
        Algorithm.setTarget(tt);
    }*/
    
    private void txtRequiredFitnessValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRequiredFitnessValActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRequiredFitnessValActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        // TODO add your handling code here:
        //start();
        //setTarget();
        
        if(getRequiredFitness()>0){
            prgBar.setMaximum(100);
            task = new ProcessTask(lblCurFitVal,requiredFitness,pop);
            task.addPropertyChangeListener((PropertyChangeEvent evt1) -> {
                if ("progress".equals(evt1.getPropertyName())) {
                    prgBar.setValue((Integer) evt1.getNewValue());
                    setFittest();
                    System.out.println("Propert changed ");
                }
            });
            task.execute();
            //btnStop.setEnabled(true);
            
        }
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnGetFittestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetFittestActionPerformed
        // TODO add your handling code here:
        setFittest();
    }//GEN-LAST:event_btnGetFittestActionPerformed

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        // TODO add your handling code her:
        int cindex = cmbTimeTableList.getSelectedIndex();
        if(cindex!=0)
            cmbTimeTableList.setSelectedIndex(cindex-1);
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
         int cindex = cmbTimeTableList.getSelectedIndex();
        if(cindex!=cmbTimeTableList.getItemCount()-1)
            cmbTimeTableList.setSelectedIndex(cindex+1);
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        // TODO add your handling code here:
        //if(!(task.isCancelled() || task.isDone())){
            task.cancel(true);
        //}
    }//GEN-LAST:event_btnStopActionPerformed

    private void cmbTimeTableListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTimeTableListActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTimeTableListActionPerformed

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed
        // TODO add your handling code here:
        TimeTable ttToWrite = pop.getFittest();
        if(ttToWrite==null){
            JOptionPane.showMessageDialog(this, "Invalid TimeTable", "Error", JOptionPane.ABORT);
        }
        else {
            Export ex = new Export(pop.getFittest());
        }
    }//GEN-LAST:event_btnConfirmActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirm;
    private javax.swing.JButton btnGetFittest;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JComboBox<String> cmbTimeTableList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCurFitVal;
    private javax.swing.JProgressBar prgBar;
    private javax.swing.JTextField txtRequiredFitnessVal;
    // End of variables declaration//GEN-END:variables
}
