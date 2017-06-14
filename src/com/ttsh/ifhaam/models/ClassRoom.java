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
    private boolean occupied;
    //private Subject assignedSubject;//this is used before substituting with exam for assigning multiple subjects in single room
    private Exam assignedExam;
    
    
    public ClassRoom(ClassRoom room){
        this.size = room.getSize();
        this.name = room.getName();
        this.occupied = room.isOccupied();
        //this.assignedSubject = room.getAssignedSubject();
        this.assignedExam = room.getAssignedExam();
                
    }

    public ClassRoom(int size, String name, boolean occupied) {
        this.size = size;
        this.name = name;
        this.occupied = occupied;
    }

    public ClassRoom(int size, String name) {
        this.size = size;
        this.name = name;
        occupied = false;
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

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Exam getAssignedExam() {
        return assignedExam;
    }

    public void setAssignedExam(Exam assignedExam) {
        this.assignedExam = assignedExam;
    }

    public boolean setExam(Exam exam){
        
        if(isOccupied()){
            return false;
        }else{
            assignedExam = exam;
            setOccupied(true);
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
        return "ClassRoom ( "+ getSize()+ " ): ["+name+"] :"+assignedExam;
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
        if (getClass() != obj.getClass()) {
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
}
