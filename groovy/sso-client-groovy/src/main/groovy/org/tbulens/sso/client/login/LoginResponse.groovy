package org.tbulens.sso.client.login

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.Canonical
import org.tbulens.sso.client.util.JsonUtil

@Canonical
class LoginResponse implements Serializable {
    @JsonIgnore final static int VALID_REQUEST = 0
    @JsonIgnore final static int BAD_REQUEST = 1
    @JsonIgnore final static int USER_ALREADY_LOGGED_IN = 2

    String secureCookieId
    String userId
    String sessionId
    String originalServiceUrl
    int statusId
    String requestTicket

    String toJson() {
        new JsonUtil().toJson(this)
    }

    boolean isLoggedIn() {
        statusId == VALID_REQUEST
    }
}
