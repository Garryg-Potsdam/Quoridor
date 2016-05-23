package message;

import static org.junit.Assert.*;
import org.junit.Test;

public class GameMessageTest {

    @Test
    public void testConstructor() {
        GameMessage msg = new GameMessage(1, new String[] {"abc:team1", "de3:team2"});
        assertEquals("Wrong player number", 1, msg.playerNumber);
        assertEquals("Wrong player count", 2, msg.playerCount());
        assertArrayEquals("Wrong names", new String[] {"abc:team1", "de3:team2"}, msg.names);
    }

    @Test
    public void testToString() {
        GameMessage msg = new GameMessage(1, new String[] {"abc:team1", "de3:team2"});
        assertEquals("Wrong message format", "GAME 1 abc:team1 de3:team2", msg.toString());
    }

    @Test
    public void testFromString() throws Throwable {
        GameMessage msg = GameMessage.fromString("  gAme  1 \t Abc:!! de3:^&*%  ");
        assertEquals("Wrong player number", 1, msg.playerNumber);
        assertEquals("Wrong player count", 2, msg.playerCount());
        assertArrayEquals("Wrong names", new String[] {"Abc:!!", "de3:^&*%"}, msg.names);
    }

    @Test(expected = MessageFormatException.class)
    public void testFromStringFailure() throws Throwable {
        GameMessage.fromString("GAME 1 team1 team2");
    }
}
