package message;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import gameboard.Coordinate;
import gameboard.PawnMove;

public class NotifyPawnMoveMessageTest {

    @Test
    public void testConstructor() {
        NotifyPawnMoveMessage msg = new NotifyPawnMoveMessage(2, new PawnMove(new Coordinate(7, 3)));
        assertEquals("Wrong player number", 2, msg.playerNumber);
        PawnMove move = new PawnMove(new Coordinate(7, 3));
        assertEquals("Wrong move", move, msg.move);
    }

    @Test
    public void testToString() {
        NotifyPawnMoveMessage msg = new NotifyPawnMoveMessage(2, new PawnMove(new Coordinate(7, 3)));
        assertEquals("Wrong message format", "ATARI 2 (3, 7)", msg.toString());
    }

    @Test
    public void testFromString() throws Throwable {
        NotifyPawnMoveMessage msg = NotifyPawnMoveMessage.fromString(" AtarI  2 \t ( 3  , 7) ");
        assertEquals("Wrong player number", 2, msg.playerNumber);
        PawnMove move = new PawnMove(new Coordinate(7, 3));
        assertEquals("Wrong move", move, msg.move);
    }

    @Test(expected = MessageFormatException.class)
    public void testFromStringFailure() throws Throwable {
        NotifyPawnMoveMessage.fromString("ATARI 2(3, 7)");
    }
}
