/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.scheduler.pkgfor.uokscience.models;

import java.util.ArrayList;

/**
 *@author Ahmed
 */
public class TimeTable {
    
    private ArrayList<Day> days;
    public TimeTable(){
        days = new ArrayList<>();
    }
    
    public void addDay (Day day ){
        days.add(day);
    }
    
    public int countDays(){
        return days.size();
    }
    
    public Subject getSubject(int day,int timeslt,int classrm){
        return days.get(day).getSubject(timeslt, classrm);
    }
    
    public boolean setSubject(int day,int timeslt,int classrm,Subject subject){
        return days.get(day).setSubject(timeslt,classrm,subject);
    }
    
    public boolean addSubject(Subject subject){
        //check whether there is a free slot 
        boolean result = false;
        int selectedDay = (int)Math.floor(Math.random()*(days.size()));
        int ite = selectedDay;
        while(!result){
            if(days.get(ite).addSubject(subject)) result=true;
            ite++;
            if(ite==days.size())break;
        }
        while(!result){
            if(days.get(selectedDay).addSubject(subject)) result=true;
            selectedDay--;
            if(selectedDay<0)break;
        }
        return result;
    }

    public Day getDay(int index){
        return days.get(index);
    }
    
    public ArrayList<Day> getDays(){
        return days;
    }
    
    @Override
    public String toString() {
        String outString="Time Table";
        for(Day day:days){
            outString+=day;
        }
        outString+=">>>>>>>>>>>>>>>>END<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";
        return outString;
    }
}
