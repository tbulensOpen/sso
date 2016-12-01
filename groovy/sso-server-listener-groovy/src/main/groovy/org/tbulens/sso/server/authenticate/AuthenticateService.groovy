package org.tbulens.sso.server.authenticate

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.client.authenticate.AuthenticateResponse
import org.tbulens.sso.client.util.JsonUtil
import org.tbulens.sso.server.login.LoginTicket
import org.tbulens.sso.server.redis.RedisUtil

@Component
class AuthenticateService {
    @Autowired RedisUtil redisUtil
    @Autowired AuthenticateRequestValidator requestValidator
    JsonUtil jsonUtil = new JsonUtil()

    AuthenticateResponse process(Map<String, Object> authenticateRequestMap) {
        String loginTicketJson = redisUtil.get(authenticateRequestMap.userId)
        LoginTicket loginTicket = loginTicketJson ? jsonUtil.fromJson(loginTicketJson) : null

        int status = requestValidator.validate(authenticateRequestMap, loginTicket)
        new AuthenticateResponse()
    }
}
