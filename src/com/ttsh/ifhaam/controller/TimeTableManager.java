/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import com.ttsh.ifhaam.models.ClassRoom;
import com.ttsh.ifhaam.models.Constraints.SameStudentConstraint;
import com.ttsh.ifhaam.models.Day;
import com.ttsh.ifhaam.models.TimeSlot;
import com.ttsh.ifhaam.models.TimeTable;

/**
 *before using the getTimeTable method please use setDates(),setTimeSlotHeadings(),loadClasses
 * @author Ahmed
 */
public class TimeTableManager {
    private Date[] dates;//these are the dates 
    private String[] timeSlotsHeadings;//these are the name of the time slots where timeSlots will be intialized form this 
    private ArrayList<ClassRoom> classRooms;//this array will be cloned and created for all the time slots
    private ArrayList<TimeSlot> timeSlots;//this timeslots will be added to all the days 
    private static TimeTableManager  instance;
    
    //this method will add classroomscount number of classrooms to the array
    //all classrooms created with this method will have a fixed size of 10 
    //=------------------------------------------------------------------
    //this method has to be changed to add roooms those are customized by the user
    //=-------------------------------------------------------------------------
    
    public static TimeTableManager getInstance(){
        if(instance==null){
            instance = new TimeTableManager ();
        }
        return instance;
    }
    
    private TimeTableManager(){
        
    }
    
    public void loadClasses(int classroomscount){
        classRooms = new ArrayList<>();
        //create an ArrayWith classRoom
        
        for(int i=0;i<classroomscount;i++){
            classRooms.add(new ClassRoom(10,"A1"+i));
        }
    }
    
    
    //this room will add a class room passed by the user to the array where user can controll all the data of that class;
    private void addClassRoom(ClassRoom classroom){
        classRooms.add(classroom);
    }
    
    private void initTimeSlots(){
        timeSlots = new ArrayList<>();
        for(String name:timeSlotsHeadings){
            timeSlots.add(new TimeSlot(name,classRooms));
        }
        
    }
    
    
    // this method will return time table 
    //adding all the classrooms to the 
    public TimeTable getTimeTable(){
        initTimeSlots();
        System.out.println("Dates Size"+dates.length);
        //System.out.println("timslots init");
        if((dates==null || dates.length==0)||
                (timeSlotsHeadings==null || !(timeSlotsHeadings.length>0))||
                (classRooms==null||!(classRooms.size()>0))||
                (timeSlots==null ||!(timeSlots.size()>0))
                ){
            System.out.println(dates.length+"");
            System.out.println("Check here");
            return null;
            
        } //on error this will return null;
        //System.out.println("create tt");
        TimeTable tt = new TimeTable();
        for(Date date :dates){
           tt.addDay(new Day(timeSlots,date));
        }
        //System.out.println("DaysCount "+tt.countDays());
        tt.addConstraint(new SameStudentConstraint());
        return tt;
    }
    
    //this method will intialize a dates array to craete time table for that days
    // where this will ignore weekends and create daysCount days from startingDate
    public void setDates(Date startingDate,int dayscount){
        Calendar cal = Calendar.getInstance();
        dates = new Date[dayscount];
        cal.setTime(startingDate);
        for(int i=0;i<dayscount;){
            if((cal.get(Calendar.DAY_OF_WEEK )==Calendar.SUNDAY)||(cal.get(Calendar.DAY_OF_WEEK )==Calendar.SATURDAY)) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
                //System.out.println(cal.get(Calendar.DAY_OF_WEEK));
                //System.out.println(Calendar.SUNDAY);
                //System.out.println(Calendar.SATURDAY);
                //System.out.println("");
            }else{
                dates[i] = cal.getTime();
                cal.add(Calendar.DAY_OF_MONTH, 1);
                i++;
            }
        }
        
        //System.out.println("dates init");
    }
   
    //this will initalize following dates and help craete time table from that days;
    private void setDates(Date[] dates){
        /*
        String[] datestemp = new String[dates.length];
        for(int i=0;i<dates.length;i++){
            datestemp[i] = dates[i].toString();
        }*/
        this.dates=dates;
    }
    
    public void setTimeSlotHeadings(String[] timeSlotHeadings){
        this.timeSlotsHeadings = timeSlotHeadings;
    }
    
    public void setClassRooms(ArrayList<ClassRoom> classRooms){
        this.classRooms = classRooms;
    }
}
