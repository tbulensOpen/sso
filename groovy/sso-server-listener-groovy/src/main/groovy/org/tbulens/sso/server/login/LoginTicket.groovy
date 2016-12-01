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

    void addServices(Map<String, String> services) {
        this.services.clear()
        this.services.putAll(services)
    }

    String toJson() {
        new JsonUtil().toJson(this)
    }

    boolean isExpired() {
        expiredTime < new Date()
    }
}
