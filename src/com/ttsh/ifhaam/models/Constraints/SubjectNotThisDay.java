/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models.Constraints;

import com.ttsh.ifhaam.models.Position;
import com.ttsh.ifhaam.models.Subject;
import com.ttsh.ifhaam.models.TimeTable;
import java.util.Date;

/**
 *
 * @author Ahmed
 */
public class SubjectNotThisDay extends SoftConstraint {
    Subject subjectSelected;
    Date date;
    public SubjectNotThisDay(int fitness,Subject subject,Date date) {
        super(fitness);
        this.subjectSelected = subject;
        this.date = date;
    }

    @Override
    public int calculateFitness(Position pos, TimeTable timeTable) {
        if(timeTable.getDays().get(pos.getDay()).getDate().equals(date))return 0;
        //if(timeTable.getDays().get(pos.getDay()).equals(date))return 0;// return (-1)*getFitness();
        System.out.println("Date "+date + "selected date"+timeTable.getDay(pos.getDay()).getDate());
        System.out.println("Subejct "+subjectSelected );
        return getFitness();
    
    }

    @Override
    public boolean isApplicableTo(Object subject) {
        return this.subjectSelected.equals(((Subject)subject));
    }
    
}
