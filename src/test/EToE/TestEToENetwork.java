/*import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EToENetworkTest {

    private Client c;

    @Before
    public void setUp() throws Throwable {
        // start servers on ports 11111, 11112, 11113, 11114
        for (int i = 0; i < 4; i++) {
            int port = 11111 + i;
            (new Thread(() ->
                (new Server()).run(port)
            )).start();
        }
        c = new Client();
        c.addServer("localhost", 11111);
        c.addServer("localhost", 11112);
        c.addServer("localhost", 11113);
        c.addServer("localhost", 11114);
    }

    // Teardown Client after each test
    @After
    public void TearDown() {
        c = null;
    }

    // Testing that all the servers check in
    @Test
    public void testClientCanConnectToTwoServers() throws Throwable {
        String response = c.run();
        assertEquals("Wrong message returned",
                "Server Check: 11111 OK;"
                + "Server Check: 11112 OK;"
                + "Server Check: 11113 OK;"
                + "Server Check: 11114 OK;",
                response);
    }

    // Testing that we have a valid server count
    @Test
    public void testServersStartedByCount() {
        c.run();
        int serverCount = 4;
        assertEquals("Checking Server Count", serverCount, c.getCount());
    }
}*/
