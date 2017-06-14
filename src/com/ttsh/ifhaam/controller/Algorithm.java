/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.controller;

import java.util.ArrayList;
import java.util.Calendar;
import com.ttsh.ifhaam.models.ClassRoom;
import com.ttsh.ifhaam.models.Constraints.Constraint;
import com.ttsh.ifhaam.models.Day;
import com.ttsh.ifhaam.models.Exam;
import com.ttsh.ifhaam.models.Population;
import com.ttsh.ifhaam.models.Position;
import com.ttsh.ifhaam.models.Subject;
import com.ttsh.ifhaam.models.TimeSlot;
import com.ttsh.ifhaam.models.TimeTable;

/**
 *
 * @author Ahmed
 */
public class Algorithm {
    private static double uniformRate = 0.5;
    private static double mutationRate = 0.0015;//THIS AMOUNT WORKED BETTER WITH TRIAL 0.0015(
    //aROUND ten times better
    private static final int tournamentsize = 5;
    private static TimeTable target;
    public static boolean elitism = true;
    
    private ArrayList<Constraint> Constraints;
    
    public static Population evolvePop(Population pop){
        Population newPop = new Population();
        int elitismOffset =0;
        if(elitism){
            newPop.saveTimeTable(0, pop.getFittest());
            elitismOffset = 1;
        }
        
        for(int i = elitismOffset;i<newPop.size();i++){
            TimeTable parent1 = tournamentSelection(pop);
            TimeTable parent2 = tournamentSelection(pop);
            
            if(parent1==null)System.out.println("parent 1 null");
            if(parent2==null)System.out.println("parent 2 null");
            //crossover parents 
            TimeTable child = null;
            try{
                child = crossOver(parent1,parent2);
                if(child ==null)System.out.println("child null");
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
            
            //addChild to the population
            newPop.saveTimeTable(i, child);
        }
        
        //mutate the new population a bit to add some new genetic material
        for(int i=0;i<newPop.size();i++){
            //System.out.println(newPop.getTimeTable(i));
            newPop.saveTimeTable(i, mutate(newPop.getTimeTable(i)));
            //System.out.println(newPop.getTimeTable(i));
        }
        //pop = newPop;
        return newPop;
    }
    
    //crossover for exam objects
    public static TimeTable crossOver(TimeTable tt1,TimeTable tt2){
        
        TimeTableManager ttMan = TimeTableManager.getInstance();
        //because of singleton pattern these line become useless
        //TimeTableManager ttMan = new TimeTableManager();
        //ttMan.setTimeSlotHeadings(new String[]{"Morning","Evening"});
        //ttMan.loadClasses(3);//loads the class rooms 
        //ttMan.setDates(Calendar.getInstance().getTime(),4);
        TimeTable newtt= ttMan.getTimeTable();
        
        //crossover
        //starting position;
        int dayst = (int)(Math.random()*newtt.countDays());
        int timeSlotstr = (int)(Math.random()*newtt.getDay(dayst).getTimeSlots().size());
        int classRoomstr = (int)(Math.random()*newtt.getDay(dayst).getTimeSlots().get(timeSlotstr).getClassRooms().size());
        
        //ending position
        int dayend = (int)(Math.random()*newtt.countDays());
        int timeSlotend = (int)(Math.random()*newtt.getDay(dayend).getTimeSlots().size());
        int classRoomend = (int)(Math.random()*newtt.getDay(dayend).getTimeSlots().get(timeSlotend).getClassRooms().size());
        
        
        //make sure starting is before or equal to end 
        //all three timining componenets day,timeslot and class room
        
        if(dayst>dayend){
            int temp = dayend;
            dayend = dayst;
            dayst = temp;
            
        }else if(dayst==dayend){
            if(timeSlotstr>timeSlotend){
                int temp = timeSlotend;
                timeSlotend = timeSlotstr;
                timeSlotstr = temp;
            }else if(timeSlotstr==timeSlotend){
                if(classRoomstr>classRoomend){
                    int temp = classRoomend;
                    classRoomend = classRoomstr;
                    classRoomstr = temp;
                }
            }
        }
        
        
        for(int i = 0 ; i<newtt.countDays();i++){
            for(int j=0;j<newtt.getDay(i).getTimeSlots().size();j++){
                for(int k=0;k<newtt.getDay(i).getTimeSlots().get(j).getClassRooms().size();k++ ){
                    if(dayst ==dayend && dayst ==i){
                        if(timeSlotstr==timeSlotend && timeSlotstr==j){
                            if(classRoomstr<=k && k<=classRoomend){
                                //swap subjects
                                newtt.setExam(i, j, k, tt1.getExam(i, j, k));
                                continue;
                            }
                        }else if(timeSlotstr==j){
                            if(classRoomstr<=k){
                                //swap subjects
                                newtt.setExam(i, j, k, tt1.getExam(i, j, k));
                                continue;
                            }
                        }else if(timeSlotend==j){
                            if(classRoomstr>=k){
                                //swap subjects
                                newtt.setExam(i, j, k, tt1.getExam(i,j,k));
                                continue;
                            }
                        }else if(timeSlotstr<=j && timeSlotend>=j){
                            newtt.setExam(i, j, k, tt1.getExam(i, j, k));
                            continue;
                        }
                    }else if(dayst==i){
                        if(timeSlotstr==j){
                            if(classRoomstr<=k){
                                newtt.setExam(i, j, k, tt1.getExam(i, j, k));
                                continue;
                            }
                        }else if(timeSlotstr<j){
                            newtt.setExam(i, j, k, tt1.getExam(i, j, k));
                        }
                    }else if(dayend ==i){
                        if(timeSlotend==j){
                            if(classRoomend>=k){
                                newtt.setExam(i, j, k, tt1.getExam(i, j, k));
                            }
                        }else if(timeSlotend>j){
                            newtt.setExam(i, j, k, tt1.getExam(i, j, k));
                        }
                    }else if(dayst<i && i<dayend){
                        newtt.setExam(i, j, k, tt1.getExam(i, j, k));
                    }
                }
            }
        }
        
        //fill the empty slots with the subjects that are not already in newtt
        for(int i=0;i<tt2.countDays();i++){
            for(int j=0;j<tt2.getDay(i).getTimeSlots().size();j++){
                for(int k=0;k<tt2.getDay(i).getTimeSlots().get(j).getClassRooms().size();k++){
                    //System.out.println("i "+i+" j "+j+" k "+k);
                    Exam exm = tt2.getExam(i, j, k);
                    if(!newtt.contains(exm)){
                            boolean check = true;
                        for(int x=0;x<tt2.countDays() && check;x++){
                            for(int y=0;y<tt2.getDay(x).getTimeSlots().size() && check;y++){
                                for(int z=0;z<tt2.getDay(x).getTimeSlots().get(y).getClassRooms().size() && check;z++){
                                    if(newtt.getExam(x, y, z) == null){
                                        newtt.setExam(x, y, z, tt2.getExam(i, j, k));
                                        check = false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if(newtt==null)System.out.println("new tt is null");
        return newtt;
    }
    
    
    /*
    public static TimeTable crossOver(TimeTable tt1,TimeTable tt2){
        TimeTableManager ttMan = new TimeTableManager();
        ttMan.setTimeSlotHeadings(new String[]{"Morning","Evening"});
        ttMan.loadClasses(3);
        ttMan.setDates(Calendar.getInstance().getTime(), 4);
        TimeTable newtt = ttMan.getTimeTable();
        
        //crossover
        //starting position
        int dayst = (int)(Math.random()*newtt.countDays());
        int timeSlotstr = (int)(Math.random()*newtt.getDay(dayst).getTimeSlots().size());
        int classRoomStr = (int)(Math.random()*newtt.getDay(dayst).getTimeSlots().get(timeSlotstr).getClassRooms().size());
        
        //ending position
        int dayend = (int)(Math.random()*newtt.countDays());
        int timeSlotend = (int)(Math.random()*newtt.getDay(dayend).getTimeSlots().size());
        int classRoomend = (int)(Math.random()*newtt.getDay(dayend).getTimeSlots().get(timeSlotend).getClassRooms().size());
        
        
        
        if(dayst>dayend){
            int temp  = dayend;
            dayend = dayst;
            dayst = temp;
        }else if(dayst==dayend){
            if(timeSlotstr >timeSlotend){
                int temp = timeSlotend;
                timeSlotend = timeSlotstr;
                timeSlotstr= temp;
            }else if(timeSlotstr == timeSlotend){
                if(classRoomStr>classRoomend){
                    int temp = classRoomend;
                    classRoomend = classRoomStr;
                    classRoomStr = classRoomend;
                }
            }
        }
     
        for(int i=0;i<newtt.countDays();i++){
            for(int j=0;j<newtt.getDay(i).getTimeSlots().size();j++){
                for(int k=0;k<newtt.getDay(i).getTimeSlots().get(j).getClassRooms().size();k++){
                    //System.out.println("<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>");
                   //System.out.print(dayst+"<");System.out.print(i);System.out.println("<"+dayend);
                    //System.out.print(timeSlotstr+"<");System.out.print(j);System.out.println("<"+timeSlotend);
                   // System.out.print(classRoomStr+"<");System.out.print(k);System.out.println("<"+classRoomend);
                    //System.out.println("<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>");
                    //case one 
                    if(dayst==dayend && dayend==i){
                        if(timeSlotstr==timeSlotend && timeSlotend==j){
                            if(classRoomStr<=k && k<=classRoomend){
                                //swap subjects
                                newtt.setSubject(i, j, k, tt1.getSubject(i, j, k));
                                continue;
                            }
                        }else if(timeSlotstr==j){
                            if(classRoomStr<=k){
                                //swap subjects
                                newtt.setSubject(i, j, k, tt1.getSubject(i, j, k));
                                continue;
                            }
                        }else if(timeSlotend==j){
                            if(classRoomend>=k){
                                //swapt subjects
                                newtt.setSubject(i, j, k, tt1.getSubject(i, j, k));
                                continue;
                            }
                        }else if(timeSlotstr<j && timeSlotend>j){
                                //swapt subjects
                                newtt.setSubject(i, j, k, tt1.getSubject(i, j, k));
                                if(newtt.contains(tt1.getSubject(i, j, k)))System.out.println("Success");
                                else System.out.println("failed");
                                continue;
                            
                        }
                    }else if(dayst==i){
                        if(timeSlotstr==j){
                            if(classRoomStr<=k){
                                //swap subjects 
                                newtt.setSubject(i, j, k, tt1.getSubject(i, j, k));
                                continue;
                            }
                        }else if(timeSlotstr<j){
                            //swap subjects
                            newtt.setSubject(i, j, k, tt1.getSubject(i, j, k));
                            continue;
                        }
                    }else if(dayend==i){
                        if(timeSlotend==j){
                            if(classRoomend>=k){
                                //swapt subjects
                                newtt.setSubject(i, j, k, tt1.getSubject(i, j, k));
                                continue;
                            }
                        }else if(timeSlotend>j){
                            newtt.setSubject(i, j, k, tt1.getSubject(i, j, k));
                            continue;
                        }
                    }else if(dayst<i && dayend>i){
                        //swapt subjects
                        newtt.setSubject(i, j, k, tt1.getSubject(i, j, k));
                        continue;
                            
                    }
                }
            }
        }
        //System.out.println(newtt);
        for(int i=0;i<tt2.countDays();i++){
            for(int j=0;j<tt2.getDay(i).getTimeSlots().size();j++){
                for(int k=0;k<tt2.getDay(i).getTimeSlots().get(j).getClassRooms().size();k++){
                    Subject sbj = tt2.getSubject(i, j, k);
                    if(!newtt.contains(sbj)){
                            boolean check = true;
                        for(int x=0;x<tt2.countDays() && check;x++){
                            for(int y=0;y<tt2.getDay(x).getTimeSlots().size() && check;y++){
                                for(int z=0;z<tt2.getDay(x).getTimeSlots().get(y).getClassRooms().size() && check;z++){
                                    if(newtt.getSubject(x, y, z) == null){
                                        newtt.setSubject(x, y, z, tt2.getSubject(i, j, k));
                                        check = false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        //add rest of the subjects;
        
        //System.out.println("*************************************\n");
        //System.out.println(newtt);
        //System.out.println("*************************************\n");
        return newtt;
    }*/
    
    
    public static TimeTable mutate(TimeTable tt){
        //loop through genes and swap
        ArrayList<Day> days = tt.getDays();
        for(int i=0;i<days.size();i++){
            ArrayList<TimeSlot> ts = days.get(i).getTimeSlots();
            for(int j=0;j<ts.size();j++){
                ArrayList<ClassRoom> classRooms = ts.get(j).getClassRooms();
                for(int k=0;k<classRooms.size();k++){
                    if(Math.random()<=mutationRate){
                        //swap this with a random subject
                        int x = (int)(Math.random()*days.size());
                        int y = (int)(Math.random()*ts.size());
                        int z = (int)(Math.random()*classRooms.size());
                        
                        Exam exm = tt.getExam(i, j, k);
                        Exam exm2 = tt.getExam(x,y,z);
                        
                        if(!(tt.setExam(x, y, z, exm) && (tt.setExam(i, j, k, exm2)))){
                            tt.setExam(x,y,z, exm);
                            tt.setExam(i, j, k, exm2);
                        }
                    }
                }
            }
        }
        
        return tt;
    }
    
    /*
    public static TimeTable mutate(TimeTable tt){
        //loop through genes and swap
        ArrayList<Day> days = tt.getDays();
        for(int i =0;i<days.size();i++){
            ArrayList<TimeSlot> ts =  days.get(i).getTimeSlots();
            for(int j=0;j<ts.size();j++){
                ArrayList<ClassRoom> classRooms = ts.get(j).getClassRooms();
                for(int k=0;k<classRooms.size();k++){
                    if(Math.random()<=mutationRate){
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
        return tt;
    }*/
    
    
    
    public static TimeTable tournamentSelection(Population pop){
        //create a tournament population
        Population tournament  = new Population(tournamentsize,false);
        
        //for each place in the tournament get a random timetable (individual)aa
        for(int i =0;i<tournamentsize;i++){
            int randomID = (int)(Math.random()*pop.size());
            tournament.saveTimeTable(i, pop.getTimeTable(randomID));
        }
        
        return tournament.getFittest();
    }
    
    public static void setTarget(TimeTable tt){
        target = tt;//have to be removed used to test at the initial phase
    }
    
    
    //this method has to be improved
    public static int calculateFitness(TimeTable tt){
        if(tt==null)return 0;
        int fitness=0;
        for(int i=0;i<tt.countDays();i++ ){
            for(int k=0;k<tt.getDay(i).getTimeSlots().size();k++){
                for(int j=0;j<tt.getDay(i).getTimeSlots().get(k).getClassRooms().size();j++){
                    Exam exm = tt.getExam(i, k, j);
                    if(exm!=null){
                        ArrayList<Subject> subjects = exm.getSubjects();
                        for(Subject sbj:subjects){
                            for(Constraint con : tt.getConstraints()){
                                if(con.isApplicableTo(sbj)){
                                    int retVal = con.calculateFitness(new Position(i,k,j), tt);
                                    //System.out.println("Fitness :"+fitness+"Change : "+retVal);
                                    //System.out.println(sbj);
                                    fitness+= retVal;
                                    
                                }
                            }

                        }
                    }
                }
            }
        }
        //System.out.println("Fitness :"+fitness);
         return fitness;
        
        /*old test alphabet order one
        for(int i=0;i<target.countDays();i++){
            for(int j=0;j<target.getDay(i).getTimeSlots().size();j++){
                for(int k=0;k<target.getDay(i).getTimeSlots().get(j).getClassRooms().size();k++){
                    //if(tt.getSubject(i, j, k).equals(target.getSubject(i, j, k)))fitness++;
                    
                    //fitness++;
                   
                }
            }
        }
        return fitness;*/
    }
}
