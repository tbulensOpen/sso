package org.tbulens.sso.client.authenticate

import org.tbulens.sso.client.util.JsonUtil


class AuthenticateResponse {
    final static int AUTHENTICATED = 0
    final static int BAD_REQUEST = 1
    final static int NOT_AUTHENTICATED = 2
    final static int NOT_AUTHORIZED_SECURITY_VIOLATION = 3
    final static int TICKET_EXPIRED = 4

    String secureCookieId
    String sessionId
    String requestTicket
    int statusId
    String originalServiceUrl

    String toJson() {
        new JsonUtil().toJson(this)
    }
}
