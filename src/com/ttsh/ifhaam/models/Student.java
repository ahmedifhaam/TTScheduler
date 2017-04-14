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
    
    
    
}
