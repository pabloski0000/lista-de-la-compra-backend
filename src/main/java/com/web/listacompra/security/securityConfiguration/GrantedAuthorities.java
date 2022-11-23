package com.web.listacompra.security.securityConfiguration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class GrantedAuthorities {
    public static final GrantedAuthority ADMIN = new SimpleGrantedAuthority("ADMIN");
}
