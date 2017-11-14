/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.controller;

import com.ttsh.ifhaam.models.ClassRoom;
import com.ttsh.ifhaam.models.Day;
import com.ttsh.ifhaam.models.Exam;
import com.ttsh.ifhaam.models.TimeSlot;
import com.ttsh.ifhaam.models.TimeTable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author Ahmed
 */
public class ExcelWrite {
    File file;
    public ExcelWrite(File file){
        this.file = file;
    }
    
    public void writeTimeTable(TimeTable tt) throws IOException, WriteException{
        WritableWorkbook wworkbook;
      wworkbook = Workbook.createWorkbook(file);
      WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);
      
      for(int i =0;i<tt.countDays();i++){
          Label label = new Label(i,0,tt.getDay(i).getDate().toString());
          wsheet.addCell(label);
          ArrayList<TimeSlot> timeSlots = tt.getDay(i).getTimeSlots();
          for(int j=0;j<timeSlots.size();j++){
              int classRoomscount = j+1+(timeSlots.get(0).getClassRooms().size()*j)+(j*1);
              label = new Label(i,classRoomscount,timeSlots.get(j).getIdentifier());
              wsheet.addCell(label);
              ArrayList<ClassRoom> classRooms = timeSlots.get(j).getClassRooms();
              for(int k=0;k<classRooms.size();k++){
                  ClassRoom cr = classRooms.get(k);
                  Exam ex = cr.getAssignedExam();
                  String assignedExamStr = ex==null?"-":ex.toString();
                  label = new Label(i,classRoomscount+1+k,cr.getName()+" ("+cr.getSize()+")"+assignedExamStr);
                  wsheet.addCell(label);
              }
          }
      }
      
      //Label label = new Label(0, 2, "A label record");
      //wsheet.addCell(label);
      //jxl.write.Number number = new jxl.write.Number(3, 4, 3.1459);
      //wsheet.addCell(number);
      wworkbook.write();
      wworkbook.close();
    }
}
