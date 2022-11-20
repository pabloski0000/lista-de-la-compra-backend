package com.web.listacompra.utils;

import java.util.Date;
import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTGenerator {
    public static String generateStandardJWTWithoutExpiration(String userNickName, List<String> roles, String secretKey){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
            .withSubject(userNickName)
            .withClaim("roles", roles)
            .sign(algorithm);
    }
    public static String generateStandardJWT(String userNickName, List<String> roles, String secretKey, Date expiresAt){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
            .withSubject(userNickName)
            .withClaim("roles", roles)
            .withExpiresAt(expiresAt)
            .sign(algorithm);
    }
}
