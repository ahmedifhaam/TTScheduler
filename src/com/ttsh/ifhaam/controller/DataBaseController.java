/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.controller;

import com.ttsh.ifhaam.models.ClassRoom;
import com.ttsh.ifhaam.models.CombinedClassRoom;
import com.ttsh.ifhaam.models.SingleClassRoom;
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
import javax.swing.JOptionPane;

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
            JOptionPane.showMessageDialog(null,"Error Connecting to"
                    + " DataBase Please Fix the issue and try again", "Error", JOptionPane.WARNING_MESSAGE);
        }catch(SQLException ex){
            System.out.println("Connecting to Database Error - "+ex.getMessage());
             JOptionPane.showMessageDialog(null,"Error Connecting to"
                    + " DataBase Please Fix the issue and try again", "Error", JOptionPane.WARNING_MESSAGE);
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
        String sql = "SELECT _id,name,size,type from class_rooms";
        ClassRoom cr;
        try {
            ResultSet rs = stmnt.executeQuery(sql);
            while(rs.next()){
                if(rs.getInt("type")==1){
                    cr = new SingleClassRoom(rs.getInt("size"),rs.getString("name"));
                }else{
                    //code for the combined classRoom
                    String sql2 = "select * from combined_class_rooms where class_room_id = '"+rs.getString("name")+"'";
                    Statement stmnt2 = conn.createStatement();
                    ResultSet rs2 = stmnt2.executeQuery(sql2);
                    cr =  new CombinedClassRoom(rs.getInt("size"),rs.getString("name"));
                    while(rs2.next()){
                        ((CombinedClassRoom)cr).addClassRoom(new SingleClassRoom(0,rs2.getString("class_room_combined_id")));
                    }
                }
                //ClassRoom  cr = new ClassRoom(rs.getInt("size"),rs.getString("name"));//comment this line after implementing the condition
                classRooms.add(cr);
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"Error Connecting to"
                    + " DataBase Please Fix the issue and try again", "Error", JOptionPane.WARNING_MESSAGE);
        }catch(Exception ex){
             JOptionPane.showMessageDialog(null,"Error Connecting to"
                    + " DataBase Please Fix the issue and try again", "Error", JOptionPane.WARNING_MESSAGE);
        }
        connectionClose();
        return classRooms;
    }
   
    public ArrayList<ClassRoom> getSingleClassRooms(){
        connectDataBase();
        ArrayList<ClassRoom> classRooms = new ArrayList<>();
        String sql = "SELECT _id,name,size from class_rooms where type=1";//type 1 means single zero means combined
        try {
            ResultSet rs = stmnt.executeQuery(sql);
            while(rs.next()){
                SingleClassRoom  cr = new SingleClassRoom(rs.getInt("size"),rs.getString("name"));
                classRooms.add(cr);
            }
        } catch (SQLException ex) {
            System.out.println("Executing Statement SQL Error");
        }
        connectionClose();
        return classRooms;
    }
    
    public boolean insertOrUpdateClassRoom(ClassRoom classroom){
        boolean result = false;
        connectDataBase();
        int type = 0;
        if(classroom instanceof SingleClassRoom){
            type = 1;
        }
        String sql="insert into class_rooms(name,size,type) values('"+classroom.getName()+"','"+classroom.getSize()+"','"+type+"')"
                + "ON DUPLICATE KEY UPDATE name = values(name),size=values(size),type=values(type);";
        //System.out.println(sql);
        try{
            stmnt.executeUpdate(sql);
            
            if(type==0){
                for(ClassRoom combinedClassRoom:((CombinedClassRoom)classroom).getCombinedRooms()){
                    String sql2 = "insert into combined_class_rooms(class_room_id,class_room_combined_id)"
                            + "values('"+classroom.getName()+"','"+combinedClassRoom.getName()+"')"
                            + "ON DUPLICATE KEY UPDATE class_room_id=values(class_room_id),class_room_combined_id=values(class_room_combined_id);";
                    
                    
                    stmnt.execute(sql2);
                   // System.out.println(sql2);
                }
            }
            result =true;
        }catch(SQLException ex){
            System.out.println("Executing sql Statement error"+ex);
        }
        connectionClose();
        return result;
    }
    
    /* with the new getSubjects method this method should not be used
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
                }
            }
            connectionClose();
        }catch(SQLException ex){
            System.out.println("Executing Statement SQL Error "+ex.getMessage());
            
        }
        return result;
    }*/
    /**
     * This method will retrieve subjects from database 
     * according to the parameters provided
     * for the parameter pass zero to ignore that parameter in the search query
     * @param year This is the year query value 
     * @param offeredYear This is the parameter to filter result based on the year course offered 
     * @param offeredSemester Parameter which will filter the subjects by offered Semester
     * @param isRepeat this parameter marks whether its a repeat subject or not pass -1 to ignore
     * @return returns ArrayList with Subject elements 
     */
    public ArrayList<Subject> getSubjects(String year,int offeredYear,int offeredSemester,int isRepeat){
        ArrayList<Subject> subjects = new ArrayList<>();
        
        try{
            connectDataBase();
            String sql = "SELECT code,year,offered_year,offered_semester,is_repeat,"
                + "subject_id,student_id FROM `subjects` INNER JOIN `subject_student`"
                + " ON subjects._id=subject_student.subject_id INNER JOIN modules "
                + "ON modules._id =subjects.module_id WHERE code!='' ";
            
            if((!year.equals("0"))){
                sql+= "AND year='"+year+"' ";
            }
            if(offeredYear>0){
                sql+= "AND offered_year="+offeredYear+" ";
            }
            
            if(offeredSemester>0){
                sql+= "AND offered_semester="+offeredSemester+" ";
            }
            
            if(isRepeat>-1 && isRepeat<2){
                sql+= "AND is_repeat="+isRepeat+"";
            }
            
            ResultSet rs = stmnt.executeQuery(sql);
            
            while(rs.next()){
                 Student student = new Student(rs.getString("student_id"));
                 
                 //if the subjects list empty or its a new subject add a new subject
                 //add the students to the relevent subject
                 Subject sb =  new Subject(rs.getString("code"),rs.getInt("year")
                    ,rs.getInt("offered_year"),rs.getInt("offered_semester"),rs.getInt("is_repeat"));
                if(!subjects.isEmpty()){
                    Subject subj = subjects.get(subjects.size()-1);
                    //Subject subjtemp = new Subject(rs.getString("code"),rs.getInt("is_repeat"));
                    
                    if(subj.equals(sb)){//check for repeat too
                        subj.addStudent(student);
                        continue;
                    }
                }         
                //Subject sb =  new Subject(rs.getString("code"),rs.getInt("year")
                    //,rs.getInt("offered_year"),rs.getInt("offered_semester"));
                sb.addStudent(student);
                subjects.add(sb);
            }
            connectionClose();
        }catch(SQLException ex){
            System.out.println("Error retrieving data "+ex.getMessage());
        }
        
        
        return subjects;
    }
}
