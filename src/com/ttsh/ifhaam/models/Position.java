/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models;

/**
 *
 * @author Ahmed
 */
public class Position {
    int day;
    int timeSlot;
    int classRoom;
    private TimeTable tt;

    public Position(int day, int timeSlot, int classRoom) {
        this.day = day;
        this.timeSlot = timeSlot;
        this.classRoom = classRoom;
    }
    
    public Position(TimeTable tt){
        this.tt = tt;
        day=0;
        timeSlot=0;
        classRoom=0;
    }
    
    

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(int classRoom) {
        this.classRoom = classRoom;
    }
    
    public String toString(){
        return "day : "+day+" | timeSlot : "+timeSlot+" | classRoom : "+classRoom;
    }
    
    
    public Position randomPosition(){
        int day = (int)(Math.random()*tt.countDays());
        int timeSlot = (int)(Math.random()*tt.getDay(day).getTimeSlots().size());
        int classRoom = (int)(Math.random()*tt.getDay(day).getTimeSlots().get(timeSlot).getClassRooms().size());
        setDay(day);
        setTimeSlot(timeSlot);
        setClassRoom(classRoom);
        return this;
    }
    
    public Position next(){
        if(tt.getDay(day).getTimeSlots().get(timeSlot).getClassRooms().size()-1>classRoom){
            classRoom++;
        }else{
            if(tt.getDay(day).getTimeSlots().size()-1>timeSlot){
                timeSlot++;
                classRoom=0;
            }else{
                if(tt.getDays().size()-1>day){
                    day++;
                    timeSlot=0;
                    classRoom=0;
                }
            }
        }
        return this;
    }
    
    public boolean isEnd(){
        if(day==tt.getDays().size()-1){
            if(timeSlot==tt.getDay(day).getTimeSlots().size()-1){
                if(classRoom ==tt.getDay(day).getTimeSlots().get(timeSlot).getClassRooms().size()-1){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
