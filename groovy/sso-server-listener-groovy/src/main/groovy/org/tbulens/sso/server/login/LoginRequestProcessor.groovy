package org.tbulens.sso.server.login

import org.apache.log4j.Logger
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.client.util.JsonUtil

@Component
class LoginRequestProcessor {
    @Autowired LoginService loginService
    JsonUtil jsonUtil = new JsonUtil()
    Logger logger = Logger.getLogger(this.class.name)

    @RabbitListener(queues = "login")
    String login(String loginRequestJson) {
        logger.debug("Login Processor recieved request = " + loginRequestJson)
        Map<String, Object> loginRequestMap = jsonUtil.fromJson(loginRequestJson, Map.class)
        loginService.process(loginRequestMap).toJson()
    }
}
