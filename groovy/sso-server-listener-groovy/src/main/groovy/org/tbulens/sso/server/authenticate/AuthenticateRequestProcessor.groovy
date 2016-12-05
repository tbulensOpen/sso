package org.tbulens.sso.server.authenticate

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.client.util.JsonUtil

@Component
class AuthenticateRequestProcessor {
    @Autowired AuthenticateService authenticateService

    @RabbitListener(queues = "authenticate")
    String process(String authenticateRequestJson) {
        JsonUtil jsonUtil = new JsonUtil()
        Map<String, Object> authenticateRequestMap = jsonUtil.fromJson(authenticateRequestJson)

        authenticateService.process(authenticateRequestMap).toJson()
    }
}
