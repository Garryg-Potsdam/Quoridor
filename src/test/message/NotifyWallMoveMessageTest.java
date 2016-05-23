package message;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import gameboard.Coordinate;
import gameboard.Wall;
import gameboard.WallMove;

public class NotifyWallMoveMessageTest {

    @Test
    public void testConstructor() {
        NotifyWallMoveMessage msg = new NotifyWallMoveMessage(2, new WallMove(new Wall(new Coordinate(7, 3), Wall.Direction.VERTICAL)));
        assertEquals("Wrong player number", 2, msg.playerNumber);
        WallMove move = new WallMove(new Wall(new Coordinate(7, 3), Wall.Direction.VERTICAL));
        assertEquals("Wrong move", move, msg.move);
    }

    @Test
    public void testToString() {
        NotifyWallMoveMessage msg = new NotifyWallMoveMessage(2, new WallMove(new Wall(new Coordinate(7, 3), Wall.Direction.VERTICAL)));
        assertEquals("Wrong message format", "ATARI 2 [(3, 7), v]", msg.toString());
    }

    @Test
    public void testFromString() throws Throwable {
        NotifyWallMoveMessage msg = NotifyWallMoveMessage.fromString(" AtarI  2 \t [( 3  , 7), V ] ");
        assertEquals("Wrong player number", 2, msg.playerNumber);
        WallMove move = new WallMove(new Wall(new Coordinate(7, 3), Wall.Direction.VERTICAL));
        assertEquals("Wrong move", move, msg.move);
    }

    @Test(expected = MessageFormatException.class)
    public void testFromStringFailure() throws Throwable {
        NotifyWallMoveMessage.fromString("ATARI 2 [(3, 7) h]");
    }
}
