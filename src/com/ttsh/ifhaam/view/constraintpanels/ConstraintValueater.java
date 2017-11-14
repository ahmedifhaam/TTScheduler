/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.view.constraintpanels;

import com.ttsh.ifhaam.models.Constraints.Constraint;

/**
 *
 * @author Ahmed
 */
public interface ConstraintValueater {
    public Constraint getConstraint() throws ConstraintException;
    
}
