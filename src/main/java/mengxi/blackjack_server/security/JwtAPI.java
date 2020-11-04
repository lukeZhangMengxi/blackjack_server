package mengxi.blackjack_server.security;

import java.sql.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtAPI {

    public enum ClaimType {
        EMAIL, PLAYERID
    }

    private static String ISSUER = "Mengxi";
    private static String SECRET = "random-string";
    private static Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    private static JWTVerifier verifier = JWT.require(ALGORITHM).withIssuer(ISSUER).acceptLeeway(1).build();

    public static String generateToken(String email, String playerId, int minutes) {
        return JWT.create().withIssuer(ISSUER).withClaim(ClaimType.EMAIL.toString(), email)
                .withClaim(ClaimType.PLAYERID.toString(), playerId)
                .withExpiresAt(new Date(System.currentTimeMillis() + minutes * 60 * 1000)).sign(ALGORITHM);
    }

    public static boolean verifyToken(String token, String email, ClaimType type) {
        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim(type.toString()).asString().equals(email);
        } catch (TokenExpiredException e) {
            // Log it
            return false;
        } catch (Exception e) {
            return false;
        }

    }
}
