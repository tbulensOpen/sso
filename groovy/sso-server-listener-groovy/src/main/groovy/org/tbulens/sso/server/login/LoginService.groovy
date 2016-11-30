package org.tbulens.sso.server.login

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.server.redis.RedisUtil

@Component
class LoginService {
    @Autowired RedisUtil redisUtil
    @Autowired LoginTicketFactory loginTicketFactory
    @Autowired LoginResponseFactory loginResponseFactory
    @Autowired LoginRequestValidator loginRequestValidator

    LoginResponse process(def loginRequestMap) {
        int status = loginRequestValidator.validate(loginRequestMap)
        LoginTicket loginTicket = loginTicketFactory.create(loginRequestMap)
        if (status == LoginResponse.VALID_REQUEST) {
            redisUtil.push(loginTicket.secureCookieId, loginTicket.toJson())
        }
        return loginResponseFactory.create(loginTicket, status)
    }
}
