package org.tbulens.sso.filter

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class SsoFilter implements Filter {
    static final String SSO_SESSION_ID = "ssoSessionId"
    static final String AUTHENTICATE = "/authenticate"
    static final String LOGIN = "/login"
    String ssoUrlPrefix

    void init(FilterConfig filterConfig) throws ServletException {

    }

    // be sure only secure urls get into this filter by specifying filter pattern.
    void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request =  servletRequest as HttpServletRequest
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.sendRedirect(buildCompleteSsoUrl(request));
    }

    private String buildCompleteSsoUrl(HttpServletRequest request) {
        String encodedServiceUrl = buildEncodedServiceUrl(request)
        String ssoUrl = buildSsoUrl(request.cookies)
        ssoUrl + "?service=" + encodedServiceUrl
    }

    private String buildSsoUrl(Cookie[] cookies) {
        Cookie ssoCookie = cookies.find { it.name == SSO_SESSION_ID }
        ssoCookie ? ssoUrlPrefix + AUTHENTICATE : ssoUrlPrefix + LOGIN
    }

    private String buildEncodedServiceUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        URLEncoder.encode(url + "?" + queryString, "UTF-16")
    }

    void destroy() {

    }
}
