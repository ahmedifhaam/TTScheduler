/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Ahmed
 */
public class Exam {
    private ArrayList<Subject> subjects;
    
    
    public Exam(){
        subjects = new ArrayList<>();
    }
    public Exam(Subject sbj){
        this();
        addSubject(sbj);
    }
    
    public Exam(ArrayList<Subject> subjects){
        this();
        this.subjects = subjects;
    }
    
    //in this method method should check whether the students enrolled for 
    //the other subjects a;ready in the same exam
    public void addSubject(Subject subject){
        subjects.add(subject);
    }
    public int countSubjects(){
        return subjects.size();
    }
    
    public Subject getSubject(int index){
        return subjects.get(index);
    }
    
    @Override
    public String toString(){
        String output = "";
        for(Subject subject:subjects){
            output +="|"+subject.toString()+"|";
        }
        return output.length()>0?output:"null";
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }
    
    public boolean isSitting(Student std){
        for(Subject subj:subjects){
            if(subj.isSitting(std))return true;
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.subjects);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Exam other = (Exam) obj;
        if (Objects.equals(this.subjects, other.subjects)) {
            return true;
        }
        return false;
    }
    
    public int getStudentCount(){
        int total = 0;
        for(Subject sbj:subjects){
            total+=sbj.getStudentsCount();
        }
        return total;
    }
    
    
    public Exam clone(){
        Exam exam = new Exam();
        exam.subjects = (ArrayList<Subject>) this.subjects.clone();
        return exam;
    }
}
