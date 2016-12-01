package org.tbulens.sso.client.authenticate

import org.tbulens.sso.client.util.JsonUtil


class AuthenticateRequest {
    String secureCookieId
    String sessionId
    String requestTicket
    String originalServiceUrl

    String toJson() {
        new JsonUtil().toJson(this)
    }
}
