/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models;

import com.ttsh.ifhaam.controller.Algorithm;
import com.ttsh.ifhaam.controller.TimeTableManager;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Ahmed
 */
public class Population {
    private TimeTable[] timeTables;
    TimeTableManager ttMan;
    public ArrayList<Subject> subjects;
    
    
    public Population(int size,boolean initialize){
        ttMan = new TimeTableManager();
        ttMan.setTimeSlotHeadings(new String[]{"Morning","Evening"});
        ttMan.loadClasses(3);
        ttMan.setDates(Calendar.getInstance().getTime(), 4);
        
        initSubjects();
        timeTables = new TimeTable[size];
        for(int j=0;j<timeTables.length;j++){
            TimeTable tt = ttMan.getTimeTable();
            //System.out.println("Time Table one complete");
            if(initialize){
                for(int i =0;i<subjects.size();i++){
                    if(!tt.addSubject(subjects.get(i)))System.out.println("failed "+subjects.get(i));
                }
            }
            //System.out.println(tt);
            timeTables[j]=tt;
        }
        //System.out.println(timeTables[0]);
                   
    }
    
    private void initSubjects(){
        subjects = new ArrayList<>();
        String sbjectCode = "abcdefghijklmnopqrstuvwx";
        for(int i=0;i<24;i++){
           subjects.add(new Subject(sbjectCode.charAt(i)+""));
        }
    }
    
    public int size(){
        return timeTables.length;
    }
    
    public void saveTimeTable(int index,TimeTable tt){
        timeTables[index] = tt;
    }
    
    public TimeTable getTimeTable(int index){
        return timeTables[index];
    }
            
    public TimeTable getFittest(){
        TimeTable tt = timeTables[0];
        for(int i=1;i<size();i++){
            if(Algorithm.calculateFitness(tt)<Algorithm.calculateFitness(timeTables[i])){
                tt = timeTables[i];
            }
        }
        return tt;
    }
}
