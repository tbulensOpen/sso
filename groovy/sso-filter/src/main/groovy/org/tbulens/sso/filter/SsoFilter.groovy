package org.tbulens.sso.filter

import org.apache.log4j.Logger
import org.tbulens.sso.client.authenticate.AuthenticateRequest
import org.tbulens.sso.client.authenticate.AuthenticateResponse
import org.tbulens.sso.client.authenticate.AuthenticateSender
import org.tbulens.sso.common.util.UrlBuilder

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
    Logger log = Logger.getLogger(this.class.name)
    UrlBuilder urlBuilder = new UrlBuilder()

    void init(FilterConfig filterConfig) throws ServletException {

    }

    // be sure only secure urls get into this filter by specifying filter pattern.
    void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = servletRequest as HttpServletRequest
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        log.debug("Started sso filter!!!")
        if (isAuthenticated(request)) {
            log.debug("User is authenticated!!!")
            filterChain.doFilter(servletRequest, servletResponse)
        } else {
            log.debug("Redirect login!!!")
            httpResponse.sendRedirect(buildCompleteLoginSsoUrl(request))
        }
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        Cookie secureCookie = findSecureCookie(request.cookies)
        log.debug("sso.filter - isAuthenticated.secureCookie = " + secureCookie?.value )
        if (!secureCookie) return false

        AuthenticateRequest authenticateRequest = authenticateRequestFactory.create(request, secureCookie.value)
        AuthenticateResponse authenticateResponse = authenticateSender.send(authenticateRequest)

        log.debug("ssoFilter authenticated response = " + authenticateResponse)
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
        URLEncoder.encode(urlBuilder.create(request), "UTF-16")
    }

    void destroy() {

    }
}
