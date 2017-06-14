/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam;

import com.ttsh.ifhaam.controller.Algorithm;
import com.ttsh.ifhaam.controller.TimeTableManager;
import java.util.ArrayList;
import com.ttsh.ifhaam.models.ClassRoom;
import com.ttsh.ifhaam.models.Day;
import com.ttsh.ifhaam.models.Population;
import com.ttsh.ifhaam.models.Subject;
import com.ttsh.ifhaam.models.TimeSlot;
import com.ttsh.ifhaam.models.TimeTable;
import java.util.Calendar;

/**
 *
 * @author Ahmed
 */
public class TimeTableSchedulerForUOKScience {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       Population pop = new Population( 3,true);
       //System.out.println(pop.getTimeTable(0));
       //System.out.println(pop.getTimeTable(1));
       Algorithm.crossOver(pop.getTimeTable(0), pop.getTimeTable(1));
       
       //System.out.println("******************************************************");
       //System.out.println(pop.getTimeTable(1));
        //pop.getTimeTable(0).setSubject(0, 0, 0,pop.getTimeTable(1).getSubject(0, 0, 0));
        //System.out.println(pop.getTimeTable(0));
        //System.out.println(pop.getTimeTable(1));
        //System.out.println(pop.getTimeTable(2));
        
        TimeTableManager ttMan = TimeTableManager.getInstance();
        //TimeTableManager ttMan = new TimeTableManager();
        // ttMan = new TimeTableManager();
        ttMan.setTimeSlotHeadings(new String[]{"Morning","Evening"});
        ttMan.loadClasses(3);
        ttMan.setDates(Calendar.getInstance().getTime(), 4);
        
        TimeTable tt = ttMan.getTimeTable();
        String name = "abcdefghijklmnopqrstuvwx";
        int r=0;
        for(int i=0;i<tt.countDays();i++){
            for(int j=0;j<tt.getDay(i).getTimeSlots().size();j++){
                for(int k=0;k<tt.getDay(i).getTimeSlots().get(j).getClassRooms().size();k++){
                    //tt.setSubject(i, j, k,new Subject(name.charAt(r)+""));
                    r++;
                }
            }
        }
        
        Algorithm.setTarget(tt);
        int gen =0;
        while(Algorithm.calculateFitness(pop.getFittest())<24){
            
            System.out.println("Gen "+gen);
            System.out.println("Fitness : "+Algorithm.calculateFitness(pop.getFittest()));
            //pop = 
            Algorithm.evolvePop(pop);
            gen++;
            //System.out.println(pop.getFittest());
        }
        System.out.println(pop.getFittest());
    }
    
}
