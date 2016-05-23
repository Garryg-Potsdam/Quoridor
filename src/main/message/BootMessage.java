package message;

import static java.lang.Integer.parseInt;

/**
 * Represents an GOTE message as defined in the protocol document, and handles
 * converting to and from the network format.
 *
 * The format of this message is:
 * GOTE name
 *
 * For more details, see:
 * http://cs.potsdam.edu/Classes/405/assignments/project/protocol.txt
 */
public class BootMessage extends Message {

    public static final String PATTERN = "GOTE\\s+(1|2|3|4)";

    /**
     * The player number of the server being booted from the game.
     */
    public final int playerNumber;

    /**
     * @param pNumber - player number of the server being booted
     */
    public BootMessage(int pNumber) {
        playerNumber = pNumber;
    }

    /**
     * @see Message#toString()
     */
    public String toString() {
        return "GOTE " + playerNumber;
    }

    /**
     * @see Message#type()
     */
    public Type type() {
        return Type.BOOT;
    }

    /**
     * Parses message according to the network protocol and returns a new
     * BootMessage with the values found in message.
     *
     * Case is ignored, as is leading and trailing whitespace.
     *
     * @throws MessageFormatException - if the message could not be parsed
     */
    public static BootMessage fromString(String message) throws MessageFormatException {
        String[] groups = parse(PATTERN, message);
        return new BootMessage(parseInt(groups[1]));
    }
}
