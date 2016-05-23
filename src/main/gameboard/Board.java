package gameboard;

import gameboard.Wall.Direction;
import java.util.*;

/* This class will create a board consisting of 9x9 tiles and
 * an edge connecting each adjacent tile
 */
public class Board {

    private Node[][] board;
    private int[] walls;
    private Map<Integer, Coordinate> pawns;
    List<Coordinate> validMove;
    private int moveCheck;
    private Wall lastWall;

    /**
     * Constructor for a new Board object, creating a 9 x 9 grid of Nodes with
     * Right and Down edges to simulate a Quoridor board
     */
    public Board() {
        pawns = new HashMap<Integer, Coordinate>();
        board = new Node[9][9];
        for (int col = 0; col < 9; col++) {
            for (int row = 0; row < 9; row++) {
                board[col][row] = new Node(col, row);
                if (row < 8) {
                    board[col][row].addDownEdge();
                }
                if (col < 8) {
                    board[col][row].addRightEdge();
                }
            }
        }
    }

    /**
     * @param pNumber - the player to check if they won
     * @return - true if player made to opposite side false otherwise
     */
    public boolean isWinner(int pNumber) {
        Map<Integer, Coordinate> pawns = getPawnPositions();
        switch (pNumber) {
            case 1:
                return pawns.get(1).row == 8;
            case 2:
                return pawns.get(2).row == 0;
            case 3:
                return pawns.get(3).column == 8;
            case 4:
                return pawns.get(4).column == 0;
            default:
                throw new IllegalArgumentException("No such player exists.");
        }
    }

    /**
     * @param walls - the amount of walls they get
     */
    public void setWalls(int walls) {
        if (walls == 10) {
            this.walls = new int[]{0, 10, 10};
        } else {
            this.walls = new int[]{0, 5, 5, 5, 5};
        }
    }

    /*returns the last wall placed for either drawing on the GUI or for AI
    *move calculating*/
    public Wall lastWall() {
        return lastWall;
    }

    /**
     * @param pNumber - the player to get wall count for
     * @return - an int of the walls remaining
     */
    public int getWalls(int pNumber) {
        return walls[pNumber];
    }

    /*returns the map of player positions*/
    public Map<Integer, Coordinate> getPawnPositions() {
        return pawns;
    }

    /**
     * @param pNumber - the pawn whos position you want
     * @return - the pawns Coordinate object position
     */
    public Coordinate getPawnPosition(int pNumber) {
        return pawns.get(pNumber);
    }

    /*places a pawn at a given location, should only be used for
    *initialization of the game, NOT for moving players.*/
    public void placePawn(int player, Coordinate position) {
        board[position.column][position.row].occupied = true;
        pawns.put(player, position);
    }

    /*Returns the Board object and the current state of edges*/
    public Node[][] getBoard() {
        return board;
    }

    /**
     * @return - return the amount of edges of a node
     */
    public int getEdges(Coordinate node) {
        int edges = 0;
        if (getUpEdge(node.column, node.row)) {
            edges++;
        }
        if (getRightEdge(node.column, node.row)) {
            edges++;
        }
        if (getDownEdge(node.column, node.row)) {
            edges++;
        }
        if (getLeftEdge(node.column, node.row)) {
            edges++;
        }
        return edges;
    }

    /*Returns the current state of a node's right edge, true for present (so
    *from that node it is possible to move right as there is no wall) or false
    *for there being no edge there, or a wall having been placed.*/
    public boolean getRightEdge(int col, int row) {
        if (col >= 8) {
            return false;
        }
        return board[col][row].getRightEdge();
    }

    /*Same as the previous method, but for the node's down edge.*/
    public boolean getDownEdge(int col, int row) {
        if (row >= 8) {
            return false;
        }
        return board[col][row].getDownEdge();
    }

    /*Returns a node's left edge state. As a left edge was not implemented
    *in the Node class, this method looks to the node to the left's right edge
    *and returns its state.*/
    public boolean getLeftEdge(int col, int row) {
        if (col <= 0) {
            return false;
        }
        return board[col - 1][row].getRightEdge();
    }

    /**
     * @return - whether node contains an upper connection or not
     */
    public boolean getUpEdge(int col, int row) {
        if (row <= 0) {
            return false;
        }
        return board[col][row - 1].getDownEdge();
    }

