package message;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import gameboard.Coordinate;
import gameboard.Wall;
import gameboard.WallMove;

public class WallMoveMessageTest {

    @Test
    public void testConstructor() {
        WallMoveMessage msg = new WallMoveMessage(new WallMove(new Wall(new Coordinate(7, 3), Wall.Direction.HORIZONTAL)));
        WallMove move = new WallMove(new Wall(new Coordinate(7, 3), Wall.Direction.HORIZONTAL));
        assertEquals("Wrong move", move, msg.move);
    }

    @Test
    public void testToString() {
        WallMoveMessage msg = new WallMoveMessage(new WallMove(new Wall(new Coordinate(7, 3), Wall.Direction.HORIZONTAL)));
        assertEquals("Wrong message format", "TESUJI [(3, 7), h]", msg.toString());
    }

    @Test
    public void testFromString() throws Throwable {
        WallMoveMessage msg = WallMoveMessage.fromString(" teSUji \t [( 3  , 7), H] ");
        WallMove move = new WallMove(new Wall(new Coordinate(7, 3), Wall.Direction.HORIZONTAL));
        assertEquals("Wrong move", move, msg.move);
    }

    @Test(expected = MessageFormatException.class)
    public void testFromStringFailure() throws Throwable {
        WallMoveMessage.fromString("TESUJI [(3, 7) h]");
    }
}
