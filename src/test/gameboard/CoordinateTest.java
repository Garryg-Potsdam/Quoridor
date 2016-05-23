package gameboard;

import org.junit.Test;
import static org.junit.Assert.*;

public class CoordinateTest {

    @Test
    public void testEquals() {
        Coordinate c1 = new Coordinate(2, 1);
        Coordinate c2 = new Coordinate(2, 1);
        assertTrue(c1.equals(c2));
    }

    @Test
    public void testNotEquals() {
        Coordinate c1 = new Coordinate(2, 1);
        Coordinate c2 = new Coordinate(2, 4);
        assertFalse(c1.equals(c2));
    }

    @Test
    public void testHashCode() {
        Coordinate c = new Coordinate(2, 1);
        assertEquals(0b0110, c.hashCode());
    }

    @Test
    public void testUp() {
        Coordinate c1 = new Coordinate(2, 1);
        Coordinate c2 = new Coordinate(1, 1);
        assertEquals(c2, c1.up());
    }

    @Test
    public void testRight() {
        Coordinate c1 = new Coordinate(2, 1);
        Coordinate c2 = new Coordinate(2, 2);
        assertEquals(c2, c1.right());
    }

    @Test
    public void testDown() {
        Coordinate c1 = new Coordinate(2, 1);
        Coordinate c2 = new Coordinate(3, 1);
        assertEquals(c2, c1.down());
    }

    @Test
    public void testLeft() {
        Coordinate c1 = new Coordinate(2, 1);
        Coordinate c2 = new Coordinate(2, 0);
        assertEquals(c2, c1.left());
    }
}
