package message;

import static org.junit.Assert.*;
import org.junit.Test;

public class HelloMessageTest {

    @Test
    public void testConstructor() {
        new HelloMessage();
    }

    @Test
    public void testToString() {
        HelloMessage msg = new HelloMessage();
        assertEquals("Wrong message format", "HELLO", msg.toString());
    }

    @Test
    public void testFromString() throws Throwable {
        HelloMessage msg = HelloMessage.fromString(" Hello ");
    }

    @Test(expected = MessageFormatException.class)
    public void testFromStringFailure() throws Throwable {
        HelloMessage.fromString("HELLO WORLD");
    }
}
