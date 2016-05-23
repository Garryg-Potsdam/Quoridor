package message;

/**
 * Represents a HELLO message as defined in the protocol document, and handles
 * converting to and from the network format.
 *
 * The format of this message is:
 * HELLO
 *
 * For more details, see:
 * http://cs.potsdam.edu/Classes/405/assignments/project/protocol.txt
 */
public class HelloMessage extends Message {

    public static final String PATTERN = "HELLO";

    /**
     * @see Message#toString()
     */
    public String toString() {
        return "HELLO";
    }

    /**
     * @see Message#type()
     */
    public Type type() {
        return Type.HELLO;
    }

    /**
     * Parses message according to the network protocol and returns a new
     * HelloMessage.
     *
     * Case is ignored, as is leading and trailing whitespace.
     *
     * @throws MessageFormatException - if the message could not be parsed
     */
    public static HelloMessage fromString(String message) throws MessageFormatException {
        parse(PATTERN, message);
        return new HelloMessage();
    }
}
