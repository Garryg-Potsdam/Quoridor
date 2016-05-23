package message;

import gameboard.Move;

/**
 * Represents a TESUJI message as defined in the protocol document.
 *
 * The format of this message is:
 * TESUJI (column, row)
 * or
 * TESUJI [(column, row), direction]
 *
 * For more details, see:
 * http://cs.potsdam.edu/Classes/405/assignments/project/protocol.txt
 */
public abstract class MoveMessage extends Message {

    public Move move;

    protected MoveMessage(Move move) {
        this.move = move;
    }

    /**
     * @see Message#type()
     */
    public Type type() {
        return Type.MOVE;
    }

    /**
     * Parses message and return a new PawnMoveMessage or WallMoveMessage,
     * depending on the format of the message.
     *
     * Case is ignored, as is leading and trailing whitespace.
     *
     * @throws MessageFormatException - if the message could not be parsed
     */
    public static MoveMessage fromString(String message) throws MessageFormatException {
        try {
            return PawnMoveMessage.fromString(message);
        } catch (MessageFormatException e) {
            return WallMoveMessage.fromString(message);
        }
    }
}
