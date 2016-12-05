package org.tbulens.sso.server.login

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.client.util.JsonUtil

@Component
class LoginRequestProcessor {
    @Autowired LoginService loginService
    JsonUtil jsonUtil = new JsonUtil()

    @RabbitListener(queues = "login")
    // @SendTo("login.rpc.replies") used when the client doesn't set replyTo.
    String login(String loginRequestJson) {
        Map<String, Object> loginRequestMap = jsonUtil.fromJson(loginRequestJson)
        loginService.process(loginRequestMap).toJson()
    }
}
