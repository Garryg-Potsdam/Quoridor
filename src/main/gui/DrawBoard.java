package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;

import gameboard.*;

public class DrawBoard extends JFrame {

    protected Map<Integer, Coordinate> pawnPositions;
    protected Map<Integer, Icon> icons;
    protected BoardPanel board;

    public DrawBoard(String[] names) {
        super("Quoridor");

        loadIcons();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);

        board = new BoardPanel();

        //JPanel sidebar = new JPanel(new BorderLayout());
        //JLabel label = new JLabel("", icons.get(4), JLabel.CENTER);
	Sidebar sidebar = new Sidebar(names,icons) ;
	
        //sidebar.add(label, BorderLayout.CENTER);

        add(board, BorderLayout.CENTER);
        add(sidebar, BorderLayout.EAST);
        //setContentPane(board);

        pawnPositions = new HashMap<>();
    }

    public void makeMove(int pNumber, Move move) {
        if (move instanceof PawnMove) {
            PawnMove pMove = (PawnMove)move;
            movePawn(pNumber, pMove.destination);
        } else {
            WallMove wMove = (WallMove)move;
            buildWall(pNumber, wMove.wall);
        }
    }

    /**
     * Puts a pawn for the given player on the board at the given position.
     *
     * @param pNumber - must be 1-4
     * @param pos - row and column must be 0-8
     */
    public void placePawn(int pNumber, Coordinate pos) {
        if (pNumber < 1 || pNumber > 4)
            throw new IllegalArgumentException("Player number out of bounds");

        if (pos.row < 0 || pos.row > 8 || pos.column < 0 || pos.column > 8)
            throw new IllegalArgumentException("Pawn position out of bounds");

        board.getSpaceButton(pos).setIcon(icons.get(pNumber));
        pawnPositions.put(pNumber, pos);
    }
    
    public void winCondition(int pNumber){
	JOptionPane.showMessageDialog(this,"Player " + pNumber + " Won!!!!!!!!!!!!!!", "A winner is you", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Removes the given player's pawn from the board.
     *
     * Their pawn must have been placed already with placePawn().
     */
    public void removePawn(int pNumber) {
        if (!pawnPositions.containsKey(pNumber)) {
            String err = String.format("Player's pawn is not on the board");
            throw new IllegalArgumentException();
        }

        Coordinate pos = pawnPositions.remove(pNumber);
        board.getSpaceButton(pos).setDefBackground();
    }

    /**
     * Move the given player's pawn to the given position.
     *
     * Their pawn must have been placed already with placePawn().
     *
     * @param pNumber - must be 1-4
     * @param pos - row and column must be 0-8
     */
    public void movePawn(int pNumber, Coordinate pos) {
        removePawn(pNumber);
        placePawn(pNumber, pos);
    }

    private void loadIcons() {
        icons = new HashMap<>();
        icons.put(1, getIcon("/BCL.jpg"));
        icons.put(2, getIcon("/CCL.jpg"));
        icons.put(3, getIcon("/SMH.jpg"));
        icons.put(4, getIcon("/TVF.jpg"));
    }

    /** 
     * Gets the image for the icon 
     * @param filename filename of the image
     * @return the image as an imageIcon 
     */
    private Icon getIcon(String filename) {
        return new ImageIcon(getClass().getResource(filename));
    }

    /**
     * Draws a wall on the board starting at given wall and going down/right
     * @param pNumber - the number of the player who placed this will
     * @param wall - the wall to place
     */
    protected void buildWall(int pNumber, Wall wall) {
        Coordinate pos = wall.position;
        Wall.Direction dir = wall.direction;

        switch (dir) {
            // places vertical wall
            case VERTICAL:
                board.getWallButton(pos, WallButton.Direction.VERTICAL).placeWall();
                board.getWallButton(pos, WallButton.Direction.CONNECTORS).placeWall(WallButton.Direction.VERTICAL);
                pos = pos.down();
                board.getWallButton(pos, WallButton.Direction.VERTICAL).placeWall();
                break;
            // places horizontal wall
            case HORIZONTAL:
                board.getWallButton(pos, WallButton.Direction.HORIZONTAL).placeWall();
                board.getWallButton(pos, WallButton.Direction.CONNECTORS).placeWall(WallButton.Direction.HORIZONTAL);   
                pos = pos.right();
                board.getWallButton(pos, WallButton.Direction.HORIZONTAL).placeWall();
                break;
        }
    }

}
