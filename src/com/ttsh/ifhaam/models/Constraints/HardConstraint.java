/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models.Constraints;



/**
 *
 * @author Ahmed
 */
public abstract class HardConstraint extends Constraint{
    public static final int DEFAULT_HARD_CONSTRAINT_VAL = 40;
    
    public HardConstraint(){
        super(DEFAULT_HARD_CONSTRAINT_VAL);
    }
    
    public HardConstraint(int val){
        super(val);
    }
    
    
}
