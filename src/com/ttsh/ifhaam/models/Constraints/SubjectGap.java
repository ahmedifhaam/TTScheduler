/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models.Constraints;

import com.ttsh.ifhaam.models.ClassRoom;
import com.ttsh.ifhaam.models.Day;
import com.ttsh.ifhaam.models.Position;
import com.ttsh.ifhaam.models.Subject;
import com.ttsh.ifhaam.models.TimeSlot;
import com.ttsh.ifhaam.models.TimeTable;
import java.util.ArrayList;

/**
 *
 * @author Ahmed
 */
public class SubjectGap extends SoftConstraint{
    public static final int TIMESLOT = 0;
    public static final int DAY = 1;
    Subject subjectOne;
    Subject subjectTwo;
    int gap;

    public SubjectGap(int fitness,Subject one,Subject two,int gap){//,int gapType
        super(fitness);
        this.subjectOne = one;
        this.subjectTwo = two;
        this.gap = gap;
    }
    
    @Override
    public int calculateFitness(Position pos,TimeTable tt){
        int gapStart = pos.getDay()-gap;
        gapStart = gapStart<0?0:gapStart;
        
        int gapEnd = pos.getDay()+gap;
        gapEnd = gapEnd>=tt.countDays()?tt.countDays()-1:gapEnd;
        System.out.println("Starting date"+tt.getDay(gapStart).getDateString());
        System.out.println("ending date"+tt.getDay(gapEnd).getDateString());
        for(int i=gapStart;i<gapEnd;i++){
            Day day = tt.getDay(i);
            for(TimeSlot timeSlot : day.getTimeSlots()){
                for(ClassRoom room:timeSlot.getClassRooms()){
                    if(room.contains(subjectTwo)){
                        return 0;
                    }
                }
            }
        }
        System.out.println("Subject one "+subjectOne);
        System.out.println("Subject one "+subjectTwo);
        return getFitness();
    }

    /*
    @Override
    public int calculateFitness(Position pos, TimeTable timeTable) {
        //for now only implementing gap with day
        //if(timeTable.getExam(pos).getSubjects().contains(subjectOne) ){
            //go gap days behind and go gap days forward and find the subject two
            for(int i=pos.getDay();i<pos.getDay()+gap&& i<timeTable.countDays();i++){//have to check the other time slots in the same day too
                for(TimeSlot timeSlot:timeTable.getDay(i).getTimeSlots()){
                    for(ClassRoom classRoom:timeSlot.getClassRooms()){
                        if(classRoom.contains(subjectTwo)){
                            return 0;//return (-1)*getFitness();
                        }
                    }
                }
            }
            for(int i=pos.getDay()-1;i<pos.getDay()-gap&& i>=0;i--){//dont have to check the other time slots in the same day too
                for(TimeSlot timeSlot:timeTable.getDay(i).getTimeSlots()){
                    for(ClassRoom classRoom:timeSlot.getClassRooms()){
                        if(classRoom.contains(subjectTwo)){
                            return 0;//return (-1)*getFitness();
                        }
                    }
                }
            }
            return getFitness();
        /*}else{
            //go gap days behind and go gap days forward and find the subject two
            for(int i=pos.getDay();i<pos.getDay()+gap && i<timeTable.countDays();i++){//have to check the other time slots in the same day too
                for(TimeSlot timeSlot:timeTable.getDay(i).getTimeSlots()){
                    for(ClassRoom classRoom:timeSlot.getClassRooms()){
                        if(classRoom.contains(subjectOne)){
                            return 0;//return (-1)*getFitness();
                        }
                    }
                }
            }
            for(int i=pos.getDay()-1;i<pos.getDay()-gap&& i>=0;i--){
                for(TimeSlot timeSlot:timeTable.getDay(i).getTimeSlots()){
                    for(ClassRoom classRoom:timeSlot.getClassRooms()){
                        if(classRoom.contains(subjectOne)){
                            return 0;//return (-1)*getFitness();
                        }
                    }
                }
            }
            return getFitness();
        }
    }*/

    @Override
    public boolean isApplicableTo(Object object) {
        if(((Subject)object).equals(this.subjectOne)){ //||((Subject)object).equals(subjectTwo)){
            return true;
        }
        return false;
    }
    
}
