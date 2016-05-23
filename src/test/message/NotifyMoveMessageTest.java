package message;

import static org.junit.Assert.*;
import org.junit.Test;

public class NotifyMoveMessageTest {

    @Test
    public void testPawnMessageFromString() throws Throwable {
        NotifyMoveMessage msg = NotifyMoveMessage.fromString("ATARI 2 (1, 2)");
        assertTrue("Wrong message type", msg instanceof NotifyPawnMoveMessage);
    }

    @Test
    public void testWallMessageFromString() throws Throwable {
        NotifyMoveMessage msg = NotifyMoveMessage.fromString("ATARI 2 [(1, 2), v]");
        assertTrue("Wrong message type", msg instanceof NotifyWallMoveMessage);
    }

    @Test(expected = MessageFormatException.class)
    public void testFromStringFail() throws Throwable{
        NotifyMoveMessage msg = NotifyMoveMessage.fromString("ATARI 1 []");
    }
}
