package gui;

import javax.swing.*;
import java.awt.*;

class SpaceButton extends JButton {

    /** 
     * Constructors 
     */
    public SpaceButton() throws IllegalArgumentException {
        super();
        setDefBackground();
        super.setPreferredSize(new Dimension(3, 3));
        setVisible(true);
    }

    /** 
     * Sets the default background of the button 
     */
    public void setDefBackground() {
        super.setIcon(new ImageIcon(getClass().getResource("/Space.jpg")));
    }

}