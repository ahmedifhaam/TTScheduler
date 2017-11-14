/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models.Constraints;

import com.ttsh.ifhaam.models.ClassRoom;
import com.ttsh.ifhaam.models.Position;
import com.ttsh.ifhaam.models.Subject;
import com.ttsh.ifhaam.models.TimeSlot;
import com.ttsh.ifhaam.models.TimeTable;
import java.util.ArrayList;

/**
 *
 * @author Ahmed
 */
public class CreditSubjectGap extends SoftConstraint{
    int credit;
    int day_gap;
    
    public CreditSubjectGap(int fitness,int Credit,int Gap) {
        super(fitness);
    }

    @Override
    public int calculateFitness(Position pos, TimeTable timeTable) {
        for(int i=pos.getDay();i<=(pos.getDay()+day_gap);i++){
            ArrayList<TimeSlot> timeSlots = timeTable.getDay(i).getTimeSlots();
            for(TimeSlot timeSlot :timeSlots){
                for(ClassRoom classRoom:timeSlot.getClassRooms()){
                    for(Subject subject:classRoom.getAssignedExam().getSubjects()){
                        if(subject.getCredit()==credit) return 0;///return (-1)*getFitness();
                    }
                }
            }
        }
        for(int i=pos.getDay()-1;i>=pos.getDay()-day_gap;i++){
            ArrayList<TimeSlot> timeSlots = timeTable.getDay(i).getTimeSlots();
            for(TimeSlot timeSlot :timeSlots){
                for(ClassRoom classRoom:timeSlot.getClassRooms()){
                    for(Subject subject:classRoom.getAssignedExam().getSubjects()){
                        if(subject.getCredit()==credit) return 0;//return (-1)*getFitness();
                    }
                }
            }
        }
        return getFitness();
    }

    @Override
    public boolean isApplicableTo(Object object) {
        Subject subject = (Subject)object;
        return subject.getCredit() == credit;
    }
    
}
