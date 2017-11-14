/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models.Constraints;

import com.ttsh.ifhaam.models.ClassRoom;
import com.ttsh.ifhaam.models.Position;
import com.ttsh.ifhaam.models.Subject;
import com.ttsh.ifhaam.models.TimeTable;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Ahmed
 */
public class ClassRoomWhileClassRoomConstraint extends HardConstraint{
    public static String DESCRIPTION = "Only one of two classrooms selected here will be assigned subject";
    ClassRoom allowedRoom;
    ClassRoom notAllowedRoom;
    
    public ClassRoomWhileClassRoomConstraint(ClassRoom allowedClassRoom,ClassRoom notAllowedClassRoom){
        super(TYPE.CONSTRAINT_FOR_ROOM);
        this.allowedRoom = allowedClassRoom;
        this.notAllowedRoom = notAllowedClassRoom;
    }
    

    @Override
    public int calculateFitness(Position pos, TimeTable timeTable) {
        
        ArrayList<ClassRoom> classRooms = timeTable.getDay(pos.getDay()).getTimeSlots().get(pos.getTimeSlot()).getClassRooms();
        for(ClassRoom classRoom:classRooms){
            
            if(classRoom.equals(notAllowedRoom)){
                if(classRoom.getAssignedExam()!=null && timeTable.getExam(pos)!=null){
                    //return (-1)*getFitness();
                    return 0;
                }else{
                    return getFitness();
                }
                
            }
        }
        return 0;
    }

    @Override
    public boolean isApplicableTo(Object classRoom) {
         
        return classRoom.equals(allowedRoom);
    }
    
    public String getValue(){
        return "this is a 2 class room contraint"+allowedRoom +notAllowedRoom;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClassRoomWhileClassRoomConstraint other = (ClassRoomWhileClassRoomConstraint) obj;
        if (!Objects.equals(this.allowedRoom, other.allowedRoom)) {
            return false;
        }
        if (!Objects.equals(this.notAllowedRoom, other.notAllowedRoom)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return allowedRoom.getName() +" Or "+notAllowedRoom.getName()+" can't be occupied at the same time ";
    }
    
}
