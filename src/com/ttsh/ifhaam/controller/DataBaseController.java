/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.controller;

import com.ttsh.ifhaam.models.ClassRoom;
import com.ttsh.ifhaam.models.Student;
import com.ttsh.ifhaam.models.Subject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed
 */
public class DataBaseController {
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/timetabler";
        
    static final String USER = "root";
    static final String PASS = "";
    
    Connection conn;
    Statement stmnt;
    
    private static DataBaseController instance = null;
    
    private DataBaseController(){
        
    }
    
    public static DataBaseController newInstance(){
        if(instance==null){
            instance = new DataBaseController();
        }
        return instance;
    }
    
    //creates the connection to the database and opens the connections
    private void connectDataBase(){
       try{
            Class.forName(JDBC_DRIVER); 
            
            System.out.println("Connecting to the database");
            conn = DriverManager.getConnection(DB_URL, USER,PASS);
            stmnt = conn.createStatement();
            
        }catch(ClassNotFoundException ex){
            System.out.println("Database Driver Not Found - "+ex.getMessage());
        }catch(SQLException ex){
            System.out.println("Connecting to Database Error - "+ex.getMessage());
        }
    }
    
    //clear the environment and close any open connections
    private void connectionClose(){
        try {
            stmnt.close();
            
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Closing Error - "+ex.getMessage());
        }
        
    }
    
    
    //get all the class rooms available in the system
    public ArrayList<ClassRoom> getClassRooms(){
        connectDataBase();
        ArrayList<ClassRoom> classRooms = new ArrayList<>();
        String sql = "SELECT _id,name,size from class_rooms";
        try {
            ResultSet rs = stmnt.executeQuery(sql);
            while(rs.next()){
                ClassRoom  cr = new ClassRoom(rs.getInt("size"),rs.getString("name"),false);
                classRooms.add(cr);
            }
        } catch (SQLException ex) {
            System.out.println("Executing Statement SQL Error");
        }
        connectionClose();
        return classRooms;
    }
    
    //returnss all the subjects with the registered students under that subject
    public ArrayList<Subject> getSubjects(){
        connectDataBase();
        ArrayList<Subject> result = new ArrayList<>();
        String sql = "SELECT code,year,offered_year,offered_semester,is_repeat,"
                + "subject_id,student_id FROM `subjects` INNER JOIN `subject_student`"
                + " ON subjects._id=subject_student.subject_id INNER JOIN modules "
                + "ON modules._id =subjects.module_id ";
        
        try{
            ResultSet rs = stmnt.executeQuery(sql);
            
            while(rs.next()){
                 Student student = new Student(rs.getString("student_id"));
                 
                 //if the subjects list empty or its a new subject add a new subject
                 //add the students to the relevent subject
                if(!result.isEmpty()){
                    Subject subj = result.get(result.size()-1);
                    if(subj.getSubjectCode().equals(rs.getString("code"))){
                        subj.addStudent(student);
                        continue;
                    }
                }         
                Subject sb =  new Subject(rs.getString("code"),rs.getInt("year")
                    ,rs.getInt("offered_year"),rs.getInt("offered_semester"));
                sb.addStudent(student);
                result.add(sb);
                 /*
                if(result.isEmpty()){
                    Subject sb =  new Subject(rs.getString("code"),rs.getInt("year")
                    ,rs.getInt("offered_year"),rs.getInt("student_id"));
                    
                    sb.addStudent(student);
                }else{
                    Subject subj = result.get(result.size()-1);
                    
                    if(subj.getSubjectCode().equals(rs.getString("code"))){
                        subj.addStudent(student);
                    }
                }*/
            }
        }catch(SQLException ex){
            System.out.println("Executing Statement SQL Error "+ex.getMessage());
            
        }
        return result;
    }
}
