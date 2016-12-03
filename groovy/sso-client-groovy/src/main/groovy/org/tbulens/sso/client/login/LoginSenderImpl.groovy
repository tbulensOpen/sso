package org.tbulens.sso.client.login

import org.tbulens.sso.rabbitmq.SsoJmsMessageSender

class LoginSenderImpl implements LoginSender {

    String loginQueueChannel
    SsoJmsMessageSender ssoJmsMessageSender
    LoginResponseFactory loginResponseFactory = new LoginResponseFactory()

    LoginResponse send(LoginRequest request) {
        String loginResponseJson = ssoJmsMessageSender.sendAndReceive(loginQueueChannel, "login.rpc.requests", request.toJson())
        loginResponseFactory.create(loginResponseJson)
    }
}
