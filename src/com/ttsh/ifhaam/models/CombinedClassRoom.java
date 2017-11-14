/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models;

import java.util.ArrayList;

/**
 *
 * @author Ahmed
 */
public class CombinedClassRoom extends ClassRoom{
    ArrayList<ClassRoom> combinedRooms;
    public CombinedClassRoom(int size, String name) {
        super(size, name);
        combinedRooms = new ArrayList<>();
    }
    
    public CombinedClassRoom(int size,String name,ArrayList<ClassRoom> list){
        super(size,name);
        combinedRooms = list;
    }
    
    public void addClassRoom(ClassRoom classRoom){
        combinedRooms.add(classRoom);
        
    }
    
    public void removeClassRoom(ClassRoom classRoom){
        combinedRooms.remove(classRoom);
    }
    
    public void removeClassRoom(int index){
        combinedRooms.remove(index);
    }

    public ArrayList<ClassRoom> getCombinedRooms() {
        return combinedRooms;
    }
    
   
    
}
