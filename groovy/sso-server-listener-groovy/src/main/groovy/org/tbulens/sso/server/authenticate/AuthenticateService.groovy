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

    protected AuthenticateResponse process(Map<String, Object> authenticateRequestMap) {
        String secureCookieId = authenticateRequestMap.secureCookieId
        LoginTicket loginTicket = loginTicketFactory.createFromSecureCookie(secureCookieId)

        int status = requestValidator.validate(authenticateRequestMap, loginTicket)
        AuthenticateResponse response = responseFactory.create(loginTicket, status)

        //todo: convert switch into processors, retrieve processor using a factory
        switch (status) {
            case AuthenticateResponse.AUTHENTICATED:
                loginTicket.requestTicket = response.requestTicket
                redisUtil.push(secureCookieId, loginTicket.toJson())
                break
            case AuthenticateResponse.BAD_REQUEST:
                //todo: what to do here
                break
            case AuthenticateResponse.NOT_AUTHENTICATED:
                //todo: what to do here
                break
            case AuthenticateResponse.NOT_AUTHORIZED_SECURITY_VIOLATION:
                //todo: what to do here
                break
            case AuthenticateResponse.TICKET_EXPIRED:
                //todo: what to do here
                break
            default:
                //todo: what to do here
                break
        }
        response
    }


}