    /**
     * @param pNumber - the player who's turn it is
     * @param move - the move to be made or wall to be placed
     * @return - true if successful false otherwise
     */
    public boolean makeMove(int pNumber, Move move) {
        if (move instanceof PawnMove) {
            return movePawn(pNumber, ((PawnMove) move).destination);
        }
        return placeWall(pNumber, ((WallMove) move).wall);
    }

    /**
     * Removes the last wall placed
     */
    public void removeWall(int placer, int row, int col, Direction d) {
        if (d == Direction.HORIZONTAL) {
            board[col][row].addDownEdge();
            board[col + 1][row].addDownEdge();
        } else {
            board[col][row].addRightEdge();
            board[col][row + 1].addRightEdge();
        }
        board[col][row].brc = true;
        walls[placer]++;
    }

    /* 
    * @param player - the player who is placing the wall
    * @param wall - the wall object to be placed
    * @return - true if successful false otherwise
     */
    public boolean placeWall(int player, Wall wall) {
        int col = wall.position.column;
        int row = wall.position.row;

        if (walls[player] == 0) {
            return false;
        }
        if (col > 7 || col < 0 || row > 7 || row < 0) {
            return false;
        }
        if (!board[col][row].brc) {
            return false;
        }
        if (wall.direction == Wall.Direction.HORIZONTAL) {
            if (board[col][row].getDownEdge() && board[col + 1][row].getDownEdge()) {
                board[col][row].removeDownEdge();
                board[col + 1][row].removeDownEdge();
                if (checkBlocked()) {
                    board[col][row].addDownEdge();
                    board[col + 1][row].addDownEdge();
                    return false;
                }
                lastWall = new Wall(wall.position, wall.direction);
                walls[player]--;
                board[col][row].brc = false;
                return true;
            }
        } else if (wall.direction == Wall.Direction.VERTICAL) {
            if (board[col][row].getRightEdge() && board[col][row + 1].getRightEdge()) {
                board[col][row].removeRightEdge();
                board[col][row + 1].removeRightEdge();
                if (checkBlocked()) {
                    board[col][row].addRightEdge();
                    board[col][row + 1].addRightEdge();
                    return false;
                }
                lastWall = new Wall(wall.position, wall.direction);
                walls[player]--;
                board[col][row].brc = false;
                return true;
            }
        }
        return false;
    }

    /**
     * @returns int of the shortest path for all players
     */
    public int getClosestPlayer() {
        int closestPlayer = -1;
        int shortestPath = 100;
        for (Integer i : pawns.keySet()) {
            int temp = getShortestPathLength(i, pawns.get(i));
            if (shortestPath > temp) {
                shortestPath = temp;
                closestPlayer = i;
            }
        }
        return closestPlayer;
    }

    // The metrics for the wall placement algorithm
    private int path;
    private double chance;

    public void setPathChance(int path, int chance) {
        this.path = path;
        this.chance = (double) chance / 100;
    }

