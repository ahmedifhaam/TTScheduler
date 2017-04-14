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
    //INTE 22543
    //first one year
    //second semester 
    // last credit
    private String subjectCode;
    private ArrayList<Student> students;
    private int enrolledYear;// this is the year students have enrolled for the course
    private boolean isRepeat;
    private ClassRoom classroom;

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

    public int getEnrolledYear() {
        return enrolledYear;
    }

    public void setEnrolledYear(int year) {
        this.enrolledYear = year;
    }
    
    public boolean isIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }
    
    public int getYear(){
        return Integer.parseInt(subjectCode.charAt(0)+"");
    }
    
    public int getSemester(){
        return Integer.parseInt(subjectCode.charAt(2)+"");
    }
    
    public int getCredit(){
        return Integer.parseInt(subjectCode.charAt(subjectCode.length()-1)+"");
    }
    
    public void addStudent(Student student){
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
        return true;
    }
    
    
}
