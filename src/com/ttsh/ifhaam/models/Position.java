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
    

    public Position(int day, int timeSlot, int classRoom) {
        this.day = day;
        this.timeSlot = timeSlot;
        this.classRoom = classRoom;
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
    
}
