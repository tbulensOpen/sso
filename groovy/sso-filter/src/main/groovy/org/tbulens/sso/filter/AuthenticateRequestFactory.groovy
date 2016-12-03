package org.tbulens.sso.filter

import org.tbulens.sso.client.authenticate.AuthenticateRequest

import javax.servlet.http.HttpServletRequest


class AuthenticateRequestFactory {

    AuthenticateRequest create(HttpServletRequest request, String secureCookieId) {
        AuthenticateRequest authenticateRequest = new AuthenticateRequest()
        authenticateRequest.secureCookieId = secureCookieId
        authenticateRequest
    }
}
