package message;

/**
 * Represents an IAM message as defined in the protocol document, and handles
 * converting to and from the network format.
 *
 * The format of this message is:
 * IAM team:name
 *
 * For more details, see:
 * http://cs.potsdam.edu/Classes/405/assignments/project/protocol.txt
 */
public class IAmMessage extends Message {

    public static final String PATTERN = "IAM\\s+(\\S{3}):(\\S+)";

    /**
     * The unique team name of the server that sent this message.
     */
    public final String team;
    /**
     * The display name of the server that sent this message.
     */
    public final String name;

    /**
     * @param team - unique team name for the server
     * @param name - display name for the server
     */
    public IAmMessage(String team, String name) {
        this.team = team;
        this.name = name;
    }

    /**
     * @see Message#toString()
     */
    public String toString() {
        return String.format("IAM %s:%s", team, name);
    }

    /**
     * @see Message#type()
     */
    public Type type() {
        return Type.I_AM;
    }

    /**
     * Parses message according to the network protocol and returns a new
     * IAmMessage with the values found in message.
     *
     * Case is ignored, as is leading and trailing whitespace.
     *
     * @throws MessageFormatException - if the message could not be parsed
     */
    public static IAmMessage fromString(String message) throws MessageFormatException {
        String[] groups = parse(PATTERN, message);
        return new IAmMessage(groups[1], groups[2]);
    }
}
