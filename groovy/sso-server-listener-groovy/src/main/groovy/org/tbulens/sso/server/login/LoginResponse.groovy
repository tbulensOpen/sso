package org.tbulens.sso.server.login

import groovy.transform.Canonical
import org.tbulens.sso.client.util.JsonUtil

class LoginResponse implements Serializable {
    final static int VALID_REQUEST = 0
    final static int BAD_REQUEST = 1
    final static int USER_ALREADY_LOGGED_IN = 2

    String secureCookieId
    String userId
    String sessionId
    String originalServiceUrl
    int statusId

    String toJson() {
        new JsonUtil().toJson(this)
    }
}
