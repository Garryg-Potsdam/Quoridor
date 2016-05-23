package message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Message {

    public static enum Type {
        HELLO,
        I_AM,
        GAME,
        REQUEST_MOVE,
        MOVE,
        NOTIFY_MOVE,
        BOOT,
        VICTORY;
    }

    private static Pattern whitespace = Pattern.compile("\\s");

    /**
     * Returns true if str contains any whitespace characters.
     */
    protected static boolean containsWhitespace(String str) {
        return whitespace.matcher(str).find();
    }

    /**
     * Parses message using the given regex and returns an array containing the
     * captured groups.
     *
     * Case is ignored, as is leading and trailing whitespace.
     *
     * The groups returned are taken from Matcher.group() starting with 0.
     *
     * @param regex - the pattern to parse message with
     * @param message - the message to parse
     * @return an array containing the captured groups
     *
     * @throws MessageFormatException - if the message could not be parsed
     * @throws PatternSyntaxException - if the regex is invalid
     */
    protected static String[] parse(String regex, String message) throws MessageFormatException {
        Pattern pat = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher match = pat.matcher(message.trim());

        if (!match.matches()) {
            throw new MessageFormatException(message);
        }

        String[] groups = new String[match.groupCount() + 1];
        for (int i = 0; i < groups.length; i++) {
            groups[i] = match.group(i);
        }

        return groups;
    }

    public static Message fromString(String message) throws MessageFormatException {
        // trim message first to avoid empty substring
        String firstWord = message.trim().split("\\s+", 2)[0];

        switch (firstWord.toUpperCase()) {
            case "HELLO":
                return HelloMessage.fromString(message);
            case "IAM":
                return IAmMessage.fromString(message);
            case "GAME":
                return GameMessage.fromString(message);
            case "MYOUSHU":
                return RequestMoveMessage.fromString(message);
            case "TESUJI":
                return MoveMessage.fromString(message);
            case "ATARI":
                return NotifyMoveMessage.fromString(message);
            case "GOTE":
                return BootMessage.fromString(message);
            case "KIKASHI":
                return VictoryMessage.fromString(message);
            default:
                throw new MessageFormatException(message);
        }
    }

    /**
     * Returns a string representation of this message in the network format.
     */
    public abstract String toString();

    /**
     * Returns the type of this message.
     * @see Message#Type
     */
    public abstract Type type();
}
