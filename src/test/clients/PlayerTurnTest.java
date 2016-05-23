package clients;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PlayerTurnTest {

    @Test
    public void testConstructor() {
        PlayerTurn turns = new PlayerTurn(1, 4, 2, 3);
    }

    @Test
    public void testNext() {
        PlayerTurn turns = new PlayerTurn(1, 4, 2, 3);

        int [] expected = {1, 4, 2, 3};
        for (int i = 0; i < expected.length; i++) {
            assertEquals("Wrong player number", expected[i], turns.next());
        }
        // make sure it wraps around properly
        for (int i = 0; i < expected.length; i++) {
            assertEquals("Wrong player number", expected[i], turns.next());
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testNextThrowsExceptionWithNoPlayers() {
        PlayerTurn turns = new PlayerTurn(1);
        turns.bootPlayer(1);
        turns.next();
    }

    @Test
    public void testBootPlayer() throws Throwable {
        PlayerTurn turns = new PlayerTurn(1, 4, 2, 3);
        turns.bootPlayer(1);
        turns.bootPlayer(2);
        assertEquals("Wrong player number", 4, turns.next());
        assertEquals("Wrong player number", 3, turns.next());

    }

    @Test
    public void testPlayerCount() {
        PlayerTurn turns = new PlayerTurn(1, 2);
        assertEquals("Wrong player count", 2, turns.playerCount());
        turns.bootPlayer(1);
        turns.bootPlayer(2);
        assertEquals("Wrong player count", 0, turns.playerCount());
    }
}
