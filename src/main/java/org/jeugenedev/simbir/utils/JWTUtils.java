package org.jeugenedev.simbir.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {
    public static final String KEY_ROLE = "user";

    @Value("${auth.secret}")
    private String secret;
    @Value("${auth.username.param}")
    private String usernameParam;
    @Value("${auth.password.param}")
    private String passwordParam;
    private Algorithm algorithm;
    private JWTVerifier verifier;
    private final String issuer = getClass().getPackage().getName();
    private final int MINUTES_15_MS = 1000 * 60 * 15;

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(this.algorithm).withIssuer(this.issuer).build();
    }

    public String generateToken(String username, String password, String role) {
        return JWT.create()
                .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + MINUTES_15_MS))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim(usernameParam, username)
                .withClaim(passwordParam, password)
                .withClaim(KEY_ROLE, role)
                .sign(algorithm);
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
}
