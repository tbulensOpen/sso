package org.tbulens.sso.server.login

import groovy.json.JsonSlurper
import org.springframework.stereotype.Component

@Component
class LoginTicketFactory {

    LoginTicket create( def loginRequestMap) {
        String userId = loginRequestMap.userId
        String sessionId = loginRequestMap.sessionId
        String originalServiceUrl = loginRequestMap.originalServiceUrl
        UUID uid = UUID.randomUUID()
        String cookieId = String.valueOf(uid)

        new LoginTicket(secureCookieId: cookieId, userId: userId, sessionId: sessionId, createDate: new Date(), lastAccessed: new Date(), originalServiceUrl: originalServiceUrl)
    }
}
