package org.tbulens.sso.client.authenticate

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.Canonical
import org.tbulens.sso.client.SsoJwtToken
import org.tbulens.sso.client.util.JsonUtil

@Canonical
class AuthenticateResponse implements Serializable {
    @JsonIgnore final static int AUTHENTICATED = 0
    @JsonIgnore final static int BAD_REQUEST = 1
    @JsonIgnore final static int NOT_AUTHENTICATED = 2
    @JsonIgnore final static int NOT_AUTHORIZED_SECURITY_VIOLATION = 3
    @JsonIgnore final static int TICKET_EXPIRED = 4

    SsoJwtToken ssoJwtToken
    int statusId
    String originalServiceUrl

    String toJson() {
        new JsonUtil().toJson(this)
    }

    @JsonIgnore
    boolean isAuthenticated() {
        statusId == AUTHENTICATED
    }
}
