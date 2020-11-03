package mengxi.blackjack_server.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import mengxi.blackjack_server.security.JwtAPI.ClaimType;

public class JwtAPITest {

    @Test
    public void generateAndVerifyTokenTest() {
        String token = JwtAPI.generateToken("myEmail", "player-id", 30);
        assertTrue(JwtAPI.verifyToken(token, "myEmail", ClaimType.EMAIL));
        assertTrue(JwtAPI.verifyToken(token, "player-id", ClaimType.PLAYERID));
        assertFalse(JwtAPI.verifyToken(token, "otherEmail", ClaimType.EMAIL));
    }

    @Test
    public void tokenExpiredTest() throws InterruptedException {
        String token = JwtAPI.generateToken("myEmail", "", 0);
        TimeUnit.SECONDS.sleep(2);
        assertFalse(JwtAPI.verifyToken(token, "myEmail", ClaimType.EMAIL));
    }

    @Test
    public void tokenLeewayTest() throws InterruptedException {
        String token = JwtAPI.generateToken("myEmail", "", 0);
        TimeUnit.SECONDS.sleep(1);

        // No expired exception is thrown here
        assertTrue(JwtAPI.verifyToken(token, "myEmail", ClaimType.EMAIL));
    }
}
