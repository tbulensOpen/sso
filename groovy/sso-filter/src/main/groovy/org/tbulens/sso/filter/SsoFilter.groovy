package org.tbulens.sso.filter

import org.tbulens.sso.client.authenticate.AuthenticateRequest
import org.tbulens.sso.client.authenticate.AuthenticateResponse
import org.tbulens.sso.client.authenticate.AuthenticateSender
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
    String ssoLoginUrlPrefix
    AuthenticateRequestFactory authenticateRequestFactory = new AuthenticateRequestFactory()
    AuthenticateSender authenticateSender

    void init(FilterConfig filterConfig) throws ServletException {

    }

    // be sure only secure urls get into this filter by specifying filter pattern.
    void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = servletRequest as HttpServletRequest
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        if (isAuthenticated(request)) {
            filterChain.doFilter(servletRequest, servletResponse)
        } else {
            httpResponse.sendRedirect(buildCompleteLoginSsoUrl(request))
        }
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        Cookie secureCookie = findSecureCookie(request.cookies)
        if (!secureCookie) return false

        AuthenticateRequest authenticateRequest = authenticateRequestFactory.create(request, secureCookie.value)
        AuthenticateResponse authenticateResponse = authenticateSender.send(authenticateRequest)
        authenticateResponse.isAuthenticated()
    }

    private Cookie findSecureCookie(Cookie[] cookies) {
        cookies.find { it.name == SSO_SESSION_ID }
    }

    private String buildCompleteLoginSsoUrl(HttpServletRequest request) {
        String encodedServiceUrl = buildEncodedServiceUrl(request)
        ssoLoginUrlPrefix + LOGIN + "?service=" + encodedServiceUrl
    }

    private String buildEncodedServiceUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        URLEncoder.encode(url + "?" + queryString, "UTF-16")
    }

    void destroy() {

    }
}
