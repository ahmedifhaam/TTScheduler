/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models.Constraints;

import com.ttsh.ifhaam.models.ClassRoom;
import com.ttsh.ifhaam.models.Exam;
import com.ttsh.ifhaam.models.Position;
import com.ttsh.ifhaam.models.Subject;
import com.ttsh.ifhaam.models.TimeSlot;
import com.ttsh.ifhaam.models.TimeTable;

/**
 *this Constraint should make sure that a subject appears before another in the time table 
 * @author Ahmed
 */
public class SubjectBeforeSubjectConstraint extends SoftConstraint{
    Subject subjectBefore;
    Subject subjectAfter;

    private SubjectBeforeSubjectConstraint(int fitness) {
        super(fitness);
    }
    
    public SubjectBeforeSubjectConstraint(int fitness,Subject subjectAfter,Subject subjectBefore){
        super(fitness);
        this.subjectBefore = subjectBefore  ;
        this.subjectAfter = subjectAfter;
    }

    @Override
    public int calculateFitness(Position pos, TimeTable timeTable) {
       
        int totaldays = timeTable.countDays();
        if((totaldays-pos.getDay())>pos.getDay()){
            //days before are less than days after
            for(int i=0;i<pos.getDay();i++){
                for(TimeSlot timeslot : timeTable.getDays().get(i).getTimeSlots()){
                    
                    for(ClassRoom room :timeslot.getClassRooms()){
                        if(room.getAssignedExam()!=null)
                            for(Subject subject:room.getAssignedExam().getSubjects()){
                                if(subject.equals(subjectBefore)) return getFitness();
                            }
                    }
                }
            }
            
            return 0;//return (-1)*getFitness();
        }else{
            //days after are less than days before
             for(int i=pos.getDay();i<totaldays;i++){
                for(TimeSlot timeslot : timeTable.getDays().get(i).getTimeSlots()){
                    for(ClassRoom room :timeslot.getClassRooms()){
                        if(room.getAssignedExam()!=null)
                            for(Subject subject:room.getAssignedExam().getSubjects()){
                                if(subject.equals(subjectBefore)) return 0;//return (-1)*getFitness();
                            }
                    }
                }
            }
            
            return getFitness();
        }
        
        
       
    }

    @Override
    public boolean isApplicableTo(Object subject) {
        //if the selected subjet passed to this method it will return true
        
        return (((Subject)subject).equals(subjectAfter));
    }
    
}
