package org.tbulens.sso.server.login

import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.tbulens.sso.client.login.LoginResponse
import org.tbulens.sso.client.util.JsonUtil
import org.tbulens.sso.server.util.DateConverter
import org.tbulens.sso.server.util.TicketGenerator
import org.tbulens.sso.redis.RedisUtil

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
        String cookieId = ""

        DateTime expirationDate = new DateTime().plusSeconds(Integer.valueOf(ticketExpirationInSeconds))

        String requestTicket = null
        if (statusId == LoginResponse.VALID_REQUEST) {
            requestTicket = ticketGenerator.generateRequestTicket()
            cookieId = ticketGenerator.generateTicket()
        }

        String key = loginRequestMap.originalServiceUrl as String
        Map<String, String> services = [:]
        services.put(key, requestTicket)

        new LoginTicket(secureCookieId: cookieId, userId: userId, sessionId: sessionId, createDate: new Date(),
                lastAccessed: new Date(), expiredTime: expirationDate.toDate(), services: services)
    }


    private LoginTicket createFromJsonMap(Map<String, Object> loginTicketMap) {
        LoginTicket ticket = new LoginTicket()
        ticket.secureCookieId = loginTicketMap.secureCookieId
        ticket.userId = loginTicketMap.userId
        ticket.sessionId = loginTicketMap.sessionId
        ticket.expiredTime = DateConverter.convertFromJson(loginTicketMap.expiredTime)
        ticket.createDate = DateConverter.convertFromJson(loginTicketMap.createDate)
        ticket.lastAccessed = DateConverter.convertFromJson(loginTicketMap.lastAccessed)
        ticket.services = loginTicketMap.services as Map<String, String>
        ticket
    }
}
