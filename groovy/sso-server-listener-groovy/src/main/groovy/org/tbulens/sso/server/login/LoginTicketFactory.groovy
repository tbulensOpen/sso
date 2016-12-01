package org.tbulens.sso.server.login

import groovy.json.JsonSlurper
import org.springframework.stereotype.Component
import org.tbulens.sso.client.login.LoginResponse

@Component
class LoginTicketFactory {

    protected LoginTicket create( def loginRequestMap, int status) {
        String userId = loginRequestMap.userId
        String sessionId = loginRequestMap.sessionId
        String originalServiceUrl = loginRequestMap.originalServiceUrl
        UUID cookieUid = UUID.randomUUID()
        String cookieId = String.valueOf(cookieUid)

        String requestTicket = null
        if (status == LoginResponse.VALID_REQUEST) {
            UUID requestTicketUid = UUID.randomUUID()
            requestTicket = "RT_" + String.valueOf(requestTicketUid)
        }

        new LoginTicket(secureCookieId: cookieId, userId: userId, sessionId: sessionId, createDate: new Date(),
                        lastAccessed: new Date(), originalServiceUrl: originalServiceUrl, requestTicket: requestTicket)
    }
}
