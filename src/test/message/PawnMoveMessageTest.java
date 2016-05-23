package message;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import gameboard.Coordinate;
import gameboard.PawnMove;

public class PawnMoveMessageTest {

    @Test
    public void testConstructor() {
        PawnMoveMessage msg = new PawnMoveMessage(new PawnMove(new Coordinate(7, 3)));
        PawnMove move = new PawnMove(new Coordinate(7, 3));
        assertEquals("Wrong move", move, msg.move);
    }

    @Test
    public void testToString() {
        PawnMoveMessage msg = new PawnMoveMessage(new PawnMove(new Coordinate(7, 3)));
        assertEquals("Wrong message format", "TESUJI (3, 7)", msg.toString());
    }

    @Test
    public void testFromString() throws Throwable {
        PawnMoveMessage msg = PawnMoveMessage.fromString(" teSUji \t ( 3  , 7) ");
        PawnMove move = new PawnMove(new Coordinate(7, 3));
        assertEquals("Wrong move", move, msg.move);
    }

    @Test(expected = MessageFormatException.class)
    public void testFromStringFailure() throws Throwable {
        PawnMoveMessage.fromString("TESUJI (3 7)");
    }
}
