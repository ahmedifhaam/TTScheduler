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

/**
 *
 * @author Ahmed
 */
public abstract class SubjectBeforeDay extends SoftConstraint{
    int dayBefore;
    public SubjectBeforeDay(int fitness,int dayBefore) {
        super(fitness);
        this.dayBefore = dayBefore;
    }

    @Override
    public int calculateFitness(Position pos, TimeTable timeTable) {
         //To change body of generated methods, choose Tools | Templates.
         if(pos.getDay()<dayBefore) return getFitness();
         else return 0;//
             //return (-1)*getFitness();
    }

    protected int getDayBefore(){
        return dayBefore;
    }
    
    
}
