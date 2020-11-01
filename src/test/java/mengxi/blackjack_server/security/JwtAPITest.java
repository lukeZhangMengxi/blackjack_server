package mengxi.blackjack_server.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.concurrent.TimeUnit;

import com.auth0.jwt.exceptions.TokenExpiredException;

import org.junit.Test;

public class JwtAPITest {
    
    @Test
    public void generateAndVerifyTokenTest() {
        String token = JwtAPI.generateToken("myEmail", 30);
        assertTrue(JwtAPI.verifyToken(token, "myEmail"));
        assertFalse(JwtAPI.verifyToken(token, "otherEmail"));
    }

    @Test(expected = TokenExpiredException.class)
    public void tokenExpiredTest() throws InterruptedException {
        String token = JwtAPI.generateToken("myEmail", 0);
        TimeUnit.SECONDS.sleep(2);
        JwtAPI.verifyToken(token, "myEmail");
    }

    @Test
    public void tokenLeewayTest() throws InterruptedException {
        String token = JwtAPI.generateToken("myEmail", 0);
        TimeUnit.SECONDS.sleep(1);

        // No expired exception is throwned here
        JwtAPI.verifyToken(token, "myEmail");
    }
}
