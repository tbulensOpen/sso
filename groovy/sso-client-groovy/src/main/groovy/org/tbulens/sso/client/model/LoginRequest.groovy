package org.tbulens.sso.client.model

import groovy.json.JsonBuilder
import org.tbulens.sso.client.util.JsonUtil


class LoginRequest {
    String userId
    String sessionId
    String ssoTicket

    String toJson() {
        new JsonUtil().toJson(this)
    }
}