package org.tbulens.sso.server.login

import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.tbulens.sso.client.login.LoginResponse
import org.tbulens.sso.client.util.JsonUtil
import org.tbulens.sso.server.TicketGenerator
import org.tbulens.sso.server.redis.RedisUtil
import org.tbulens.sso.server.util.DateConverter

@Component
class LoginTicketFactory {
    @Autowired RedisUtil redisUtil
    @Value('${ticketExpirationInSeconds}') String ticketExpirationInSeconds
    TicketGenerator ticketGenerator = new TicketGenerator()
    JsonUtil jsonUtil = new JsonUtil()

    LoginTicket createFromSecureCookie(String secureCookieId) {
        String loginTicketJson = redisUtil.get(secureCookieId)

        LoginTicket loginTicket = null
        if (loginTicketJson) {
            Map<String, Object> loginTicketMap = jsonUtil.fromJson(loginTicketJson)
            loginTicket = createFromJsonMap(loginTicketMap)
        }
        loginTicket
    }

    protected LoginTicket create(Map<String, Object> loginRequestMap, int statusId) {
        String userId = loginRequestMap.userId
        String sessionId = loginRequestMap.sessionId
        String originalServiceUrl = loginRequestMap.originalServiceUrl
        String cookieId = ticketGenerator.generateTicket()

        DateTime expirationDate = new DateTime().plusSeconds(Integer.valueOf(ticketExpirationInSeconds))

        String requestTicket = null
        if (statusId == LoginResponse.VALID_REQUEST) {
            requestTicket = ticketGenerator.generateRequestTicket()
        }

        new LoginTicket(secureCookieId: cookieId, userId: userId, sessionId: sessionId, createDate: new Date(),
                lastAccessed: new Date(), originalServiceUrl: originalServiceUrl, requestTicket: requestTicket, expiredTime: expirationDate.toDate())
    }


    private LoginTicket createFromJsonMap(Map<String, Object> loginTicketMap) {
        LoginTicket ticket = new LoginTicket()
        ticket.secureCookieId = loginTicketMap.secureCookieId
        ticket.userId = loginTicketMap.userId
        ticket.sessionId = loginTicketMap.sessionId
        ticket.originalServiceUrl = loginTicketMap.originalServiceUrl
        ticket.requestTicket = loginTicketMap.requestTicket
        ticket.expiredTime = DateConverter.convertFromJson(loginTicketMap.expiredTime)
        ticket.createDate = DateConverter.convertFromJson(loginTicketMap.createDate)
        ticket.lastAccessed = DateConverter.convertFromJson(loginTicketMap.lastAccessed)
        ticket
    }
}
