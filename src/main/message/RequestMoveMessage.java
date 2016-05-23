package message;

/**
 * Represents a MYOUSHU message as defined in the protocol document, and handles
 * converting to and from the network format.
 *
 * The format of this message is:
 * MYOUSHU
 *
 * For more details, see:
 * http://cs.potsdam.edu/Classes/405/assignments/project/protocol.txt
 */
public class RequestMoveMessage extends Message {

    public static final String PATTERN = "MYOUSHU";

    /**
     * @see Message.toString()
     */
    public String toString() {
        return "MYOUSHU";
    }

    /**
     * @see Message#type()
     */
    public Type type() {
        return Type.REQUEST_MOVE;
    }

    /**
     * Parses message according to the network protocol and returns a new
     * RequestMoveMessage.
     *
     * Case is ignored, as is leading and trailing whitespace.
     *
     * @throws MessageFormatException - if the message could not be parsed
     */
    public static RequestMoveMessage fromString(String message) throws MessageFormatException {
        parse(PATTERN, message);
        return new RequestMoveMessage();
    }
}
