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
import com.ttsh.ifhaam.models.Constraints.Constraint.TYPE;
import com.ttsh.ifhaam.models.Constraints.HardConstraint;
import com.ttsh.ifhaam.models.Day;
import com.ttsh.ifhaam.models.Exam;
import com.ttsh.ifhaam.models.Population;
import com.ttsh.ifhaam.models.Position;
import com.ttsh.ifhaam.models.Subject;
import com.ttsh.ifhaam.models.TimeSlot;
import com.ttsh.ifhaam.models.TimeTable;
import com.ttsh.ifhaam.models.NoBFSException;

/**
 *
 * @author Ahmed
 */
public class Algorithm {
    public static final boolean DEBUG = false;
    
    
    private static double uniformRate = 0.5;
    private static double mutationRate = 0.0015;//THIS AMOUNT WORKED BETTER WITH TRIAL 0.0015(
    //aROUND ten times better
    private static final int tournamentsize = 5;
    private static TimeTable target;
    public static boolean elitism = true;
    public static final int POPULATION_SIZE = 30;
    
    private ArrayList<Constraint> Constraints;
    
    
    private static int generation= 0;
    //this method is to test different parameters and find a best one for the solution space
    /*public static Population testEvolutionParams(Population pop){
        // //change mutation rate
        System.out.println("Generation "+generation);
        Population best = pop;
        int curbestfit = 0;
        for(mutationRate=0.0015;mutationRate<0.3;mutationRate+=0.0015){
            System.out.println("Generation "+generation);
            System.out.println("Mutation Rate : "+mutationRate);
            Population p = evolvePop(best.clone());
            int temp  = getRankofWorst(p.getFittest());
            if(temp>curbestfit){
                best= p;
                curbestfit = temp;
            }
        }
        generation++;
        
        return best;
    }*/
    
    public static void intelSwap(TimeTable t1 ){
        Position p = new Position(t1);
        Position p2 = new Position(t1);
                
        while(!(p.isEnd()|| p2.isEnd())) {
            Exam exam1 = t1.getExam(p);
            Exam exam2 = t1.getExam(p2);
            t1.setExam(p, exam2);
            t1.setExam(p2, exam1);
            p.next();
            p2.next();
        }
    }
    