    /**
     * @param placer - the person placing walls
     * @param walled - the person getting walled
     * @return - returns the best WallMove or null
     */
    public WallMove bestWall(int placer, int walled) {
        if (Math.random() > chance || getShortestPathLength(walled, pawns.get(walled)) > path) {
            return null;
        }
        WallMove checkWall = greatWall(placer, walled, 0);
        if (checkWall != null) {            
            return checkWall;
        }

        double fitness = Double.NEGATIVE_INFINITY;
        Coordinate coord = null;
        Wall.Direction d = null;
        int shortestPath = getShortestPathLength(walled, pawns.get(walled));
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boolean check = placeWall(placer, new Wall(new Coordinate(row, col), Wall.Direction.HORIZONTAL));
                if (check) {
                    int theirLength = getShortestPathLength(walled, pawns.get(walled));
                    int ourLength = getShortestPathLength(placer, pawns.get(placer));
                    double tempFitness = 1.5 * theirLength - ourLength;
                    if (tempFitness > fitness) {
                        coord = new Coordinate(row, col);
                        fitness = tempFitness;
                        d = Wall.Direction.HORIZONTAL;
                    }
                    removeWall(placer, row, col, Direction.HORIZONTAL);
                }
                check = placeWall(placer, new Wall(new Coordinate(row, col), Wall.Direction.VERTICAL));
                if (check) {
                    int theirLength = getShortestPathLength(walled, pawns.get(walled));
                    int ourLength = getShortestPathLength(placer, pawns.get(placer));
                    double tempFitness = 1.5 * theirLength - ourLength;
                    if (tempFitness > fitness) {
                        coord = new Coordinate(row, col);
                        fitness = tempFitness;
                        d = Wall.Direction.VERTICAL;
                    }
                    removeWall(placer, row, col, Direction.VERTICAL);
                }
            }
        }
        if (coord == null) {
            return null;
        }
        Wall wall = new Wall(coord, d);
        placeWall(placer, wall);
        int finalShortestPath = getShortestPathLength(walled, pawns.get(walled));
        if (finalShortestPath == shortestPath) {
            removeWall(placer, coord.row, coord.column, d);
            return null;
        }
        System.out.println("Placed Wall: " + wall);
        System.out.println("Player " + walled + "'s path now " 
                            + (getShortestPathLength(walled, pawns.get(walled)) - getShortestPathLength(placer, pawns.get(placer)))
                            + " moves longer.");
        return new WallMove(wall);
    }

    /**
     * @param placer - the person placing walls
     * @param walled - the person getting walled
     * @return - returns the best WallMove or null
     */
    public WallMove greatWall(int placer, int walled, int diff) {
        List<Wall> wallList = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boolean check = placeWall(placer, new Wall(new Coordinate(row, col), Wall.Direction.HORIZONTAL));
                if (check) {
                    int theirLength = getShortestPathLength(walled, pawns.get(walled));
                    int ourLength = getShortestPathLength(placer, pawns.get(placer));
                    if (theirLength > ourLength) {
                        wallList.add(new Wall(new Coordinate(row, col), Direction.HORIZONTAL));
                    }
                    removeWall(placer, row, col, Direction.HORIZONTAL);
                }
                check = placeWall(placer, new Wall(new Coordinate(row, col), Wall.Direction.VERTICAL));
                if (check) {
                    int theirLength = getShortestPathLength(walled, pawns.get(walled));
                    int ourLength = getShortestPathLength(placer, pawns.get(placer));
                    if (theirLength > ourLength) {
                        wallList.add(new Wall(new Coordinate(row, col), Direction.VERTICAL));
                    }
                    removeWall(placer, row, col, Direction.VERTICAL);
                }
            }
        }
        if (wallList.size() > 0) {
            int length = 0;
            Wall tempWall = null;
            for (Wall w : wallList) {
                if (placeWall(placer, w)) {
                    int theirLength = getShortestPathLength(walled, pawns.get(walled));
                    if (theirLength > length) {
                        tempWall = w;
                        length = theirLength;
                    }
                    removeWall(placer, w.position.row, w.position.column, w.direction);
                }
                if (length <= diff)
                    return null;
            }
            System.out.println("Placed Great Wall: " + tempWall);
            System.out.println("Player " + walled + "'s path now " 
                            + (length - getShortestPathLength(placer, pawns.get(placer)))
                            + " moves longer.");
            placeWall(placer, tempWall);
            return new WallMove(tempWall);
        }
        return null;
    }

    /**
     * @return pawns size
     */
    public int getPawnsSize() {
        return pawns.size();
    }

    /**
     * @param pNumber - the player whos best move you need
     * @return - returns a best move PawnMove object
     */
    public PawnMove bestMove(int pNumber) {
        List<Coordinate> moves = getValidMoveList(pNumber);
        int shortestPath = 100;
        for (Coordinate coord : moves) {
            int temp = getShortestPathLength(pNumber, coord);
            if (shortestPath > temp) {
                shortestPath = temp;
            }
        }
        List<Coordinate> shortestMoves = new ArrayList<>();
        for (Coordinate coord : moves) {
            int temp = getShortestPathLength(pNumber, coord);
            if (shortestPath == temp) {
                shortestMoves.add(coord);
            }
        }
        Random rand = new Random();
        return new PawnMove(shortestMoves.get(rand.nextInt(shortestMoves.size())));
    }

    /**
     * @param pNumber - player to get shortest path for
     * @param coord - the coordinate that you expect the player to be at
     * @return - the shortest path length
     */
    public int getShortestPathLength(int pNumber, Coordinate coord) {
        int[][] distances = fillDistances(coord);

        int shortest = 100;
        switch (pNumber) {
            case 1:
                if (coord.row == 8) {
                    return 0;
                }
                for (int i = 0; i < 9; i++) {
                    if (distances[i][8] != -1) {
                        shortest = Math.min(shortest, distances[i][8]);
                    }
                }
                break;
            case 2:
                if (coord.row == 0) {
                    return 0;
                }
                for (int i = 0; i < 9; i++) {
                    if (distances[i][0] != -1) {
                        shortest = Math.min(shortest, distances[i][0]);
                    }
                }
                break;
            case 3:
                if (coord.column == 8) {
                    return 0;
                }
                for (int i = 0; i < 9; i++) {
                    if (distances[8][i] != -1) {
                        shortest = Math.min(shortest, distances[8][i]);
                    }
                }
                break;
            case 4:
                if (coord.column == 0) {
                    return 0;
                }
                for (int i = 1; i < 9; i++) {
                    if (distances[0][i] != -1) {
                        shortest = Math.min(shortest, distances[0][i]);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Error: Invalid Player Number.");
        }

        return shortest;
    }

    /**
     * @return whether a wall move was blocked or not
     */
    public boolean checkBlocked() {
        for (Integer i : pawns.keySet()) {
            Coordinate currentPawn = pawns.get(i);
            int[][] distances = fillDistances(currentPawn);
            int temp = 0;
            for (int j = 0; j <= 8; j++) {
                if (i == 1 && distances[j][8] == -1) {
                    temp++;
                } else if (i == 2 && distances[j][0] == -1) {
                    temp++;
                } else if (i == 3 && distances[8][j] == -1) {
                    temp++;
                } else if (i == 4 && distances[0][j] == -1) {
                    temp++;
                }

                if (temp == 9) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[][] fillDistances(Coordinate source) {
        LinkedList<Node> nodes = new LinkedList<Node>();
        nodes.add(board[source.column][source.row]);
        int[][] distances = new int[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                distances[x][y] = -1;
            }
        }
        Node node;
        Coordinate cnode;

        int distance = 0; // Starting value of pawn
        int edges, count = 0, currentNodesAtDistance = 1, nodesAtNextDistance = 0;
        while (nodes.peek() != null) {
            node = nodes.poll();
            distances[node.getColCoordinate()][node.getRowCoordinate()] = distance;
            cnode = new Coordinate(node.getRowCoordinate(), node.getColCoordinate());
            edges = getEdges(cnode);
            if (getUpEdge(node.getColCoordinate(), node.getRowCoordinate())) {
                if (distances[node.getColCoordinate()][node.getRowCoordinate() - 1] == -1 || distance + 1 < distances[node.getColCoordinate()][node.getRowCoordinate() - 1]) {
                    nodes.add(board[node.getColCoordinate()][node.getRowCoordinate() - 1]);
                } else {
                    edges--;
                }
            }
            if (getRightEdge(node.getColCoordinate(), node.getRowCoordinate())) {
                if (distances[node.getColCoordinate() + 1][node.getRowCoordinate()] == -1 || distance + 1 < distances[node.getColCoordinate() + 1][node.getRowCoordinate()]) {
                    nodes.add(board[node.getColCoordinate() + 1][node.getRowCoordinate()]);
                } else {
                    edges--;
                }
            }
            if (getDownEdge(node.getColCoordinate(), node.getRowCoordinate())) {
                if (distances[node.getColCoordinate()][node.getRowCoordinate() + 1] == -1 || distance + 1 < distances[node.getColCoordinate()][node.getRowCoordinate() + 1]) {
                    nodes.add(board[node.getColCoordinate()][node.getRowCoordinate() + 1]);
                } else {
                    edges--;
                }
            }
            if (getLeftEdge(node.getColCoordinate(), node.getRowCoordinate())) {
                if (distances[node.getColCoordinate() - 1][node.getRowCoordinate()] == -1 || distance + 1 < distances[node.getColCoordinate() - 1][node.getRowCoordinate()]) {
                    nodes.add(board[node.getColCoordinate() - 1][node.getRowCoordinate()]);
                } else {
                    edges--;
                }
            }
            count++;
            nodesAtNextDistance += edges;
            if (count == currentNodesAtDistance) {
                distance++;
                count = 0;
                currentNodesAtDistance = nodesAtNextDistance;
                nodesAtNextDistance = 0;
            }
        }
        return distances;
    }

    /* 
    * @param player - the player who moving
    * @param dest - the desired destination
    * @return - true if successful false otherwise
     */
    public boolean movePawn(int player, Coordinate dest) {
        if (dest.column < 0 || dest.column > 8 || dest.row < 0 || dest.row > 8) {
            return false;
        }
        int destRow = dest.row;
        int destColumn = dest.column;
        int pawnRow = pawns.get(player).row;
        int pawnColumn = pawns.get(player).column;
        for (int i = 1; i <= pawns.size(); i++) {
            if (pawns.containsKey(i)) {
                if (destRow == pawns.get(i).row && destColumn == pawns.get(i).column) {
                    return false;
                }
            }
        }
        validMove = getValidMoveList(player);
        if (validMove.contains(dest)) {
            board[pawns.get(player).column][pawns.get(player).row].occupied = false;
            board[dest.column][dest.row].occupied = true;
            pawns.put(player, dest);
            return true;
        } else {
            return false;
        }
    }

    public List<Coordinate> getValidMoveList(int player) {
        validMove = new ArrayList<Coordinate>();
        int pawnRow = pawns.get(player).row;
        int pawnColumn = pawns.get(player).column;
        if (getRightEdge(pawnColumn, pawnRow)) {
            if (board[pawnColumn + 1][pawnRow].occupied) {
                jump(player, pawnColumn + 1, pawnRow, validMove, pawnColumn, pawnRow);
            } else {
                validMove.add(new Coordinate(pawnRow, pawnColumn + 1));
            }
        }
        if (getLeftEdge(pawnColumn, pawnRow)) {
            if (board[pawnColumn - 1][pawnRow].occupied) {
                jump(player, pawnColumn - 1, pawnRow, validMove, pawnColumn, pawnRow);
            } else {
                validMove.add(new Coordinate(pawnRow, pawnColumn - 1));
            }
        }
        if (getDownEdge(pawnColumn, pawnRow)) {
            if (board[pawnColumn][pawnRow + 1].occupied) {
                jump(player, pawnColumn, pawnRow + 1, validMove, pawnColumn, pawnRow);
            } else {
                validMove.add(new Coordinate(pawnRow + 1, pawnColumn));
            }
        }
        if (getUpEdge(pawnColumn, pawnRow)) {
            if (board[pawnColumn][pawnRow - 1].occupied) {
                jump(player, pawnColumn, pawnRow - 1, validMove, pawnColumn, pawnRow);
            } else {
                validMove.add(new Coordinate(pawnRow - 1, pawnColumn));
            }
        }
        return validMove;
    }

    private void jump(int playerNum, int pawnColumn, int pawnRow, List<Coordinate> validMove, int sourceColumn, int sourceRow) {
        if (pawnColumn == pawns.get(playerNum).column && pawnRow == pawns.get(playerNum).row) {
            return;
        }
        if (getRightEdge(pawnColumn, pawnRow) && !(pawnColumn + 1 == sourceColumn && pawnRow == sourceRow)) {
            if (board[pawnColumn + 1][pawnRow].occupied) {
                jump(playerNum, pawnColumn + 1, pawnRow, validMove, pawnColumn, pawnRow);
            } else {
                validMove.add(new Coordinate(pawnRow, pawnColumn + 1));
            }
        }
        if (getLeftEdge(pawnColumn, pawnRow) && !(pawnColumn - 1 == sourceColumn && pawnRow == sourceRow)) {
            if (board[pawnColumn - 1][pawnRow].occupied) {
                jump(playerNum, pawnColumn - 1, pawnRow, validMove, pawnColumn, pawnRow);
            } else {
                validMove.add(new Coordinate(pawnRow, pawnColumn - 1));
            }
        }
        if (getDownEdge(pawnColumn, pawnRow) && !(pawnColumn == sourceColumn && pawnRow + 1 == sourceRow)) {
            if (board[pawnColumn][pawnRow + 1].occupied) {
                jump(playerNum, pawnColumn, pawnRow + 1, validMove, pawnColumn, pawnRow);
            } else {
                validMove.add(new Coordinate(pawnRow + 1, pawnColumn));
            }
        }
        if (getUpEdge(pawnColumn, pawnRow) && !(pawnColumn == sourceColumn && pawnRow - 1 == sourceRow)) {
            if (board[pawnColumn][pawnRow - 1].occupied) {
                jump(playerNum, pawnColumn, pawnRow - 1, validMove, pawnColumn, pawnRow);
            } else {
                validMove.add(new Coordinate(pawnRow - 1, pawnColumn));
            }
        }
    }

    /**
     * @param player - the booted player
     */
    public void removePawn(int player) {
        board[pawns.get(player).column][pawns.get(player).row].occupied = false;
        pawns.remove(player);
    }
}
