package org.tbulens.sso.server.login

import groovy.json.JsonSlurper
import org.springframework.stereotype.Component

@Component
class LoginTicketFactory {

    LoginTicket create(String loginRequestJson) {
        def jsonSlurper = new JsonSlurper()
        def loginRequestMap = jsonSlurper.parseText(loginRequestJson)

        String userId = loginRequestMap.userId
        String sessionId = loginRequestMap.sessionId

        new LoginTicket(userId: userId, sessionId: sessionId, createDate: new Date(), lastAccessed: new Date())
    }
}
