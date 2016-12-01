package org.tbulens.sso.server.login

import org.tbulens.sso.client.util.JsonUtil

class LoginTicket implements Serializable {
    String secureCookieId
    String userId
    String sessionId
    Date createDate
    Date lastAccessed
    Date expiredTime
    // key: originalServiceUrl, value: request ticket
    Map<String, String> services = [:]

    void addService(String key, String value) {
        services.put(key, value)
    }

    String toJson() {
        new JsonUtil().toJson(this)
    }

    boolean isExpired() {
        false
    }
}
