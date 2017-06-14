/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Ahmed
 */
public class Day {
    private ArrayList<TimeSlot>  timeSlots;
    private Date date;

    public Day(Date date) {
        this.date = date;
    }

    public Day(ArrayList<TimeSlot> timeSlots, Date date) {
        this.timeSlots = new ArrayList<>();
       // this.timeSlots = (ArrayList)timeSlots.clone();
        for(TimeSlot slot :timeSlots){
            this.timeSlots.add(new TimeSlot(slot));
        }
        this.date = date;
    }

    public ArrayList<TimeSlot> getTimeSlots() {
        return timeSlots;
    }
    
    public Exam getExam(int timeSlot,int classRoom){
        return timeSlots.get(timeSlot).getClassRooms().get(classRoom).getAssignedExam();
    }
    
    public boolean setExam(int timeSlot,int classRoom,Exam exam){
        return timeSlots.get(timeSlot).getClassRooms().get(classRoom).setExam(exam);
    }
    
    /*
    public Subject getSubject(int timeslt,int classrm){
        return timeSlots.get(timeslt).getClassRooms().get(classrm).getAssignedSubject();
    }
    
    public boolean setSubject(int timeslt,int classrm,Subject subject){
        return timeSlots.get(timeslt).getClassRooms().get(classrm).setSubject(subject);
    }*/

    public String getDate() {
        SimpleDateFormat formatter;
        String pattern = "EEE, MMM d, ''yy";
        formatter = new SimpleDateFormat(pattern);
        
        String output = formatter.format(date);
        
        return output;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public void AddTimeSlot(TimeSlot slot)    {
        timeSlots.add(slot);
    }
    
    public void removeTimeSlot(int index){
        timeSlots.remove(index);
    }

    @Override
    public String toString() {
        return "\nDay { date=" + date + "\n" + timeSlots + "\n}";
    }
    
    /*
    public boolean addSubject(Subject subject){
        TimeSlot available = null;
        for(TimeSlot slot :timeSlots){
            if(!slot.isTimeSlotFull() ){
                available = slot;
                break;
            }
        }
        if(available!=null){
            return available.addSubject(subject);
        }
        
        return false;
                
    }*/
    
    public boolean addExam(Exam exam){
        TimeSlot available =null;
        for(TimeSlot slot :timeSlots){
            if(!slot.isTimeSlotFull()){
                available = slot;
                break;
            }
        }
        if(available != null){
            return available.addExam(exam);
        }
        return false;
    }
    
    public boolean isThereFreeSlot(){
        for(TimeSlot slot:timeSlots){
            if(!slot.isTimeSlotFull()){
                return true;
            }
        }
        return false;
    }
}
