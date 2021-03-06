package org.tbulens.sso.common.util


import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

class SsoCookieCreator {


    static final String SSO_SESSION_ID = "ssoSessionId"

    void create(HttpServletResponse response, String ssoJwtTokenJson, String domain, String ssoContextRoot) {
        Cookie cookie = new Cookie(SSO_SESSION_ID, ssoJwtTokenJson)
        cookie.setHttpOnly(true)
        cookie.setSecure(false)
        cookie.setDomain(domain)
        cookie.setMaxAge(-1)
        cookie.setPath(ssoContextRoot)
        response.addCookie(cookie)
    }
}
