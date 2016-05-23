package network;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ConnectionTest {

    private StringReader input;
    private StringWriter output;
    private Connection conn;

    @Before
    public void setUp() throws Throwable {
        // input simulates messages sent from the client to the conn
        // messages must end with an EOL
        input = new StringReader(String.format("GAME START%n"));
        // output captures messages sent from the conn to the client
        // messages will end with an EOL
        output = new StringWriter();

        conn = new Connection(input, output);
    }

    @Test
    public void testSend() {
        conn.send("PLAYER 1");
        assertEquals("Wrong message", String.format("PLAYER 1%n"), output.toString());
    }

    @Test
    public void testReceive() {
        assertEquals("Wrong message", "GAME START", conn.receive());
        assertEquals("Wrong message", null, conn.receive());
    }
}
