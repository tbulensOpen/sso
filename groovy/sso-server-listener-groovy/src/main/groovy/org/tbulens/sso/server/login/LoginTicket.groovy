package org.tbulens.sso.server.login

import org.tbulens.sso.client.util.JsonUtil

class LoginTicket implements Serializable {
    String secureCookieId
    String userId
    String sessionId
    Date createDate
    Date lastAccessed
    Date expiredTime
    String originalServiceUrl
    String requestTicket

    String toJson() {
        new JsonUtil().toJson(this)
    }
}
