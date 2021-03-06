/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.scheduler.pkgfor.uokscience.models;

import java.util.ArrayList;

/**
 *
 * @author Ahmed
 */
public class Day {
    private ArrayList<TimeSlot>  timeSlots;
    private String date;

    public Day(String date) {
        this.date = date;
    }

    public Day(ArrayList<TimeSlot> timeSlots, String date) {
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
    
    public Subject getSubject(int timeslt,int classrm){
        return timeSlots.get(timeslt).getClassRooms().get(classrm).getAssignedSubject();
    }
    
    public boolean setSubject(int timeslt,int classrm,Subject subject){
        return timeSlots.get(timeslt).getClassRooms().get(classrm).setSubject(subject);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
