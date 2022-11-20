package com.web.listacompra.security.filter;

import java.io.IOException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.web.listacompra.systemConfiguration.ApplicationProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!request.getServletPath().matches("^/api/users/register.*$")){
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(authorization == null){
                //Inform user of the error
                response.sendError(401, "You need to have an authorization field (as key) in your header request");
                throw new ServletException("You need to have an authorization field (as key) in your header request");
            }
            String access_token = authorization.substring("Bearer".concat(" ").length());
            String secretKey = ApplicationProperties.getSingleton().getSecretKey();
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(access_token); //TODO (just as a posibility) error controller
            String username = decodedJWT.getSubject();
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
            for(String role: roles){
                grantedAuthorities.add(new SimpleGrantedAuthority(role));
            }
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
