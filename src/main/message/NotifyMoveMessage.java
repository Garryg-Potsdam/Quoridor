package message;

import gameboard.Move;

/**
 * Represents a ATARI message as defined in the protocol document.
 *
 * The format of this message is:
 * ATARI pNumber (column, row)
 * or
 * ATARI pNumber [(column, row), direction]
 *
 * For more details, see:
 * http://cs.potsdam.edu/Classes/405/assignments/project/protocol.txt
 */
public abstract class NotifyMoveMessage extends Message {

    /**
     * The player number of the server that made this move.
     */
    public final int playerNumber;

    public Move move;

    protected NotifyMoveMessage(int pNumber, Move move) {
        this.playerNumber = pNumber;
        this.move = move;
    }

    /**
     * @see Message#type()
     */
    public Type type() {
        return Type.NOTIFY_MOVE;
    }

    /**
     * Parses message and return a new NotifyPawnMoveMessage or NotifyWallMoveMessage,
     * depending on the format of the message.
     *
     * Case is ignored, as is leading and trailing whitespace.
     *
     * @throws MessageFormatException - if the message could not be parsed
     */
    public static NotifyMoveMessage fromString(String message) throws MessageFormatException {
        try {
            return NotifyPawnMoveMessage.fromString(message);
        } catch (MessageFormatException e) {
            return NotifyWallMoveMessage.fromString(message);
        }
    }
}
