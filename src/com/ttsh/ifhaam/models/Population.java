/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models;

import com.ttsh.ifhaam.controller.Algorithm;
import com.ttsh.ifhaam.controller.DataBaseController;
import com.ttsh.ifhaam.controller.TimeTableManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Ahmed
 */
public class Population {
    private TimeTable[] timeTables;
    TimeTableManager ttMan;
    public ArrayList<Exam> exams;
    
    private static final int POPULATION_SIZE = 3;
    
    public Population(boolean intialize,ArrayList<ClassRoom> classrooms,String[] timeSlots,int daysCount,Date startingDate){
        //ttMan = new TimeTableManager();
        ttMan = TimeTableManager.getInstance();
        System.out.println(timeSlots.length+" time slot size");
        ttMan.setTimeSlotHeadings(timeSlots);
        ttMan.setClassRooms(classrooms);
        ttMan.setDates(startingDate,daysCount);
        
        initExams();
        timeTables = new TimeTable[POPULATION_SIZE];//population size
        for(int j=0;j<timeTables.length;j++){
            TimeTable tt = ttMan.getTimeTable();
            if(intialize){
                for(int i=0;i<exams.size();i++){
                    //System.out.println("Size of exams here "+exams.size());
                    if(!tt.addExam(exams.get(i))) {
                        System.out.println("Failed : Exam - "+exams.get(i));
                    }
                }
            }
            timeTables[j] = tt;
        }
    }
    
    public Population(){
        ttMan = TimeTableManager.getInstance();
        boolean intialize = false;
         timeTables = new TimeTable[POPULATION_SIZE];//population size
        for(int j=0;j<timeTables.length;j++){
            TimeTable tt = ttMan.getTimeTable();
            if(intialize){
                for(int i=0;i<exams.size();i++){
                    //System.out.println("Size of exams here "+exams.size());
                   if(!tt.addExam(exams.get(i))) System.out.println("Failed : Exam - "+exams.get(i));
                }
            }
            timeTables[j] = tt;
        }
    }
    
    
    public Population(int size,boolean initialize){
        ttMan = TimeTableManager.getInstance();
        //ttMan = new TimeTableManager();
                
        //initSubjects();
        initExams();
        timeTables = new TimeTable[size];
        for(int j=0;j<timeTables.length;j++){
            TimeTable tt = ttMan.getTimeTable();
            if(initialize){
                for(int i =0;i<exams.size();i++){
                    if(!tt.addExam(exams.get(i)))System.out.println("failed : Exam-"+exams.get(i));
                }
            }
            timeTables[j] = tt;
        }
    }
    /*
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
                   
    }*/
    
    /*
    private void initSubjects(){
        subjects = new ArrayList<>();
        String sbjectCode = "abcdefghijklmnopqrstuvwx";
        for(int i=0;i<24;i++){
           subjects.add(new Subject(sbjectCode.charAt(i)+""));
        }
    }*/
    
    private void initExams(){
        exams = new ArrayList<>();
        DataBaseController dbc = DataBaseController.newInstance();
        ArrayList<Subject> subjects = dbc.getSubjects();
        for(Subject sbj :subjects ){
            Exam e = new Exam(sbj);
            exams.add(e);
        }
        //System.out.println("Size of exams "+exams.size());
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
        if(tt==null)System.out.println("its null here");
        //System.out.println("calulating :"+tt.countDays());
        for(int i=1;i<size();i++){
            if(Algorithm.calculateFitness(tt)<Algorithm.calculateFitness(timeTables[i])){
                tt = timeTables[i];
            }
        }
        return tt;
    }
    
    
}
