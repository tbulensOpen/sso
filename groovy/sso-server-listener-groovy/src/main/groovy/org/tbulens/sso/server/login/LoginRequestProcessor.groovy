package org.tbulens.sso.server.login

import groovy.json.JsonSlurper
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component
import org.tbulens.sso.server.redis.RedisUtil


@Component
class LoginRequestProcessor {
    @Autowired RedisUtil redisUtil
    @Autowired LoginTicketFactory loginTicketFactory

    @RabbitListener(queues = "login.rpc.requests")
    // @SendTo("login.rpc.replies") used when the client doesn't set replyTo.
    String login(String loginRequestJson) {
        LoginTicket loginTicket = loginTicketFactory.create(loginRequestJson)
        redisUtil.push(loginTicket.userId, loginTicket.toJson())
        System.out.println(" [x] Received request for " + loginRequestJson)
        loginTicket.userId + ":" + loginTicket.sessionId
    }
}
