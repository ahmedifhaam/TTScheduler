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
public class Student {
    private String studentID;
    private String studentsName;
    private String degree;
    private int joinedYear;

    public Student(String studentID, String studentsName, String degree, int joinedYear) {
        this.studentID = studentID;
        this.studentsName = studentsName;
        this.degree = degree;
        this.joinedYear = joinedYear;
    }
    
    public Student(String studentsId){
        this.studentID = studentsId;
        this.studentsName = "Undefined";
        this.degree = "Undefined";
        this.joinedYear = 0;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentsName() {
        return studentsName;
    }

    public void setStudentsName(String studentsName) {
        this.studentsName = studentsName;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getJoinedYear() {
        return joinedYear;
    }

    public void setJoinedYear(int joinedYear) {
        this.joinedYear = joinedYear;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.studentID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(this.studentID.equals(((Student)obj).getStudentID())){
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
       return false;
        
    }

    @Override
    public String toString() {
        return "Student{" + "studentID=" + studentID + '}';
    }
    
    
    
}
