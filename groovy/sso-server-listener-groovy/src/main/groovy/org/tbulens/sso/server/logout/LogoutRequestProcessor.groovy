package org.tbulens.sso.server.logout

import org.apache.log4j.Logger
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.client.util.JsonUtil

@Component
class LogoutRequestProcessor {
    @Autowired LogoutRepository logoutRepository
    JsonUtil jsonUtil = new JsonUtil()
    Logger log = Logger.getLogger(this.class.name)

    @RabbitListener(queues = "logout")
    void logout(String logoutRequestJson) {
        Map<String, Object> logoutRequestMap = jsonUtil.fromJson(logoutRequestJson, Map.class)
        String secureCookieId = logoutRequestMap.secureCookieId
        logoutRepository.logout(secureCookieId)
    }
}
