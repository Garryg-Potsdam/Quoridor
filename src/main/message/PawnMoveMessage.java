package message;

import static java.lang.Integer.parseInt;

import gameboard.Coordinate;
import gameboard.PawnMove;

/**
 * Represents a TESUJI message as defined in the protocol document.
 *
 * The format of this message is:
 * TESUJI (column, row)
 *
 * For more details, see:
 * http://cs.potsdam.edu/Classes/405/assignments/project/protocol.txt
 */
public class PawnMoveMessage extends MoveMessage {

    /**
     * Regex pattern that matches a TESUJI message in the pawn-move version.
     * It matches these capturing groups:
     *   1: column
     *   2: row
     */
    public static final String PATTERN = "TESUJI\\s+"
                                       + "\\(\\s*([0-8])\\s*,\\s*([0-8])\\s*\\)";

    public PawnMoveMessage(PawnMove move) {
        super(move);
    }

    /**
     * @see Message#toString()
     */
    public String toString() {
        PawnMove pMove = (PawnMove)move;
        return String.format("TESUJI (%d, %d)",
                pMove.destination.column,
                pMove.destination.row);
    }

    /**
     * Parses message and return a new PawnMoveMessage.
     *
     * Case is ignored, as is leading and trailing whitespace.
     *
     * @throws MessageFormatException - if the message could not be parsed
     */
    public static PawnMoveMessage fromString(String message) throws MessageFormatException {
        String[] groups = parse(PATTERN, message);
        Coordinate dest = new Coordinate(parseInt(groups[2]), parseInt(groups[1]));
        return new PawnMoveMessage(new PawnMove(dest));
    }
}
