package message;

import static org.junit.Assert.*;
import org.junit.Test;

public class MoveMessageTest {

    @Test
    public void testPawnMessageFromString() throws Throwable {
        MoveMessage msg = MoveMessage.fromString("TESUJI (1, 2)");
        assertTrue("Wrong message type", msg instanceof PawnMoveMessage);
    }

    @Test
    public void testWallMessageFromString() throws Throwable {
        MoveMessage msg = MoveMessage.fromString("TESUJI [(1, 2), v]");
        assertTrue("Wrong message type", msg instanceof WallMoveMessage);
    }

    @Test(expected = MessageFormatException.class)
    public void testFromStringFail() throws Throwable{
        MoveMessage msg = MoveMessage.fromString("TESUJI ()");
    }
}
