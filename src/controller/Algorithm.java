/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Calendar;
import timetable.scheduler.pkgfor.uokscience.models.ClassRoom;
import timetable.scheduler.pkgfor.uokscience.models.Day;
import timetable.scheduler.pkgfor.uokscience.models.Population;
import timetable.scheduler.pkgfor.uokscience.models.Subject;
import timetable.scheduler.pkgfor.uokscience.models.TimeSlot;
import timetable.scheduler.pkgfor.uokscience.models.TimeTable;

/**
 *
 * @author Ahmed
 */
public class Algorithm {
    private static double uniformRate = 0.5;
    private static double mutationRate = 0.015;
    private static int tournamentsize = 5;
    public static Population evolvePop(Population pop){
        Population population = new Population(pop.size(),false);
        
        return population;
    }
    
    public static TimeTable crossOver(TimeTable tt1,TimeTable tt2){
        TimeTableManager ttMan = new TimeTableManager();
        ttMan.setTimeSlotHeadings(new String[]{"Morning","Evening"});
        ttMan.loadClasses(3);
        ttMan.setDates(Calendar.getInstance().getTime(), 4);
        TimeTable newtt = ttMan.getTimeTable();
        
        //crossover
        
        
        
        return null;
    }
    
    public static TimeTable mutation(TimeTable tt){
        //loop through genes and swap
        ArrayList<Day> days = tt.getDays();
        for(int i =0;i<days.size();i++){
            ArrayList<TimeSlot> ts =  days.get(i).getTimeSlots();
            for(int j=0;j<ts.size();j++){
                ArrayList<ClassRoom> classRooms = ts.get(j).getClassRooms();
                for(int k=0;k<classRooms.size();k++){
                    if(Math.random()>mutationRate){
                        //swap this with a random subject
                        int x = (int)(Math.random()*days.size());
                        int y = (int)(Math.random()*ts.size());
                        int z = (int)(Math.random()*classRooms.size());

                        Subject subject1 = tt.getSubject(i, j, k);
                        Subject subject2 = tt.getSubject(x, y, z);
                        if(!(tt.setSubject(x, y, z, subject1) && tt.setSubject(i, j, k, subject2))){//if adding one subject failed restore it as before to make sure 
                            //we dont remove one in the process
                            tt.setSubject(x, y, z, subject2);
                            tt.setSubject(i, j, k, subject1);
                        }
                    }
                }
            }
        }
        return null;
    }
    
    public static TimeTable tournamentSelection(Population pop){
        //create a tournament population
        Population tournament  = new Population(pop.size(),false);
        
        //for each place in the tournament get a random timetable (individual)aa
        for(int i =0;i<tournamentsize;i++){
            int randomID = (int)(Math.random()*pop.size());
            tournament.saveTimeTable(i, pop.getTimeTable(randomID));
        }
        int randomID = (int)(Math.random()*tournament.size());
        return tournament.getTimeTable(randomID);
    }
    
    
}
