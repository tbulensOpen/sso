package org.tbulens.sso.server.authenticate

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.client.authenticate.AuthenticateResponse
import org.tbulens.sso.server.login.LoginTicket
import org.tbulens.sso.server.login.LoginTicketFactory
import org.tbulens.sso.server.logout.ForceLogout
import org.tbulens.sso.server.logout.LogoutRepository
import org.tbulens.sso.server.redis.RedisUtil

@Component
class AuthenticateService {
    @Autowired RedisUtil redisUtil
    @Autowired AuthenticateRequestValidator requestValidator
    @Autowired AuthenticateResponseFactory responseFactory
    @Autowired LoginTicketFactory loginTicketFactory
    @Autowired ForceLogout forceLogout
    @Autowired LogoutRepository logoutRepository

    protected AuthenticateResponse process(Map<String, Object> authenticateRequestMap) {
        String secureCookieId = authenticateRequestMap.secureCookieId
        LoginTicket loginTicket = loginTicketFactory.createFromSecureCookie(secureCookieId)

        int status = requestValidator.validate(authenticateRequestMap, loginTicket)
        AuthenticateResponse response = responseFactory.create(loginTicket, status, authenticateRequestMap)

        //todo: convert switch into processors, retrieve processor using a factory
        switch (status) {
            case AuthenticateResponse.AUTHENTICATED:
                loginTicket.services.put(authenticateRequestMap.originalServiceUrl as String, response.requestTicket)
                redisUtil.push(secureCookieId, loginTicket.toJson())
                break
            case AuthenticateResponse.BAD_REQUEST:
                forceLogout.logout(loginTicket)
                //todo: what to do here
                break
            case AuthenticateResponse.NOT_AUTHENTICATED:
                //todo: what to do here
                break
            case AuthenticateResponse.NOT_AUTHORIZED_SECURITY_VIOLATION:
                //todo: what to do here
                forceLogout.logout(loginTicket)
                break
            case AuthenticateResponse.TICKET_EXPIRED:
                logoutRepository.logout(loginTicket)
                //todo: what to do here
                break
            default:
                //todo: what to do here
                break
        }
        response
    }


}
