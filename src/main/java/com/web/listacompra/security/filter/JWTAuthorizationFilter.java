package com.web.listacompra.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.web.listacompra.controller.userController.UserControllerUrlPaths;
import com.web.listacompra.security.securityConfiguration.GrantedAuthorities;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private static final String IT_IS_NOT_ADMIN = "Request does not have ADMIN Authority";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(request.getServletPath().matches(UserControllerUrlPaths.USER_REGISTRATION_ALERTER_SERVICE)){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(!authentication.getAuthorities().contains(GrantedAuthorities.ADMIN)){
                throw new ServletException(IT_IS_NOT_ADMIN);
            }
        }
        filterChain.doFilter(request, response);
    }
    
}
