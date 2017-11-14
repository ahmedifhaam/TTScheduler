/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models.Constraints;

import com.ttsh.ifhaam.models.Subject;

/**
 *
 * @author Ahmed
 */
public class OfferedYearSubjectBeforeDay extends SubjectBeforeDay{
    int offeredYear;
    
    public OfferedYearSubjectBeforeDay(int fitness, int dayBefore,int offeredYear) {
        super(fitness, dayBefore);
        this.offeredYear = offeredYear;
    }

    @Override
    public boolean isApplicableTo(Object subject) {
        return ((Subject)subject).getYear()==offeredYear;
    }
    
}
