package org.tbulens.sso.server.login

import org.tbulens.sso.client.util.JsonUtil

class LoginTicket {
    String userId
    String sessionId
    Date createDate
    Date lastAccessed
    String request

    String toJson() {
        new JsonUtil().toJson(this)
    }
}
