package org.tbulens.sso.client.login

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.rabbitmq.SsoJmsMessageSender

class LoginSenderImpl implements LoginSender {

    String loginQueueChannel
    SsoJmsMessageSender ssoJmsMessageSender

    String send(LoginRequest request) {
        ssoJmsMessageSender.send(loginQueueChannel, request.toJson())
    }
}
