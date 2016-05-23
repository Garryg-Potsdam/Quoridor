package gui;

import javax.swing.*;
import java.awt.*;

class WallButton extends JButton {

    private Direction dir; // Direction of wall
    private boolean wallplaced;

    /** 
     * Constructors 
     */
    public WallButton(Direction d) throws IllegalArgumentException {
        super();
        this.dir = d;
        this.wallplaced = false;
        setSize(dir);
        super.setIcon(new ImageIcon(getClass().getResource("/WallI.jpg")));
        setVisible(true);
    }

    /** 
     * Sets the size of the button based on direction 
     * @param d direction of the button 
     */
    public void setSize(Direction d) {
        switch (d) {
            case VERTICAL:
                super.setPreferredSize(new Dimension(1, 3));
                break;
            case HORIZONTAL:
                super.setPreferredSize(new Dimension(3, 1));
                break;
            case CONNECTORS:
                super.setPreferredSize(new Dimension(1, 1));
                break;
        }
    }

    /**
     * Changes the wall texture to show a wall has been placed
     */
    public void placeWall() {
        if (dir == Direction.VERTICAL) {
            super.setIcon(new ImageIcon(getClass().getResource("/WallPV.jpg")));
        } else {
            super.setIcon(new ImageIcon(getClass().getResource("/WallPH.jpg")));
        }
    }

    public void placeWall(Direction d) {
        if (d == Direction.VERTICAL)
            super.setIcon(new ImageIcon(getClass().getResource("/WallPV.jpg")));
        else
            super.setIcon(new ImageIcon(getClass().getResource("/WallPH.jpg")));        
    }

    /** 
     * Gets the direction
     * @return the direction of the button 
     */
    public Direction getDir() {
        return this.dir;
    }

    public boolean getWallPlaced() {
        return this.wallplaced;
    }

    /** 
     * Direction enums for specifying the button direction 
     */
    public enum Direction {
        VERTICAL, HORIZONTAL, CONNECTORS
    }
}