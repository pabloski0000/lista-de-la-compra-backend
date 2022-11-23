package com.web.listacompra.utils;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTGenerator {
    public static final String TYPE_OF_TOKEN_PROPERTY_HEADER = "typ";
    public static final String TYPE_OF_ENCRIPTAION_ALGORITHM_PROPERTY_HEADER = "alg";
    public static final String SUBJECT_PROPERTY = "sub";
    public static final String AUTHORITIES_PROPERTY = "authorities";
    public static final String EXPIRATION_PROPERTY = "exp";
    public static String generateStandardJWT(String userNickName, List<GrantedAuthority> authorities, String secretKey, Date expiresAt){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
            .withSubject(userNickName)
            .withArrayClaim(AUTHORITIES_PROPERTY, parseGrantedAuthorityListToJWTArray(authorities))
            .withExpiresAt(expiresAt)
            .sign(algorithm);
    }
    private static String[] parseGrantedAuthorityListToJWTArray(List<GrantedAuthority> grantedAuthorities){
        String[] jwtArray = new String[grantedAuthorities.size()];
        for (int i = 0; i < jwtArray.length; i++) {
            jwtArray[i] = grantedAuthorities.get(i).getAuthority();
        }
        return jwtArray;
    }
}
