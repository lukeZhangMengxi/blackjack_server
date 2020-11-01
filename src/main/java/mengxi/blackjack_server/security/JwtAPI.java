package mengxi.blackjack_server.security;

import java.sql.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtAPI {
    private static String ISSUER = "Mengxi";
    private static String SECRET = "random-string";
    private static Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    private static JWTVerifier verifier = JWT.require(ALGORITHM).withIssuer(ISSUER).acceptLeeway(1).build();

    public static String generateToken(String email, int minutes) {
        return JWT
            .create()
            .withIssuer(ISSUER)
            .withClaim("email", email)
            .withExpiresAt(new Date(System.currentTimeMillis() + minutes * 60 * 1000))
            .sign(ALGORITHM);
    }

    public static boolean verifyToken(String token, String email) {
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString().equals(email);
    }
}
