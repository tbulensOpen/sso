package org.tbulens.sso.server.login

import groovy.transform.Canonical
import org.tbulens.sso.client.util.JsonUtil

class LoginResult implements Serializable {
    String secureCookieId
    String userId
    String sessionId
    String originalServiceUrl

    String toJson() {
        new JsonUtil().toJson(this)
    }
}
