package org.tbulens.sso.redis.login

import groovy.transform.Canonical

class LoginResult implements Serializable {
    String secureCookieId
    String userId
    String sessionId
    String originalServiceUrl

}
