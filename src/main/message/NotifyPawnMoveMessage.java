package message;

import static java.lang.Integer.parseInt;

import gameboard.Coordinate;
import gameboard.PawnMove;

/**
 * Represents a ATARI message as defined in the protocol document.
 *
 * The format of this message is:
 * ATARI pNumber (column, row)
 *
 * For more details, see:
 * http://cs.potsdam.edu/Classes/405/assignments/project/protocol.txt
 */
public class NotifyPawnMoveMessage extends NotifyMoveMessage {

    /**
     * Regex pattern that matches a ATARI message in the pawn-move version.
     * It matches these capturing groups:
     *   1: pNumber
     *   2: column
     *   3: row
     */
    public static final String PATTERN = "ATARI\\s+"
                                       + "(1|2|3|4)\\s+"
                                       + "\\(\\s*([0-8])\\s*,\\s*([0-8])\\s*\\)";

    public NotifyPawnMoveMessage(int pNumber, PawnMove move) {
        super(pNumber, move);
    }

    /**
     * @see Message#toString()
     */
    public String toString() {
        PawnMove moveP = (PawnMove)move;
        return String.format("ATARI %d (%d, %d)",
                playerNumber,
                moveP.destination.column,
                moveP.destination.row);
    }

    /**
     * Parses message and return a new NotifyPawnMoveMessage.
     *
     * Case is ignored, as is leading and trailing whitespace.
     *
     * @throws MessageFormatException - if the message could not be parsed
     */
    public static NotifyPawnMoveMessage fromString(String message) throws MessageFormatException {
        String[] groups = parse(PATTERN, message);
        Coordinate dest = new Coordinate(parseInt(groups[3]), parseInt(groups[2]));
        return new NotifyPawnMoveMessage(parseInt(groups[1]), new PawnMove(dest));
    }
}
