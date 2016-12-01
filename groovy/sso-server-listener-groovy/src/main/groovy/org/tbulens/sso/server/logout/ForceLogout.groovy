package org.tbulens.sso.server.logout

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.client.util.JsonUtil
import org.tbulens.sso.server.login.LoginTicket
import org.tbulens.sso.server.login.LoginTicketFactory
import org.tbulens.sso.server.redis.RedisUtil

@Component
class ForceLogout {
    @Autowired LogoutRepository logoutRepository

    void logout(LoginTicket loginTicket) {
        if (loginTicket) {
            logoutRepository.logout(loginTicket)
        }
    }
}
