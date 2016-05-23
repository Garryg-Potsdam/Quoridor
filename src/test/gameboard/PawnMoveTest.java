package gameboard;

import org.junit.Test;
import static org.junit.Assert.*;

public class PawnMoveTest {

    @Test
    public void testEquals() {
        PawnMove m1 = new PawnMove(new Coordinate(3, 5));
        PawnMove m2 = new PawnMove(new Coordinate(3, 5));
        assertTrue(m1.equals(m2));
    }

    @Test
    public void testNotEquals() {
        PawnMove m1 = new PawnMove(new Coordinate(3, 5));
        PawnMove m2 = new PawnMove(new Coordinate(4, 6));
        assertFalse(m1.equals(m2));
    }
}
