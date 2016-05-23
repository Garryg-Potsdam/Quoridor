package message;

import static java.lang.Integer.parseInt;

import gameboard.Coordinate;
import gameboard.Wall;
import gameboard.WallMove;

/**
 * Represents a TESUJI message as defined in the protocol document.
 *
 * The format of this message is:
 * TESUJI [(column, row), direction]
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
public class WallMoveMessage extends MoveMessage {

    /**
     * Regex pattern that matches a TESUJI message in the wall-move versions.
     * It matches these capturing groups:
     *   1: column
     *   2: row
     *   3: direction
     */
    public static final String PATTERN = "TESUJI\\s+"
                                       + "\\[\\s*"
                                       + "\\(\\s*([0-8])\\s*"
                                       + ",\\s*([0-8])\\s*\\)\\s*"
                                       + ",\\s*([hv])\\s*\\]";

    public WallMoveMessage(WallMove move) {
        super(move);
    }

    /**
     * @see Message#toString()
     */
    public String toString() {
        WallMove wMove = (WallMove)move;
        String dir = (wMove.wall.direction == Wall.Direction.HORIZONTAL) ? "h" : "v";
        return String.format("TESUJI [(%d, %d), %s]",
                wMove.wall.position.column,
                wMove.wall.position.row,
                dir);
    }

    /**
     * Parses message and return a new WallMoveMessage.
     *
     * Case is ignored, as is leading and trailing whitespace.
     *
     * @throws MessageFormatException - if the message could not be parsed
     */
    public static WallMoveMessage fromString(String message) throws MessageFormatException {
        String[] groups = parse(PATTERN, message);
        Wall wall;
        Coordinate pos = new Coordinate(parseInt(groups[2]), parseInt(groups[1]));
        if (groups[3].toLowerCase().equals("h")) {
            wall = new Wall(pos, Wall.Direction.HORIZONTAL);
        } else {
            wall = new Wall(pos, Wall.Direction.VERTICAL);
        }
        return new WallMoveMessage(new WallMove(wall));
    }
}
