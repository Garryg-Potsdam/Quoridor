package message;

import static org.junit.Assert.*;
import org.junit.Test;

public class RequestMoveMessageTest {

    @Test
    public void testConstructor() {
        new RequestMoveMessage();
    }

    @Test
    public void testToString() {
        RequestMoveMessage msg = new RequestMoveMessage();
        assertEquals("Wrong message format", "MYOUSHU", msg.toString());
    }

    @Test
    public void testFromString() throws Throwable {
        RequestMoveMessage msg = RequestMoveMessage.fromString(" mYoUsHu ");
    }

    @Test(expected = MessageFormatException.class)
    public void testFromStringFailure() throws Throwable {
        RequestMoveMessage.fromString("MY0U5HU");
    }
}
