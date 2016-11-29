package org.tbulens.sso.server

import groovy.json.JsonSlurper
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component


@Component
class LoginRequestProcessor {
    @Autowired StringRedisTemplate redisTemplate;

    @RabbitListener(queues = "login.rpc.requests")
    // @SendTo("login.rpc.replies") used when the client doesn't set replyTo.
    String login(String loginRequestJson) {
        def jsonSlurper = new JsonSlurper()
        def loginRequestMap = jsonSlurper.parseText(loginRequestJson)

        String userId = loginRequestMap.userId
        String sessionId = loginRequestMap.sessionId

        System.out.println(" [x] Received request for " + loginRequestJson)
        userId + ":" + sessionId
    }
}
