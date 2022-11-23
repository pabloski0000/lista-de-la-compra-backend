package com.web.listacompra.security.securityConfiguration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.web.listacompra.controller.userController.UserControllerUrlPaths;
import com.web.listacompra.security.filter.CustomAuthorizationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests()
        .antMatchers(
            UserControllerUrlPaths.USER_ADMIN_REGISTRATION_SERVICE,
            UserControllerUrlPaths.USER_REGISTRATION_SERVICE,
            UserControllerUrlPaths.REGISTRATION_CONFIRMATION_SERVICE
            )
        .permitAll()
        .antMatchers("/api/users/notify-of-new-subscribers")
        .hasRole("ADMIN")
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
