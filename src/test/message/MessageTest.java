package message;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MessageTest {

    @Test
    public void helloFromString() throws Throwable {
        Message msg = Message.fromString("HELLO");
        assertEquals(msg.type(), Message.Type.HELLO);
    }

    @Test
    public void iamFromString() throws Throwable {
        Message msg = Message.fromString("IAM Cpt:Ishmael");
        assertEquals(msg.type(), Message.Type.I_AM);
    }

    @Test
    public void gameFromString() throws Throwable {
        Message msg = Message.fromString("GAME 2 ONE:team1 TWO:team2");
        assertEquals(msg.type(), Message.Type.GAME);
    }

    @Test
    public void requestMoveFromString() throws Throwable {
        Message msg = Message.fromString("MYOUSHU");
        assertEquals(msg.type(), Message.Type.REQUEST_MOVE);
    }

    @Test
    public void moveFromString() throws Throwable {
        Message msg = Message.fromString("TESUJI (1, 2)");
        assertEquals(msg.type(), Message.Type.MOVE);
    }

    @Test
    public void notifyMoveFromString() throws Throwable {
        Message msg = Message.fromString("ATARI 1 (1, 2)");
        assertEquals(msg.type(), Message.Type.NOTIFY_MOVE);
    }

    @Test
    public void bootFromString() throws Throwable {
        Message msg = Message.fromString("GOTE 1");
        assertEquals(msg.type(), Message.Type.BOOT);
    }

    @Test
    public void victoryFromString() throws Throwable {
        Message msg = Message.fromString("KIKASHI 1");
        assertEquals(msg.type(), Message.Type.VICTORY);
    }

    @Test(expected = MessageFormatException.class)
    public void fromStringFail() throws Throwable {
        Message.fromString("KONNICHIWA");
    }
}
