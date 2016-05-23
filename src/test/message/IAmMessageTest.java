package message;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class IAmMessageTest {

    @Test
    public void testConstructor() {
        IAmMessage msg = new IAmMessage("THM", "teamteamhivemind");
        assertEquals("Wrong team name", "THM", msg.team);
        assertEquals("Wrong display name", "teamteamhivemind", msg.name);
    }

    @Test
    public void testToString() {
        IAmMessage msg = new IAmMessage("THM", "teamteamhivemind");
        assertEquals("Wrong message format", "IAM THM:teamteamhivemind", msg.toString());
    }

    @Test
    public void testFromString() throws Throwable {
        IAmMessage msg = IAmMessage.fromString(" iAm \t nOt:5p@rtacus  ");
        assertEquals("Wrong display name", "5p@rtacus", msg.name);
    }

    @Test(expected = MessageFormatException.class)
    public void testFromStringFailure() throws Throwable {
        IAmMessage.fromString("IAM a:red herring");
    }
}
