package org.tbulens.sso.client.authenticate

import org.tbulens.sso.client.util.JsonUtil


class AuthResponseFactory {
    JsonUtil jsonUtil = new JsonUtil()

    AuthenticateResponse create(String authResponseJson) {
        Map<String, Object> authResponseMap = jsonUtil.fromJson(authResponseJson)

        AuthenticateResponse response = new AuthenticateResponse()
        response.sessionId = authResponseMap.sessionId
        response.secureCookieId = authResponseMap.secureCookieId
        response.statusId = authResponseMap.statusId
        response.originalServiceUrl = authResponseMap.originalServiceUrl
        response.requestTicket =  authResponseMap.requestTicket
        response
    }

}