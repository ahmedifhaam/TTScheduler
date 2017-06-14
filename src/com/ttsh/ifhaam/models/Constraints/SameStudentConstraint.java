/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models.Constraints;

import com.ttsh.ifhaam.models.Position;
import com.ttsh.ifhaam.models.Student;
import com.ttsh.ifhaam.models.Subject;
import com.ttsh.ifhaam.models.TimeTable;
import java.util.ArrayList;

/**
 *
 * @author Ahmed
 * this constraint should make sure that same student doesn't write two exams in the same day
 */
public class SameStudentConstraint extends HardConstraint{
    public SameStudentConstraint(){
        super();//Since this is a common hard constraints 
        //passing the empty constructor will assign the default value 
    }        

    @Override
    public int calculateFitness(Position pos, TimeTable timeTable) {
        //check whether the students who are sitting for the subject in the pos(Position)
        //not sitting for another 
        
        //since this contraint applies for all the subjects we dont check with the clssrooms before the pos
        //we check the classrooms after the pos to make the algorithm faster 
        ArrayList<Subject> subjectsOnPos = timeTable.getExam(pos).getSubjects();
        for(Subject sub :subjectsOnPos){
            for(Student std : sub.getStudents()){
                int classRoomsEnd = timeTable.getDay(pos.getDay()).getTimeSlots().get(pos.getTimeSlot()).getClassRooms().size();
                int classRoomStart = pos.getClassRoom()+1;
                if(classRoomStart>=classRoomsEnd){//if it is the last classRoom then we dont have to check
                    return getFitness();
                }
                for(int i=1;pos.getClassRoom()+i<classRoomsEnd;i++){
                    //System.out.println("Strt :"+classRoomStart+"   | classRoom End :"+classRoomsEnd);
                    if(timeTable.getDay(pos.getDay()).getTimeSlots().get(pos.getTimeSlot()).getClassRooms().get(pos.getClassRoom()+i).isSitting(std)){
                        //System.out.println("Hard Cosntraint SAMESTUDENT BROKEN pos :"+pos+" i="+i);
                        //System.out.println("Student :"+std);
                        //System.out.println(" Class Room "+timeTable.getDay(pos.getDay()).getTimeSlots().get(pos.getTimeSlot()).getClassRooms().get(pos.getClassRoom()+i));
                        
                        return getFitness()*(-1);
                    }
                }
            }
        }
        
        return getFitness();
    }

    @Override
    public boolean isApplicableTo(Subject subject) {
        //Since all the subjects have to succeed this test retturn true for all 
        return true;
    }

   
    
    
}
