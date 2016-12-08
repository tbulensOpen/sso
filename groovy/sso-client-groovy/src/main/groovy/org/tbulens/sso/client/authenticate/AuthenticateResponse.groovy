package org.tbulens.sso.client.authenticate

import com.fasterxml.jackson.annotation.JsonIgnore
import org.tbulens.sso.client.util.JsonUtil


class AuthenticateResponse {
    @JsonIgnore final static int AUTHENTICATED = 0
    @JsonIgnore final static int BAD_REQUEST = 1
    @JsonIgnore final static int NOT_AUTHENTICATED = 2
    @JsonIgnore final static int NOT_AUTHORIZED_SECURITY_VIOLATION = 3
    @JsonIgnore final static int TICKET_EXPIRED = 4

    String secureCookieId
    String sessionId
    String requestTicket
    int statusId
    String originalServiceUrl

    String toJson() {
        new JsonUtil().toJson(this)
    }

    boolean isAuthenticated() {
        statusId == AUTHENTICATED
    }
}
