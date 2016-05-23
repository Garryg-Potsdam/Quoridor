package gameboard;

import org.junit.Test;
import static org.junit.Assert.*;

public class WallTest {

    @Test
    public void testEquals() {
        Wall w1 = new Wall(new Coordinate(2, 1), Wall.Direction.HORIZONTAL);
        Wall w2 = new Wall(new Coordinate(2, 1), Wall.Direction.HORIZONTAL);
        assertTrue(w1.equals(w2));
    }

    @Test
    public void testPositionNotEquals() {
        Wall w1 = new Wall(new Coordinate(2, 1), Wall.Direction.HORIZONTAL);
        Wall w2 = new Wall(new Coordinate(2, 4), Wall.Direction.HORIZONTAL);
        assertFalse(w1.equals(w2));
    }

    @Test
    public void testDirectionNotEquals() {
        Wall w1 = new Wall(new Coordinate(2, 1), Wall.Direction.HORIZONTAL);
        Wall w2 = new Wall(new Coordinate(2, 1), Wall.Direction.VERTICAL);
        assertFalse(w1.equals(w2));
    }
}
