/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models.Constraints;

import com.ttsh.ifhaam.models.Position;
import com.ttsh.ifhaam.models.Subject;
import com.ttsh.ifhaam.models.TimeTable;

/**
 *
 * @author Ahmed
 */
public abstract class Constraint {
    int fitness;
    
    public Constraint(int fitness){
        this.fitness = fitness;
    }
    
    //this method should be used to return fitness value 
    //taking the relative difference between the same type of cosntraints 
    public abstract int calculateFitness(Position pos,TimeTable timeTable );
    
    //Checks whether parameter subject is applicable of this constraint
    public abstract boolean isApplicableTo(Subject subject);
    
    public int getFitness(){
     return fitness;   
    }
}
