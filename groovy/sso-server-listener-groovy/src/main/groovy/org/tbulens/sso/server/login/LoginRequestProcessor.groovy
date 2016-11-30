package org.tbulens.sso.server.login

import groovy.json.JsonSlurper
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.client.util.JsonUtil
import org.tbulens.sso.server.redis.RedisUtil


@Component
class LoginRequestProcessor {
    @Autowired RedisUtil redisUtil
    @Autowired LoginTicketFactory loginTicketFactory
    @Autowired LoginResultFactory loginResultFactory
    @Autowired LoginRequestValidator loginRequestValidator
    JsonUtil jsonUtil = new JsonUtil()

    @RabbitListener(queues = "login.rpc.requests")
    // @SendTo("login.rpc.replies") used when the client doesn't set replyTo.
    String login(String loginRequestJson) {
        def loginRequestMap = jsonUtil.fromJson(loginRequestJson)

        int returnValue = loginRequestValidator.validate(loginRequestMap)
        if (returnValue == LoginRequestValidator.VALID_REQUEST) {
            LoginTicket loginTicket = loginTicketFactory.create(loginRequestMap)
            redisUtil.push(loginTicket.secureCookieId, loginTicket.toJson())
            return loginResultFactory.create(loginTicket).toJson()
        }
        null
    }
}
