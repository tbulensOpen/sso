package org.tbulens.sso.loginweb.login

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component


@Component
class CredentialFactory {

    protected void setAuthentication(String userName, Collection<? extends GrantedAuthority> authorities) {
        SsoAuthentication authentication = new SsoAuthentication(authorities, userName, true)
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
