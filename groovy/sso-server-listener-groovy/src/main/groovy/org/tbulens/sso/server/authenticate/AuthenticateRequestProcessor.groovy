package org.tbulens.sso.server.authenticate

import org.apache.log4j.Logger
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.client.util.JsonUtil

@Component
class AuthenticateRequestProcessor {
    @Autowired AuthenticateService authenticateService
    JsonUtil jsonUtil = new JsonUtil()
    Logger log = Logger.getLogger(this.class.name)

    @RabbitListener(queues = "authenticate")
    String process(String authenticateRequestJson) {
        Map<String, Object> authenticateRequestMap = jsonUtil.fromJson(authenticateRequestJson, Map.class) as Map<String, Object>

        log.debug("authRequestMap: " + authenticateRequestMap)
        authenticateService.process(authenticateRequestMap).toJson()
    }
}
