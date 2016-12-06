package org.tbulens.sso.server.login

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.client.login.LoginResponse
import org.tbulens.sso.server.logout.LogoutRepository
import org.tbulens.sso.redis.RedisUtil

@Component
class LoginService {
    @Autowired RedisUtil redisUtil
    @Autowired LoginTicketFactory loginTicketFactory
    @Autowired LoginResponseFactory loginResponseFactory
    @Autowired LoginRequestValidator loginRequestValidator
    @Autowired LogoutRepository logoutRepository

    protected LoginResponse process(Map<String, Object> loginRequestMap) {
        LoginTicket existingUserLoginTicket = findLoginTicket(loginRequestMap.userId as String)
        int status = loginRequestValidator.validate(loginRequestMap, existingUserLoginTicket)

        LoginTicket loginTicket = loginTicketFactory.create(loginRequestMap, status)
        if (status == LoginResponse.VALID_REQUEST) {
            saveUser(loginTicket)
        }
        return loginResponseFactory.create(loginTicket, status, loginRequestMap)
    }

    private void saveUser(LoginTicket loginTicket) {
        redisUtil.push(loginTicket.secureCookieId, loginTicket.toJson())
        redisUtil.push(loginTicket.userId, loginTicket.secureCookieId)

    }

    private LoginTicket findLoginTicket(String userId) {
        String secureCookieId = redisUtil.get(userId)

        if (!secureCookieId) return null

        LoginTicket loginTicket = loginTicketFactory.createFromSecureCookie(secureCookieId)
        if (loginTicket.isExpired()) {
            logoutRepository.logout(loginTicket)
            return null
        }
        loginTicket
    }
}
