package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

class Sidebar extends Box{
    
    
    public Sidebar(String [] names,  Map <Integer, Icon> icons){
        super(BoxLayout.PAGE_AXIS);
        int count = 1;
        for(String name : names){
            add(new JLabel(name));
            add(new JLabel("", icons.get(count),JLabel.LEFT));
            add(createVerticalGlue());
            count++;
        }
    }
}


