/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models;

/**
 *
 * @author Ahmed
 */
public interface Constraint {
    public int calculateFitness(Subject subject);
    public boolean isApplicableTo(Subject subject);
}
