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
public class Subject {
    
    private String subjectCode;
    private ArrayList<Student> students;
    private int yearWriting;// this is the year students have enrolled for the course
    private boolean isRepeat;
    private ClassRoom classroom;

    private int offeredYear;
    private int offeredSemester;
    private Module module;
    
    public ClassRoom getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassRoom classroom) {
        this.classroom = classroom;
    }
    
    public Subject(String subjectCode) {
        this.subjectCode = subjectCode;
        
        isRepeat=false;
    }
    
    public Subject(String subjectCode,int isRepeat){
        this(subjectCode);
        if(isRepeat==1){
            this.isRepeat = true;
        }
    }
    
    public Subject(String subjectCode,int yearWriting,int offeredYear,int offeredSemester,int isRepeat){
        this.subjectCode = subjectCode;
        this.yearWriting = yearWriting;
        this.offeredSemester = offeredSemester;
        this.offeredYear = offeredYear;
        this.isRepeat = isRepeat==1?true:false;
    }
    
    public Subject(String subjectCode,Boolean repeat){
        this(subjectCode);
        isRepeat=repeat;
    }

    public String getSubjectCode() {
        return subjectCode;
        
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public int getWritingYear() {
        return yearWriting;
    }

    public void setWritingYear(int year) {
        this.yearWriting = year;
    }
    
    public boolean isIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    
    
    
    public int getYear(){
        return offeredYear;
    }
    
    public int getSemester(){
        return offeredSemester;
    }
    
    public int getCredit(){
        return Integer.parseInt(subjectCode.charAt(subjectCode.length()-1)+"");
    }
    
    public void addStudent(Student student){
        if(students == null) {
            students = new ArrayList<>();
        }
        students.add(student);
    }
    
    public void removeStudentAt(int index){
        students.remove(index);
    }

    public int getStudentsCount(){
        return students.size();
    }
    
    @Override
    public String toString(){
        if(subjectCode==null)subjectCode = "null";
        return " "+subjectCode+" ";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Subject other = (Subject) obj;
        if (!Objects.equals(this.subjectCode, other.subjectCode)) {
            return false;
        }
        if(offeredYear!=other.getYear()){
            return false;
        }
        if(offeredSemester!=other.getSemester()){
            return false;
        }
        if(yearWriting !=other.getWritingYear()){
            return false;
        }
        if(other.isRepeat != this.isRepeat){
            return false;
        }
        return true;
    }
    
    public boolean isSitting(Student std){
        if(students.contains(std))return true;
        else return false;
                
    }
    
    public Subject clone(){
        Subject sbj = new Subject(subjectCode, yearWriting, offeredYear, offeredSemester, isRepeat?1:0);
        return sbj;
    }
    
}
