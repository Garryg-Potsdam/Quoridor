package message;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Represents a GAME message as defined in the protocol document.
 *
 * The format of this message is:
 * GAME pNumber name1 name2
 * or
 * GAME pNumber name1 name2 name3 name4
 *
 * For more details, see:
 * http://cs.potsdam.edu/Classes/405/assignments/project/protocol.txt
 */
public class GameMessage extends Message {

    public static final String PATTERN = "GAME\\s+(\\d+)"
                                       + "\\s+(\\S{3}:\\S+)\\s+(\\S{3}:\\S+)"
                                       + "(?:\\s+(\\S{3}:\\S+)\\s+(\\S{3}:\\S+))?";

    /**
     * The player number of the server this message is for.
     */
    public final int playerNumber;

    public final String[] names;

    public GameMessage(int pNumber, String[] names) {
        playerNumber = pNumber;
        this.names = names;
    }

    /**
     * @see Message#type()
     */
    public Type type() {
        return Type.GAME;
    }

    /**
     * @return int - amount of players in game
     */
    public int playerCount() {
        return names.length;
    }

    public String toString() {
        return String.format("GAME %d %s", playerNumber, String.join(" ", names));
    }

    /**
     * Parses message and return a new GameMessage
     *
     * Case is ignored, as is leading and trailing whitespace.
     *
     * @throws MessageFormatException - if the message could not be parsed
     */
    public static GameMessage fromString(String message) throws MessageFormatException {
        String[] groups = parse(PATTERN, message);
        int pNumber = Integer.parseInt(groups[1]);
        // for a 2-player game, groups 4 and 5 are null
        if (groups[4] == null) {
            return new GameMessage(pNumber, Arrays.copyOfRange(groups, 2, 4));
        } else {
            return new GameMessage(pNumber, Arrays.copyOfRange(groups, 2, 6));
        }
    }
}
