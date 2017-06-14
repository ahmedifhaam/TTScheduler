/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models;

import com.ttsh.ifhaam.models.Constraints.Constraint;
import java.util.ArrayList;

/**
 *@author Ahmed
 */
public class TimeTable {
    
    private ArrayList<Day> days;
    private ArrayList<Constraint> constraints;

    public ArrayList<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(ArrayList<Constraint> constraints) {
        this.constraints = constraints;
    }
    public TimeTable(){
        days = new ArrayList<>();
        constraints = new ArrayList<>();
    }
    
    public void addConstraint(Constraint cont){
        constraints.add(cont);
    }
    
    public void addDay (Day day ){
        days.add(day);
    }
    
    public int countDays(){
        return days.size();
    }
    
    public Exam getExam (int day,int timeslot,int classrm){
        return days.get(day).getExam(timeslot, classrm);
    }
    
    public Exam getExam(Position pos){
        return days.get(pos.getDay()).getExam(pos.getTimeSlot(), pos.getClassRoom());
    }
    
    public boolean setExam(int day,int timeSlt,int classRm,Exam exam){
        return days.get(day).setExam(timeSlt, classRm, exam);
    }
    
    public boolean addExam(Exam exam){
        //check whether there is a free slot and add
        boolean result = false;
        int selectedDay = (int)Math.floor(Math.random()*(days.size()));
        int ite = selectedDay;
        //move next from randomly selected day
        while (!result){
            if(days.get(ite).addExam(exam)) result = true;
            ite++;
            if(ite==days.size())break;
        }
        //move back from randomly selected day
        while(!result){
            if(days.get(selectedDay).addExam(exam)) result = true;
            selectedDay--;
            if(selectedDay<0) break;
        }
        return result;
    }
    
    /*
    public Subject getSubject(int day,int timeslt,int classrm){
        return days.get(day).getSubject(timeslt, classrm);
    }
    
    public boolean setSubject(int day,int timeslt,int classrm,Subject subject){
        return days.get(day).setSubject(timeslt,classrm,subject);
    }
    
    public boolean addSubject(Subject subject){
        //check whether there is a free slot 
        boolean result = false;
        int selectedDay = (int)Math.floor(Math.random()*(days.size()));
        int ite = selectedDay;
        while(!result){
            if(days.get(ite).addSubject(subject)) result=true;
            ite++;
            if(ite==days.size())break;
        }
        while(!result){
            if(days.get(selectedDay).addSubject(subject)) result=true;
            selectedDay--;
            if(selectedDay<0)break;
        }
        return result;
    }*/

    public Day getDay(int index){
        return days.get(index);
    }
    
    public ArrayList<Day> getDays(){
        return days;
    }
    
    @Override
    public String toString() {
        String outString="Time Table";
        for(Day day:days){
            outString+=day;
        }
        outString+=">>>>>>>>>>>>>>>>END<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";
        return outString;
    }
    
    public int subjectCount(){
        return (days.size()* days.get(0).getTimeSlots().size()*days.get(0).getTimeSlots().get(0).getClassRooms().size());
    }
    
    public boolean contains(Exam exam){
        for(Day day:days){
            for(TimeSlot ts:day.getTimeSlots()){
                for(ClassRoom cr:ts.getClassRooms()){
                    if(cr.getAssignedExam()!=null){
                        if(cr.getAssignedExam().equals(exam))return true;
                    }
                }
            }
        }
        return false;
    }
    
    /*
    public boolean contains(Subject subject){
        for(Day day:days){
            for(TimeSlot ts:day.getTimeSlots()){
                for(ClassRoom cr :ts.getClassRooms()){
                    if(cr.getAssignedSubject()!= null)
                        if(cr.getAssignedSubject().equals(subject))return true;
                }
            }
        }
        
        return false;
    }*/
    
}
