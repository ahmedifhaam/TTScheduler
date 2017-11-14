/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models;

import com.ttsh.ifhaam.controller.Algorithm;
import com.ttsh.ifhaam.controller.DataBaseController;
import com.ttsh.ifhaam.controller.TimeTableManager;
import com.ttsh.ifhaam.models.Constraints.Constraint;
import com.ttsh.ifhaam.models.Constraints.SameStudentConstraint;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Ahmed
 */
public class Population {
    private TimeTable[] timeTables;
    TimeTableManager ttMan;
    public ArrayList<Exam> exams;
    
    private static final int POPULATION_SIZE = 10;//not used now 
    
    /*
    @Override
    public Population clone(){
        Population popnew = new Population(this.size(),false);
        popnew.timeTables = timeTables.clone();
        return popnew;
    }*/
    
    /**
    public Population(boolean intialize){
        //ttMan = new TimeTableManager();
        ttMan = TimeTableManager.getInstance();
        exams =ttMan.getExams();
        //System.out.println(timeSlots.length+" time slot size");
        //ttMan.setTimeSlotHeadings(timeSlots);
        //ttMan.setClassRooms(classrooms);
        //ttMan.setDates(startingDate,daysCount);
        System.out.println("Size of exam inside "+exams.size());
        //initExams();
        timeTables = new TimeTable[POPULATION_SIZE];//population size
        for(int j=0;j<timeTables.length;j++){
            TimeTable tt = ttMan.getTimeTable();
            if(intialize){
                for(int i=0;i<exams.size();i++){
                    //System.out.println("Size of exams here "+exams.size());
                    if(!tt.addExam(exams.get(i))) {
                        System.out.println("Failed : Exam - "+exams.get(i));
                    }
                }
            }
            timeTables[j] = tt;
        }
    }
    
    public Population(){
        ttMan = TimeTableManager.getInstance();
        boolean intialize = false;
         timeTables = new TimeTable[POPULATION_SIZE];//population size
        for(int j=0;j<timeTables.length;j++){
            TimeTable tt = ttMan.getTimeTable();
            if(intialize){
                for(int i=0;i<exams.size();i++){
                    //System.out.println("Size of exams here "+exams.size());
                   if(!tt.addExam(exams.get(i))) System.out.println("Failed : Exam - "+exams.get(i));
                }
            }
            timeTables[j] = tt;
        }
    }
    
    **/
    
    
    
    public Population(int size,boolean initialize) throws NoBFSException{
        ttMan = TimeTableManager.getInstance();
        //ttMan = new TimeTableManager();
                
        //initSubjects();
        //initExams();
        
        exams = ttMan.getExams();
        timeTables = new TimeTable[size];
        TimeTable initialTT = ttMan.getTimeTable();
        boolean initialized = false;
        System.out.println(" --------------------------------------------+");
        for(int j=0;j<timeTables.length;j++){
            TimeTable tt = ttMan.getTimeTable();
            //System.out.println(" --------------------------------------------!");
            //System.out.println(" Stage 1 ="+tt.getFitness());
            if(initialize){
                if(!initialized){
                    init(initialTT);
                    //System.out.println("initial tt "+initialTT.hashCode());
                    initialized = true;
                    //System.out.println(" Stage 2 ="+tt.getFitness());
                }
                tt = initialTT.clone();
                System.out.println("cloned tt ("+j+") "+tt.hashCode());
                //System.out.println(" Stage 3 ="+tt.getFitness());
                randomize(tt);
                /*using constructing BSF
                  for(int i =0;i<exams.size();i++){
                    if(!tt.addExam(exams.get(i)))System.out.println("failed : Exam-"+exams.get(i));
                }*/
            }
            timeTables[j] = tt;
            //System.out.println("TimeTable : "+j+" "+tt.getFitness());
            //System.out.println("Constraint size : "+tt.getConstraints().size());
            //System.out.println(" --------------------------------------------#");
            
        }
    }
    
