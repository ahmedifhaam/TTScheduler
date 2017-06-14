/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.controller;

import java.util.ArrayList;
import com.ttsh.ifhaam.models.ClassRoom;
import com.ttsh.ifhaam.models.Student;
import com.ttsh.ifhaam.models.Subject;

/**
 *
 * @author Ahmed
 */
public class DataBaseControllerTest {
    public static void main(String[] args){
        DataBaseController dbc = DataBaseController.newInstance();
        /* test for retrieveing classRooms
        ArrayList<ClassRoom> crs = dbc.getClassRooms();
        for(ClassRoom cr :crs){
            System.out.println(cr);
        }*/
        
        ArrayList<Subject> subjects = dbc.getSubjects();
        
        for(Subject sb :subjects){
            System.out.println("_______________________________________________");
            System.out.println("Subject : "+sb.getSubjectCode());
            System.out.println("-----------------------------------------");
            for(Student std : sb.getStudents()){
                System.out.println(std.getStudentID()+"");
            }
            System.out.println("-----------------------------------------");
        }
        System.out.println(subjects.size()+"");
    }
}
