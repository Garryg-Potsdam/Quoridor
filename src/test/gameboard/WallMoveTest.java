package gameboard;

import org.junit.Test;
import static org.junit.Assert.*;

public class WallMoveTest {

    @Test
    public void testEquals() {
        WallMove m1 = new WallMove(new Wall(new Coordinate(3, 5), Wall.Direction.HORIZONTAL));
        WallMove m2 = new WallMove(new Wall(new Coordinate(3, 5), Wall.Direction.HORIZONTAL));
        assertTrue(m1.equals(m2));
    }

    @Test
    public void testNotEquals() {
        WallMove m1 = new WallMove(new Wall(new Coordinate(3, 5), Wall.Direction.HORIZONTAL));
        WallMove m2 = new WallMove(new Wall(new Coordinate(4, 6), Wall.Direction.HORIZONTAL));
        assertFalse(m1.equals(m2));
    }
}