    public void init(TimeTable tt) throws NoBFSException{
        System.out.println("Here");
        ArrayList<Exam> clashed = new ArrayList<>();
       
        ArrayList<Position> positions = new ArrayList<>();
        Position p = new Position(tt);
        
        ArrayList<Exam> clashedMorethanOnce = new ArrayList<>();
        
        /*
        for(Exam exam :exams){
            
            boolean added = false;
            for(int numOfTries=0;numOfTries<15 && !added;numOfTries++){
                p.randomPosition();
                if(tt.setExam(p, exam)){
                    int fit = Algorithm.calculateFitness(tt);
                    if(fit==0){
                        clashed.add(exam);
                        positions.add(p);
                        tt.AssignExam(p, null);
                
                    }else{
                        added=true;
                    }
                }
            }
        }*/
        
        //worked fine but same timetable 
        for(Exam exam : exams){
            tt.AssignExam(p, exam);
            double fit = Algorithm.calculateFitness(tt);
            if(fit==0){
                clashed.add(exam);
                positions.add(p);
                tt.AssignExam(p, null);
            }else{
                p.next();
                if(p.isEnd()) break;
            }
                 
        }
        
        
        
        while(clashed.size()>0){
             p = new Position(tt);
             boolean added = false;
             while((!p.isEnd())&&(!added)){
                
                if(positions.get(0).getDay()!= p.getDay()){
                    if(tt.setExam(p, clashed.get(0))){
                        clashed.remove(0);
                        positions.remove(0);
                        added = true;
                    }

                }else{
                    if(positions.get(0).getTimeSlot()!=p.getTimeSlot()){
                        if(tt.setExam(p, clashed.get(0))){
                            clashed.remove(0);
                            positions.remove(0);
                            added = true;
                        }
                    }
                }
                int fit = Algorithm.calculateFitness(tt);
                if(fit==0){
                    clashedMorethanOnce.add(tt.getExam(p));
                    tt.AssignExam(p, null);
                    //positionsofMoreThanOnce.add(p);
                }
                p.next();
             }
             
        }
        
        while(clashedMorethanOnce.size()>0){
            Exam ex = clashedMorethanOnce.get(0);{
            p = new Position(tt);
            
            boolean added = false;
            while(!(p.isEnd() || added)){
                
                    if(tt.getExam(p)==null){
                        tt.AssignExam(p, ex);
                        int fit = Algorithm.calculateFitness(tt);
                        if(fit==0){
                            tt.AssignExam(p, null);
                        }else{
                            added=true;
                            clashedMorethanOnce.remove(0);
                            //positionsofMoreThanOnce.remove(0);
                        }
                    }
                    
                p.next();
            }
            if(!added){
                
                System.out.println("NO BFS");
                throw new NoBFSException();
            }
            
        }
        }
        /*
        for(int i =0;i<50;i++){
            Position p2 = new Position(tt);
            for(int j=0;j<3;j++){
                p.randomPosition();
                p2.randomPosition();
                Exam ex1 = tt.getExam(p);
                Exam ex2 = tt.getExam(p2);

                tt.AssignExam(p2, ex1);
                tt.AssignExam(p,ex2);
                int fit=Algorithm.calculateFitness(tt);
                if(fit==0){
                    tt.AssignExam(p2, ex2);
                    tt.AssignExam(p, ex1);
                }else{
                    j=3;
                }
            }
        }*/
        
        if(clashed.size()>0)System.out.println("Failed initial Construction"+clashed.size());
        //if(clashedMorethanOnce.size()>0)System.out.println("exams left initial Construction"+clashedMorethanOnce.size());
        
        System.out.println(Algorithm.getRankofWorst(tt));
        
    }
    
    public void randomize(TimeTable tt){
        Position p = new Position(tt);
        
        //System.out.println(" Stage 1 ="+tt.getFitness());
        for(int i =0;i<50;i++){
            //System.out.println(" Stage 2 ="+tt.getFitness());
            Position p2 = new Position(tt);
            for(int j=0;j<3;j++){
                //System.out.println(" Stage 3 ="+tt.getFitness());
                p.randomPosition();
                p2.randomPosition();
                Exam ex1 = tt.getExam(p);
                Exam ex2 = tt.getExam(p2);

                tt.AssignExam(p2, ex1);
                tt.AssignExam(p,ex2);
                
                int fit=Algorithm.calculateFitness(tt);
               
                if(fit==0){
                    
                    tt.AssignExam(p2, ex2);
                    tt.AssignExam(p, ex1);
                    //System.out.println(" Stage 5 ="+tt.getFitness());
                }else{
                    j=3;
                }
            }
        }
    }
    
    /*
    public Population(int size,boolean initialize){
        ttMan = new TimeTableManager();
        ttMan.setTimeSlotHeadings(new String[]{"Morning","Evening"});
        ttMan.loadClasses(3);
        ttMan.setDates(Calendar.getInstance().getTime(), 4);
        
        initSubjects();
        timeTables = new TimeTable[size];
        for(int j=0;j<timeTables.length;j++){
            TimeTable tt = ttMan.getTimeTable();
            //System.out.println("Time Table one complete");
            if(initialize){
                for(int i =0;i<subjects.size();i++){
                    if(!tt.addSubject(subjects.get(i)))System.out.println("failed "+subjects.get(i));
                }
            }
            //System.out.println(tt);
            timeTables[j]=tt;
        }
        //System.out.println(timeTables[0]);
                   
    }*/
    
    /*
    private void initSubjects(){
        subjects = new ArrayList<>();
        String sbjectCode = "abcdefghijklmnopqrstuvwx";
        for(int i=0;i<24;i++){
           subjects.add(new Subject(sbjectCode.charAt(i)+""));
        }
    }*/
    
    //chaging this method to get data from timetable Manager
    /*
    private void initExams(){
        exams = new ArrayList<>();
        DataBaseController dbc = DataBaseController.newInstance();
        ArrayList<Subject> subjects = dbc.getSubjects();
        for(Subject sbj :subjects ){
            Exam e = new Exam(sbj);
            exams.add(e);
        }
        //System.out.println("Size of exams "+exams.size());
    }*/
    
    
    
    public int size(){
        return timeTables.length;
    }
    
    public void saveTimeTable(int index,TimeTable tt){
        timeTables[index] = tt;
    }
    
    public TimeTable getTimeTable(int index){
        return timeTables[index];
    }
            
    public TimeTable getFittest(){
        TimeTable tt = timeTables[0];
        if(tt==null)System.out.println("its null here");
        //System.out.println("calulating :"+tt.countDays());
        double curFitness=Algorithm.calculateFitness(tt);
        for(int i=1;i<size();i++){
            double newFitness = Algorithm.calculateFitness(timeTables[i]);
            if(curFitness<newFitness){
                tt = timeTables[i];
                curFitness=newFitness;
            }
        }
        /*
        int curRank = 0 ;
        if(curFitness==0){
            tt= timeTables[timeTables.length-1];
            curRank = Algorithm.getRankofWorst(tt);
            for(int i =timeTables.length-2;i>=0;i--){
                int newRank = Algorithm.getRankofWorst(timeTables[i]);
                if(curRank>newRank){//lesser the rank better the product
                    tt = timeTables[i];
                    curRank = newRank;
                }
            }
        }
        System.out.println("Rank of TT "+tt.hashCode()+" :"+curRank);*/
        System.out.println("fit of TT "+tt.hashCode()+" :"+curFitness);
        return tt;
    }
    
    
}
