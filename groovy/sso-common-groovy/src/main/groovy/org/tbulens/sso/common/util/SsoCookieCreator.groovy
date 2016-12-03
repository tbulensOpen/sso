package org.tbulens.sso.common.util

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

class SsoCookieCreator {

    void create(HttpServletResponse response, String secureCookieId, String domain) {
        Cookie cookie = new Cookie("ssoCookie", secureCookieId)
        cookie.setHttpOnly(true)
        cookie.setSecure(true)
        cookie.setDomain(domain)
        response.addCookie(cookie)
    }
}
