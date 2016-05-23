package AI;

import message.*;
import gameboard.*;
import java.util.*;

public class AIveMind {

    public static final String FATAL_EXCEPTION = "fex";
    public static final String FOR_TRUE = "4tr";
    public static final String HIVE_MIND = "hvm";
    public static final String MURKA = "mur";
    public static final String ORTYS = "ort";

    private int pNumber;
    private Board gb;
    private Deque<Wall> walls;
    private String[] names;
    private boolean onBook = false;
    private int wallPathDiff = 1;

    public AIveMind(int pNumber, Board gb, String[] names) {
        this.pNumber = pNumber;
        this.gb = gb;
        this.names = names;

        walls = new ArrayDeque<>();
        walls.add(new Wall(new Coordinate(2, 0), Wall.Direction.VERTICAL));
        walls.add(new Wall(new Coordinate(6, 0), Wall.Direction.VERTICAL));
        walls.add(new Wall(new Coordinate(7, 1), Wall.Direction.HORIZONTAL));
        walls.add(new Wall(new Coordinate(7, 7), Wall.Direction.HORIZONTAL));
        walls.add(new Wall(new Coordinate(0, 1), Wall.Direction.HORIZONTAL));
        walls.add(new Wall(new Coordinate(7, 3), Wall.Direction.HORIZONTAL));
        walls.add(new Wall(new Coordinate(7, 5), Wall.Direction.HORIZONTAL));
        walls.add(new Wall(new Coordinate(4, 0), Wall.Direction.VERTICAL));

        if (names.length == 2) {
            if (pNumber == 2 && (isOpponent(ORTYS) || isOpponent(MURKA)))
                onBook = true;

            if (isOpponent(FATAL_EXCEPTION)) {
                wallPathDiff = 3;
                gb.setPathChance(6, 100);
            } else if (isOpponent(ORTYS) || isOpponent(MURKA)) {
                gb.setPathChance(7, 100);
            }
        }
    }

    private boolean isOpponent(String teamId) {
        for (String name : names) {
            if (name.startsWith(teamId))
                return true;
        }
        return false;
    }

    /**
     * @return - a message of either a wall or pawn move
     */
    public Move getMove() {
        if (onBook) {
            if (walls.size() > 0) {
                Wall wall = walls.removeFirst();
                if (onBook = gb.placeWall(pNumber, wall))
                    return new WallMove(wall);
            } else {
                onBook = false;
            }
            return gb.bestMove(pNumber);
        }

        int closestPlayer = gb.getClosestPlayer();
        WallMove greatWall = gb.greatWall(pNumber, closestPlayer, wallPathDiff);
        if (greatWall != null)
            return greatWall;
        if (closestPlayer != pNumber && gb.getWalls(pNumber) > 0) {
            WallMove move = null;
            move = gb.bestWall(pNumber, closestPlayer);
            if (move != null) {
                return move;
            }
        }
        return gb.bestMove(pNumber);
    }
}
