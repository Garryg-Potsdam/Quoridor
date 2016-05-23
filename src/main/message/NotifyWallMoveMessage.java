package message;

import static java.lang.Integer.parseInt;

import gameboard.Coordinate;
import gameboard.Wall;
import gameboard.WallMove;

/**
 * Represents a ATARI message as defined in the protocol document.
 *
 * The format of this message is:
 * ATARI pNumber [(column, row), direction]
 *
 * In the boxes below, the wall is shown with a double line.
 * The space given by (column, row) is marked with an X.
 *
 * ┌───╥───┐        ┌───┬───┐
 * │ ╳ ║   │        │ ╳ │   │
 * ├───╫───┤        ╞═══╪═══╡
 * │   ║   │        │   │   │
 * └───╨───┘        └───┴───┘
 *
 * For more details, see:
 * http://cs.potsdam.edu/Classes/405/assignments/project/protocol.txt
 */
public class NotifyWallMoveMessage extends NotifyMoveMessage {

    /**
     * Regex pattern that matches a ATARI message in the wall-move version.
     * It matches these capturing groups:
     *   1: pNumber
     *   2: column
     *   3: row
     *   4: direction
     */
    public static final String PATTERN = "ATARI\\s+"
                                       + "(1|2|3|4)\\s+"
                                       + "\\[\\s*"
                                       + "\\(\\s*([0-8])\\s*"
                                       + ",\\s*([0-8])\\s*\\)"
                                       + ",\\s*([hv])\\s*\\]";

    public NotifyWallMoveMessage(int pNumber, WallMove move) {
        super(pNumber, move);
    }

    /**
     * @see Message#toString()
     */
    public String toString() {
        WallMove moveW = (WallMove)move;
        String dir = (moveW.wall.direction == Wall.Direction.HORIZONTAL) ? "h" : "v";
        return String.format("ATARI %d [(%d, %d), %s]",
                playerNumber,
                moveW.wall.position.column,
                moveW.wall.position.row,
                dir);
    }

    /**
     * Parses message and returns a new NotifyWallMoveMessage.
     *
     * Case is ignored, as is leading and trailing whitespace.
     *
     * @throws MessageFormatException - if the message could not be parsed
     */
    public static NotifyWallMoveMessage fromString(String message) throws MessageFormatException {
        String[] groups = parse(PATTERN, message);
        Coordinate pos = new Coordinate(parseInt(groups[3]), parseInt(groups[2]));

        Wall wall;
        if (groups[4].toLowerCase().equals("h"))
            wall = new Wall(pos, Wall.Direction.HORIZONTAL);
        else
            wall = new Wall(pos, Wall.Direction.VERTICAL);

        return new NotifyWallMoveMessage(parseInt(groups[1]), new WallMove(wall));
    }
}
