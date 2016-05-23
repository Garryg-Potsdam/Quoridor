package gui;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;

import gameboard.Coordinate;

class BoardPanel extends JPanel {

    private JButton[][] boardButtons;

    public BoardPanel() {
        boardButtons = new JButton[17][17];

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        // creating the buttons, both walls and spaces
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        boardButtons[i][j] = new SpaceButton();
                    } else {
                        boardButtons[i][j] = new WallButton(WallButton.Direction.VERTICAL);
                    }
                } else {
                    if (j % 2 == 0) {
                        boardButtons[i][j] = new WallButton(WallButton.Direction.HORIZONTAL);
                    } else {
                        boardButtons[i][j] = new WallButton(WallButton.Direction.CONNECTORS);
                    }
                }

                gbc.gridx = j;
                gbc.gridy = i;
                Dimension dim = boardButtons[i][j].getPreferredSize();
                gbc.weightx = dim.width;
                gbc.weighty = dim.height;
                add(boardButtons[i][j], gbc);
            }

        }
    }

    /** 
     * Gets the space button
     * @param pos the coordinate of the button 
     * @return SpaceButton at given coordinate 
     */
    public SpaceButton getSpaceButton(Coordinate pos) {
        return (SpaceButton)boardButtons[pos.row * 2][pos.column * 2];
    }

    /** 
     * Gets the wall button
     * @param pos the coordinate of the button
     * @param dir the direction of the button
     * @return WallButton at given coordinate and direction 
     */
    public WallButton getWallButton(Coordinate pos, WallButton.Direction dir) {
        switch (dir) {
            case VERTICAL:
                return (WallButton)boardButtons[pos.row * 2][pos.column * 2 + 1];
            case HORIZONTAL:
                return (WallButton)boardButtons[pos.row * 2 + 1][pos.column * 2];
            default:
                // CONNECTORS
                return (WallButton)boardButtons[pos.row * 2 + 1][pos.column * 2 + 1];
        }
    }
}