package org.tbulens.sso.filter

import org.tbulens.sso.client.authenticate.AuthenticateRequest
import org.tbulens.sso.common.util.UrlBuilder

import javax.servlet.http.HttpServletRequest


class AuthenticateRequestFactory {
    UrlBuilder urlBuilder = new UrlBuilder()

    AuthenticateRequest create(HttpServletRequest request, String secureCookieId) {
        AuthenticateRequest authenticateRequest = new AuthenticateRequest()
        authenticateRequest.secureCookieId = secureCookieId
        authenticateRequest.originalServiceUrl = urlBuilder.create(request)
        authenticateRequest.requestTicket = request.getParameter("")
        authenticateRequest
    }
}