    public static Population evolvePop(Population pop){
        //Population newPop = new Population();
        Population newPop=null;
        try{
            newPop = new Population(POPULATION_SIZE,false);
        }catch(NoBFSException ex){
            
        }
        int elitismOffset =0;
        if(elitism){
            newPop.saveTimeTable(0, pop.getFittest());
            elitismOffset = 1;
        }
        
        for(int i = elitismOffset;i<newPop.size();i++){
            TimeTable parent1 = tournamentSelection(pop);
            TimeTable parent2 = tournamentSelection(pop);
            //parent2= pop.getFittest();
            //System.out.println("parent1 subjects "+parent1.getAssignedSubjectsCount());
            //System.out.println("parent2 subjects "+parent2.getAssignedSubjectsCount());
            
            if(parent1==null)System.out.println("parent 1 null");
            if(parent2==null)System.out.println("parent 2 null");
            //crossover parents 
            TimeTable child = null;
            try{
                child = crossOvert(parent1,parent2);
                //System.out.println("p 1"+parent1.hashCode() );
                //System.out.println("p 2"+parent2.hashCode() );
                
                //intelSwap(child);
                //child = crossOvert(parent1,parent2);
                if(child ==null)System.out.println("child null");
                //System.out.println("child subjects after crossover "+child.getAssignedSubjectsCount());
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
            
            //addChild to the population
            newPop.saveTimeTable(i, child);
        }
        
        //mutate the new population a bit to add some new genetic material
        for(int i=elitismOffset;i<newPop.size();i++){
            //System.out.println(newPop.getTimeTable(i));
            newPop.saveTimeTable(i, mutate(newPop.getTimeTable(i)));
            //System.out.println("child subjects after mutation"+(newPop.getTimeTable(i).getAssignedSubjectsCount()));
            //System.out.println(newPop.getTimeTable(i));
        }
        //pop = newPop;
        return newPop;
    }
    
    //this method worked perfect for the small dataset
    
    private static TimeTable crossOvert(TimeTable tt1,TimeTable tt2){
        int tt1Selection=0;
        int tt2Selection=0;
        TimeTableManager ttMan = TimeTableManager.getInstance();
        TimeTable newtt = ttMan.getTimeTable();
        Position pos = new Position(newtt);
        while(!pos.isEnd()){
            Exam e = tt1.getExam(pos);
            if(Math.random()<=uniformRate){
                e = tt2.getExam(pos);
                
            }
            if(!newtt.contains(e)) newtt.setExam(pos,e);
            pos.next();
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
    
    //crossover for exam objects
    private static TimeTable crossOver(TimeTable tt1,TimeTable tt2){
        
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
                        
                        tt.AssignExam(x, y, z, exm);
                        tt.AssignExam(i, j, k, exm2);
                        
                        /*
                        if(!(tt.setExam(x, y, z, exm) && (tt.setExam(i, j, k, exm2)))){
                            tt.setExam(x,y,z, exm);
                            tt.setExam(i, j, k, exm2);
                        }*/
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
        
        Population tournament  = null;//new Population(tournamentsize,false);
        try{
            tournament  = new Population(tournamentsize,false);
        }catch(NoBFSException ex){
            
        }
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
    
    
    //if all the time tables returned are zero fitness then 
    //we need to rank them in order to take the best to next level
    //lesser the returning value better the solution
    //returning the hard constraints broken
    public static int getRankofWorst(TimeTable tt){
        if(tt==null)return 0;
        int count =0;
        for(int i=0;i<tt.countDays();i++ ){
            for(int k=0;k<tt.getDay(i).getTimeSlots().size();k++){
                for(int j=0;j<tt.getDay(i).getTimeSlots().get(k).getClassRooms().size();j++){
                    //here implement class room constraints
                    Exam exm = tt.getExam(i, k, j);
                    if(exm!=null){
                        ArrayList<Subject> subjects = exm.getSubjects();
                        for(Subject sbj:subjects){
                            for(Constraint con : TimeTableManager.getInstance().getConstraints()){
                                if(con.getType()==TYPE.CONSTRAINT_FOR_ROOM){
                                    if(con.isApplicableTo(tt.getDay(i).getTimeSlots().get(k).getClassRooms().get(j))){
                                        double retVal = con.calculateFitness(new Position(i,k,j), tt);
                                        if(retVal==0 && (con instanceof HardConstraint))count++;
                                        
                                    }
                                }else{
                                    if(con.isApplicableTo(sbj)){
                                        double retVal = con.calculateFitness(new Position(i,k,j), tt);
                                        //System.out.println("Fitness :"+fitness+"Change : "+retVal);
                                        //System.out.println(sbj);
                                        if(retVal==0 && (con instanceof HardConstraint)) count++;
                                        

                                    }
                                }
                                
                                
                            }

                        }
                    }
                }
            }
        }
        return count;
    }
    
    //this method has to be improved
    public static int calculateFitness(TimeTable tt){
        //System.out.println("size of constraints "+ TimeTableManager.getInstance().getConstraints().size());
        //System.out.println("Calculating for "+tt.hashCode());
         //System.out.println("==============================*");
        if(tt.isUpdated()){
            if(tt==null)return 0;
            int fitness=0;
            for(int i=0;i<tt.countDays();i++ ){
                
                for(int k=0;k<tt.getDay(i).getTimeSlots().size();k++){
                    for(int j=0;j<tt.getDay(i).getTimeSlots().get(k).getClassRooms().size();j++){
                        //here implement class room constraints
                        Exam exm = tt.getExam(i, k, j);
                        if(exm!=null){
                            ArrayList<Subject> subjects = exm.getSubjects();
                            for(Subject sbj:subjects){
                                //System.out.println("Constraint sizes : "+TimeTableManager.getInstance().getConstraints().size());
                                for(Constraint con : TimeTableManager.getInstance().getConstraints()){
                                    if(con.getType()==TYPE.CONSTRAINT_FOR_ROOM){
                                        if(con.isApplicableTo(tt.getDay(i).getTimeSlots().get(k).getClassRooms().get(j))){
                                            int retVal = con.calculateFitness(new Position(i,k,j), tt);
                                            if(retVal==0 && (con instanceof HardConstraint)){
                                                
                                                tt.setFitness(0);
                                                tt.setUpdated(false);
                                                return 0;
                                            }else {
                                                fitness+=retVal;
                                            }
                                        }
                                    }else{
                                        if(con.isApplicableTo(sbj)){
                                            int retVal = con.calculateFitness(new Position(i,k,j), tt);
                                            //System.out.println("Fitness :"+fitness+"Change : "+retVal);
                                            //System.out.println(sbj);
                                            if(retVal==0 && (con instanceof HardConstraint)){
                                                 tt.setFitness(0);
                                                tt.setUpdated(false);
                                                return 0;
                                            }else{
                                                fitness+= retVal;
                                            }
                                            

                                        }
                                    }


                                }

                            }
                        }
                    }
                }
            }
             //System.out.println("==============================");
            tt.setFitness(fitness);
            tt.setUpdated(false);
            //System.out.println("Fitness :"+fitness);
             return fitness;
        }else{
            return tt.getFitness();
        }
        
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
