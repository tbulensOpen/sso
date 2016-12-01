package org.tbulens.sso.server.logout

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.server.login.LoginTicket
import org.tbulens.sso.server.login.LoginTicketFactory
import org.tbulens.sso.server.redis.RedisUtil

@Component
class LogoutRepository {
    @Autowired RedisUtil redisUtil
    @Autowired LoginTicketFactory loginTicketFactory

    void logout(String secureCookieId) {
        LoginTicket loginTicket = loginTicketFactory.createFromSecureCookie(secureCookieId)
        logout(loginTicket)
    }

    void logout(LoginTicket loginTicket) {
        redisUtil.delete(loginTicket.secureCookieId)
        redisUtil.delete(loginTicket.userId)
    }
}
