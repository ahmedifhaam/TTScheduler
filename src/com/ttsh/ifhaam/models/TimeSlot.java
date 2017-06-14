    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models;

import java.util.ArrayList;

/**
 *
 * @author Ahmed
 */
public class TimeSlot {
    private String identifier;
    private ArrayList<ClassRoom> classRooms;

    public TimeSlot(String identifier) {
        this.identifier = identifier;
        classRooms = new ArrayList<>();
    }
    
    public TimeSlot(TimeSlot slot){
        this.identifier = slot.getIdentifier();
        classRooms = new ArrayList<>();
        for(ClassRoom room: slot.getClassRooms()){
            this.classRooms.add(new ClassRoom(room));
        }
    }
    
    public TimeSlot(String identifier,ArrayList<ClassRoom> classRooms){
        this.identifier  = identifier;
        this.classRooms = (ArrayList<ClassRoom>)classRooms.clone();
    }
    
    /*
    public boolean addSubject(Subject subject){
        int selectedRoom = -1; 
        while (true){
            selectedRoom = (int)Math.floor(Math.random()*classRooms.size());
            if(!classRooms.get(selectedRoom).isOccupied()){
                break;
            }
        }
        return classRooms.get(selectedRoom).addSubject(subject);
        
    }*/
    
    public boolean addExam(Exam exam){
        int selectedRoom = -1;
        while(true){
            selectedRoom = (int)Math.floor(Math.random()*classRooms.size());
            if(!classRooms.get(selectedRoom).isOccupied()){
                break;
            }
        }
        return classRooms.get(selectedRoom).setExam(exam);
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ArrayList<ClassRoom> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(ArrayList<ClassRoom> classRooms) {
        this.classRooms = classRooms;
    }
    
    
    public boolean isTimeSlotFull(){
        for(ClassRoom clsRoom:classRooms){
            if(!clsRoom.isOccupied()){
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String toString(){
        String outputString="\n----------TimeSlot-["+identifier+"]------------\n";
        for(ClassRoom room :classRooms){
            outputString+="| "+room+" |";
        }
        outputString+=       "\n-----------------------------------------------\n";
        return outputString;
    }
}
