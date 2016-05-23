package message;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BootMessageTest {

    @Test
    public void testConstructor() {
        BootMessage msg = new BootMessage(2);
        assertEquals("Wrong player number", 2, msg.playerNumber);
    }

    @Test
    public void testToString() {
        BootMessage msg = new BootMessage(2);
        assertEquals("Wrong message format", "GOTE 2", msg.toString());
    }

    @Test
    public void testFromString() throws Throwable {
        BootMessage msg = BootMessage.fromString(" Gote \t 2  ");
        assertEquals("Wrong player number", 2, msg.playerNumber);
    }

    @Test(expected = MessageFormatException.class)
    public void testFromStringFailure() throws Throwable {
        BootMessage.fromString("GOTE two");
    }
}
