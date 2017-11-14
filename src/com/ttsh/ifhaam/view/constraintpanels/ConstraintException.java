/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.view.constraintpanels;

/**
 *
 * @author Ahmed
 */
public class ConstraintException extends Exception
{
    public static String VALUE = "Error retriving Constraint";
    public ConstraintException(String s){
        super(s);
    }
    
    public ConstraintException(){
        super();
    }
}
