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
    String originalServiceUrl
    int statusId
    String requestTicket

    String toJson() {
        new JsonUtil().toJson(this)
    }

    boolean isLoggedIn() {
        statusId == VALID_REQUEST
    }

    String getStatusMessage() {
        switch (statusId) {
            case VALID_REQUEST:
                null
                break
            case BAD_REQUEST:
                "Invalid credentials."
                break
            case USER_ALREADY_LOGGED_IN:
                "Already logged in."
                break
            default:
                "Status Unkown."
                break
        }
    }
}
