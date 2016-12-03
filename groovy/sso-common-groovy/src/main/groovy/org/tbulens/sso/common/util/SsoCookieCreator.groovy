package org.tbulens.sso.common.util

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

class SsoCookieCreator {


    static final String SSO_SESSION_ID = "ssoSessionId"

    void create(HttpServletResponse response, String secureCookieId, String domain) {
        Cookie cookie = new Cookie(SSO_SESSION_ID, secureCookieId)
        cookie.setHttpOnly(true)
        cookie.setSecure(true)
        cookie.setDomain(domain)
        response.addCookie(cookie)
    }
}
