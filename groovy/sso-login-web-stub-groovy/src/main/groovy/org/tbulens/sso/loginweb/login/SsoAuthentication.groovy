package org.tbulens.sso.loginweb.login

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority


class SsoAuthentication implements Authentication {
    private final Collection<? extends GrantedAuthority> authorities
    private final String userName
    private boolean authenticated

    protected SsoAuthentication(Collection<? extends GrantedAuthority> authorities, String userName, boolean authenticated) {
        this.authorities = authorities
        this.userName = userName
        this.authenticated = authenticated
    }

    Collection<? extends GrantedAuthority> getAuthorities() {
        authorities
    }

    Object getCredentials() {
       null
    }

    Object getDetails() {
        null
    }

    Object getPrincipal() {
       this
    }

    boolean isAuthenticated() {
        authenticated
    }

    void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
       this.authenticated = authenticated
    }

    String getName() {
        userName
    }
}
