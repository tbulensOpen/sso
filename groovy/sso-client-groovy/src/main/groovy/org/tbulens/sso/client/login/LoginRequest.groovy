package org.tbulens.sso.client.login

import groovy.json.JsonBuilder
import org.tbulens.sso.client.util.JsonUtil


class LoginRequest {
    String userId
    String sessionId
    String originalServiceUrl

    String toJson() {
        new JsonUtil().toJson(this)
    }
}
