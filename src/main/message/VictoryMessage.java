package message;

import static java.lang.Integer.parseInt;

/**
 * Represents an KIKASHI message as defined in the protocol document, and handles
 * converting to and from the network format.
 *
 * The format of this message is:
 * KIKASHI name
 *
 * For more details, see:
 * http://cs.potsdam.edu/Classes/405/assignments/project/protocol.txt
 */
public class VictoryMessage extends Message {

    public static final String PATTERN = "KIKASHI\\s+(1|2|3|4)";

    /**
     * The player of the server that won the game.
     */
    public final int playerNumber;

    /**
     * @param pNumber - player number of the winning server
     */
    public VictoryMessage(int pNumber) {
        playerNumber = pNumber;
    }

    /**
     * Returns a string representation of this message in the network format.
     */
    public String toString() {
        return "KIKASHI " + playerNumber;
    }

    /**
     * @see Message#type()
     */
    public Type type() {
        return Type.VICTORY;
    }

    /**
     * Parses message according to the network protocol and returns a new
     * VictoryMessage with the values found in message.
     *
     * Case is ignored, as is leading and trailing whitespace.
     *
     * @throws MessageFormatException - if the message could not be parsed
     */
    public static VictoryMessage fromString(String message) throws MessageFormatException {
        String[] groups = parse(PATTERN, message);
        return new VictoryMessage(parseInt(groups[1]));
    }
}
