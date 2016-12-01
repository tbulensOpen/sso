package org.tbulens.sso.server.login

import groovy.json.JsonSlurper
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.tbulens.sso.client.login.LoginResponse

@Component
class LoginTicketFactory {
    @Value('${ticketExpirationInSeconds}') String ticketExpirationInSeconds

    protected LoginTicket create( def loginRequestMap, int status) {
        String userId = loginRequestMap.userId
        String sessionId = loginRequestMap.sessionId
        String originalServiceUrl = loginRequestMap.originalServiceUrl
        UUID cookieUid = UUID.randomUUID()
        String cookieId = String.valueOf(cookieUid)

        DateTime expirationDate = new DateTime().plusSeconds(Integer.valueOf(ticketExpirationInSeconds))

        String requestTicket = null
        if (status == LoginResponse.VALID_REQUEST) {
            UUID requestTicketUid = UUID.randomUUID()
            requestTicket = "RT_" + String.valueOf(requestTicketUid)
        }

        new LoginTicket(secureCookieId: cookieId, userId: userId, sessionId: sessionId, createDate: new Date(),
                        lastAccessed: new Date(), originalServiceUrl: originalServiceUrl, requestTicket: requestTicket, expiredTime: expirationDate.toDate())
    }
}
