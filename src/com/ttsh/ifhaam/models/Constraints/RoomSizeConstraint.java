/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models.Constraints;

import com.ttsh.ifhaam.models.ClassRoom;
import com.ttsh.ifhaam.models.Position;
import com.ttsh.ifhaam.models.TimeTable;

/**
 *
 * @author Ahmed
 */
public class RoomSizeConstraint extends HardConstraint{
    public RoomSizeConstraint(){
        super(TYPE.CONSTRAINT_FOR_ROOM);
    }

    @Override
    public int calculateFitness(Position pos, TimeTable timeTable) {
        ClassRoom clasRoom = timeTable.getDay(pos.getDay()).getTimeSlots().get(pos.getTimeSlot()).getClassRooms().get(pos.getClassRoom());
        if( clasRoom.getSize() >= clasRoom.getAssignedExam().getStudentCount()){
            return getFitness();
        }else{
            //return (-10)*getFitness();
            return 0;
        }
        
    }

    @Override
    public boolean isApplicableTo(Object object) {
        return object instanceof ClassRoom;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj == null)return false;
        if(obj instanceof RoomSizeConstraint)return true;
        else return false;
    }
    
    @Override
    public String toString(){
        return "Student count of the exam should be less than or Equal Room Size";
    }
    
}
