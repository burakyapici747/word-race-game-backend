package com.wordrace.util.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wordrace.constant.SecurityConstant;
import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class JwtHelper {
    public String generateJwtToken(final String username){
        final Algorithm algorithm = Algorithm.HMAC256(SecurityConstant.SECRET_KEY);

        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() +
                        SecurityConstant.CURRENT_EXPIRED_DAY * 24 * 60 * 60 * 1000))
                .sign(algorithm);
    }

    public DecodedJWT decodeJwtToken(final String jwtToken) throws JWTVerificationException {
        final Algorithm algorithm = Algorithm.HMAC256(SecurityConstant.SECRET_KEY);
        final JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(jwtToken);
    }
}
