package org.tbulens.sso.server.authenticate

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.client.authenticate.AuthenticateResponse
import org.tbulens.sso.client.util.JsonUtil
import org.tbulens.sso.server.login.LoginTicket
import org.tbulens.sso.server.login.LoginTicketFactory
import org.tbulens.sso.server.redis.RedisUtil

@Component
class AuthenticateService {
    @Autowired RedisUtil redisUtil
    @Autowired AuthenticateRequestValidator requestValidator
    @Autowired AuthenticateResponseFactory responseFactory
    @Autowired LoginTicketFactory loginTicketFactory

    JsonUtil jsonUtil = new JsonUtil()

    protected AuthenticateResponse process(Map<String, Object> authenticateRequestMap) {
        String secureCookieId = authenticateRequestMap.secureCookieId
        LoginTicket loginTicket = loginTicketFactory.createFromSecureCookie(secureCookieId)

        int status = requestValidator.validate(authenticateRequestMap, loginTicket)
        responseFactory.create(loginTicket, status)
    }


}
