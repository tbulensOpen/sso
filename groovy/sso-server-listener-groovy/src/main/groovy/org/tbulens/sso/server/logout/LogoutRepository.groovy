package org.tbulens.sso.server.logout

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.server.login.LoginTicket
import org.tbulens.sso.server.login.LoginTicketFactory
import org.tbulens.sso.redis.RedisUtil

@Component
class LogoutRepository {
    @Autowired RedisUtil redisUtil
    @Autowired LoginTicketFactory loginTicketFactory
    Logger log = Logger.getLogger(this.class.name)

    void logout(String secureCookieId) {
        LoginTicket loginTicket = loginTicketFactory.createFromSecureCookie(secureCookieId)
        loginTicket ? logout(loginTicket) : log.debug(this.class.name + ": missing login ticket for secure cookieId: ${secureCookieId}")
    }

    void logout(LoginTicket loginTicket) {
        redisUtil.delete(loginTicket.secureCookieId)
        redisUtil.delete(loginTicket.userId)
    }
}
