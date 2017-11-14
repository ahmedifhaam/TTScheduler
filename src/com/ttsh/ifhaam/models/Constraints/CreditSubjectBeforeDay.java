/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models.Constraints;

import com.ttsh.ifhaam.controller.TimeTableManager;
import com.ttsh.ifhaam.models.Subject;

/**
 *
 * @author Ahmed
 */
public class CreditSubjectBeforeDay extends SubjectBeforeDay{
    int credit;
    public CreditSubjectBeforeDay(int fitness, int dayBefore,int credit) {
        super(fitness, dayBefore);
        this.credit = credit;
    }

    @Override
    public boolean isApplicableTo(Object subject) {
        return ((Subject)subject).getCredit()==credit;
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
        final CreditSubjectBeforeDay other = (CreditSubjectBeforeDay) obj;
        if (this.credit != other.credit) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return "Credit "+credit+" subjects are better be before "+TimeTableManager.getInstance().getDates()[getDayBefore()];
    }
     
    
}
