package org.tbulens.sso.filter

import org.tbulens.sso.common.util.UrlBuilder

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletResponse


class SsoLogoutFilter implements Filter {
    static final String LOGOUT = "/logout"
    static final String LOGIN = "/secure/securePage1"
    String ssoLogoutUrlPrefix

    //todo: currently does not support original request params. Need Authenticated response for orignalSeviceUrl with params
    String ssoLoginUrlPrefix

    void init(FilterConfig filterConfig) throws ServletException {

    }

    void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse
        httpResponse.sendRedirect(buildCompleteLogoutSsoUrl())
    }

    void destroy() {

    }

    private String buildCompleteLogoutSsoUrl() {
        String encodedLoginUrl = URLEncoder.encode(ssoLoginUrlPrefix + LOGIN, "UTF-16")
        ssoLogoutUrlPrefix + LOGOUT + "?loginUrl=" + encodedLoginUrl
    }
}
