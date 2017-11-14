/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttsh.ifhaam.models;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Ahmed
 */
public class ClassRoomListCellRenderer implements ListCellRenderer{
    @Override
   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
       JLabel lbl = new JLabel(" "+((ClassRoom)value).getName()+" - "+((ClassRoom)value).getSize());
       if(isSelected){
           lbl.setBackground(Color.BLUE);
           lbl.setOpaque(true);
           lbl.setForeground(Color.WHITE);
       }
       return lbl;
   }
}
    

