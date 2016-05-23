package message;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class VictoryMessageTest {

    @Test
    public void testConstructor() {
        VictoryMessage msg = new VictoryMessage(2);
        assertEquals("Wrong player number", 2, msg.playerNumber);
    }

    @Test
    public void testToString() {
        VictoryMessage msg = new VictoryMessage(2);
        assertEquals("Wrong message format", "KIKASHI 2", msg.toString());
    }

    @Test
    public void testFromString() throws Throwable {
        VictoryMessage msg = VictoryMessage.fromString(" kIkashi \t 2  ");
        assertEquals("Wrong player number", 2, msg.playerNumber);
    }

    @Test(expected = MessageFormatException.class)
    public void testFromStringFailure() throws Throwable {
        VictoryMessage.fromString("KIKASHI two");
    }
}
