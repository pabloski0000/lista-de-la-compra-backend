package com.web.listacompra.security.securityConfiguration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.web.listacompra.security.filter.CustomAuthorizationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests()
        .antMatchers("/api/users/register-admin", "/api/users/register-possible-subscriber", "/api/users/register-user")
        .permitAll()
        .antMatchers("/api/users/notify-of-new-subscribers")
        .hasRole("ADMIN")
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
