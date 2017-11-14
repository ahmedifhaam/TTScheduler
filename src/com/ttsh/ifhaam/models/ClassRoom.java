/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models;

import java.util.Objects;

/**
 *
 * @author Ahmed
 */
public class ClassRoom {
    private int size;
    private String name;
    
    //private Subject assignedSubject;//this is used before substituting with exam for assigning multiple subjects in single room
    private Exam assignedExam;
    
    
    public ClassRoom(ClassRoom room){
        this.size = room.getSize();
        this.name = room.getName();
        
        //this.assignedSubject = room.getAssignedSubject();
        this.assignedExam = room.getAssignedExam();
                
    }

    public ClassRoom(int size, String name) {
        this.size = size;
        this.name = name;
        
    }

   

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public Exam getAssignedExam() {
        return assignedExam;
    }

    /**
     * This method will replaces the assigned exam instance variable
     * This wont check it is null or not before assigning
     * @param assignedExam this will replace assigned exam instance variable
     * 
     */
    public void setAssignedExam(Exam assignedExam) {
        this.assignedExam = assignedExam;
    }

    /**
     * If the assigned Exam instance variable is null this method 
     * will replace the null value of that variable with the object exam
     * @param exam the object which will be assigned as the assigned exam variable
     * @return 
     */
    public boolean setExam(Exam exam){
        
        if(getAssignedExam()!=null){
            return false;
        }else{
            assignedExam = exam;
            
            return true;
        }
    }
    
    
    /*
    public Subject getAssignedSubject() {
        return assignedSubject;
    }

    public void setAssignedSubject(Subject assignedSubject) {
        this.assignedSubject = assignedSubject;
    }
    
    
    public boolean addSubject(Subject subject){
        if(isOccupied()){
            return false;
        }else{
            assignedSubject = subject;
            setOccupied(true);
            return true;
        }
    }
    

    public boolean setSubject(Subject subject){
        assignedSubject = subject;
        return true;
    }
        */
    
    public String toString(){
        //return "CR ( "+ getSize()+ " ): ["+name+"] :"+assignedExam;
        return name +" : "+assignedExam;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        
        final ClassRoom other = (ClassRoom) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    public boolean isSitting(Student std){
        if(assignedExam==null) return false;
        return assignedExam.isSitting(std);
    }
    
    public boolean contains(Subject sub){
        if(assignedExam==null){
            return false;
        }else{
            if(!assignedExam.getSubjects().contains(sub)){
                return false;
            }
        }
        return true;
    }
    
    @Override
    public ClassRoom clone(){
        ClassRoom cr = new ClassRoom(size,name);
        cr.assignedExam = this.assignedExam.clone();
        return cr;
    }
}
