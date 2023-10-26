package org.jeugenedev.simbir.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.jeugenedev.simbir.App;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {
    public static final String KEY_ROLE = "role";
    public static final String KEY_USER_ID = "user_id";
    private static long TOKEN_EXPIRED;

    @Value("${auth.secret}")
    private String secret;
    @Value("${auth.username.param}")
    private String usernameParam;
    @Value("${jwt.tokens.expired.minutes}")
    private int expiredToken;
    private Algorithm algorithm;
    private JWTVerifier verifier;
    private final String issuer = App.class.getPackage().getName();

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(this.algorithm).withIssuer(this.issuer).build();
        TOKEN_EXPIRED = 1000L * 60 * this.expiredToken;
    }

    public String generateToken(long id, String username, String role) {
        return JWT.create()
                .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRED))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim(KEY_USER_ID, id)
                .withClaim(usernameParam, username)
                .withClaim(KEY_ROLE, role)
                .sign(algorithm);
    }

    public String getPayload(String token) throws JWTVerificationException {
        DecodedJWT decodedJWT = verifyToken(token).jwt();
        if (decodedJWT == null) {
            throw new JWTVerificationException(null);
        }

        return decodedJWT.getPayload();
    }

    public AccountToken verifyToken(String token) {
        try {
            DecodedJWT decodedJWT = this.verifier.verify(token);
            return new AccountToken(decodedJWT, true);
        } catch (JWTVerificationException e) {
            return new AccountToken(null, false);
        }
    }

    public record AccountToken(DecodedJWT jwt, boolean verified) {}

    public static long getTokenExpired() {
        return TOKEN_EXPIRED;
    }
}
