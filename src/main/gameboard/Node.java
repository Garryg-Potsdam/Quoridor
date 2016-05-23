/*Warren Squires and Joseph Dobriko
* This helper class will construct a node and keep track of
* the edges incident to that node, as well as a method to remove
* those edges as necessary*/
package gameboard;

import java.util.*;

public class Node {

//	private int check;
//	private int player = -1;
    private int col, row;
    private boolean right, down;
    public boolean occupied;
    public boolean brc = true;

    public Node(int col, int row) {
        this.col = col;
        this.row = row;        
    }

    public int getColCoordinate() {
        return col;
    }

    public int getRowCoordinate() {
        return row;
    }

    public void addRightEdge() {
        if (!right) {
            right = true;
        } else {
            System.out.println("Node already has a right edge.");
        }
    }

    public void addDownEdge() {
        if (!down) {
            down = true;
        } else {
            System.out.println("Node already has a down edge.");
        }
    }

    public void removeRightEdge() {
        if (right) {
            right = false;
        } else {
            System.out.println("Node has no right edge.");
        }
    }

    public void removeDownEdge() {
        if (down) {
            down = false;
        } else {
            System.out.println("Node has no down edge.");
        }
    }

    public boolean getRightEdge() {
        return right;
    }

    public boolean getDownEdge() {
        return down;
    }
}
